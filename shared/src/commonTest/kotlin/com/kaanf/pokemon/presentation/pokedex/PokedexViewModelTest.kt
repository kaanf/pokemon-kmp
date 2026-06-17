package com.kaanf.pokemon.presentation.pokedex

import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.TeamMember
import com.kaanf.pokemon.domain.usecase.AddPokemonToTeamUseCase
import com.kaanf.pokemon.testutil.FakeConnectivityObserver
import com.kaanf.pokemon.testutil.FakePokemonRepository
import com.kaanf.pokemon.testutil.FakeTeamRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class PokedexViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val pokemonRepository = FakePokemonRepository()
    private val teamRepository = FakeTeamRepository()
    private val connectivityObserver = FakeConnectivityObserver()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = PokedexViewModel(
        pokemonRepository = pokemonRepository,
        connectivityObserver = connectivityObserver,
        addPokemonToTeam = AddPokemonToTeamUseCase(teamRepository),
        teamRepository = teamRepository,
    )

    private fun pokemon(id: Int) = Pokemon(id = id, name = "p$id", imageUrl = "url$id")

    @Test
    fun initialLoadFetchesFirstPageAndClearsLoadingFlags() = runTest(testDispatcher) {
        pokemonRepository.pageToEmit = listOf(pokemon(1), pokemon(2), pokemon(3))
        pokemonRepository.nextPageResult = Result.Success(false)
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(listOf(1, 2, 3), state.pokemon?.map { it.id })
        assertFalse(state.isInitialLoading)
        assertFalse(state.isLoadingMore)
        assertFalse(state.endReached)
        assertEquals(1, pokemonRepository.fetchNextPageCount)
        collector.cancel()
    }

    @Test
    fun endReachedIsSetWhenLastPageLoaded() = runTest(testDispatcher) {
        pokemonRepository.pageToEmit = listOf(pokemon(1))
        pokemonRepository.nextPageResult = Result.Success(true)
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        advanceUntilIdle()

        assertTrue(viewModel.state.value.endReached)
        collector.cancel()
    }

    @Test
    fun loadMoreIsIgnoredAfterEndReached() = runTest(testDispatcher) {
        pokemonRepository.pageToEmit = listOf(pokemon(1))
        pokemonRepository.nextPageResult = Result.Success(true)
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        advanceUntilIdle()

        viewModel.onAction(PokedexAction.OnLoadMore)
        advanceUntilIdle()

        assertEquals(1, pokemonRepository.fetchNextPageCount)
        collector.cancel()
    }

    @Test
    fun coldStartWithCachedContentSkipsFullScreenShimmer() = runTest(testDispatcher) {
        pokemonRepository.pokedex.value = listOf(pokemon(1), pokemon(2))
        pokemonRepository.nextPageResult = Result.Success(false)
        val gate = CompletableDeferred<Unit>()
        pokemonRepository.fetchNextPageGate = gate
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        runCurrent() // refresh is in flight, suspended on the gate

        // Cached data renders immediately; only the background refresh indicator is active.
        assertFalse(viewModel.state.value.isInitialLoading)
        assertTrue(viewModel.state.value.isLoadingMore)

        gate.complete(Unit)
        advanceUntilIdle()
        collector.cancel()
    }

    @Test
    fun coldStartWithEmptyDbShowsInitialShimmer() = runTest(testDispatcher) {
        pokemonRepository.pageToEmit = listOf(pokemon(1))
        pokemonRepository.nextPageResult = Result.Success(false)
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        runCurrent()

        // No cache -> full-screen shimmer while the first page loads.
        assertTrue(viewModel.state.value.isInitialLoading)
        assertFalse(viewModel.state.value.isLoadingMore)

        advanceUntilIdle()
        assertFalse(viewModel.state.value.isInitialLoading)
        collector.cancel()
    }

    @Test
    fun paginationAfterInitialLoadUsesLoadMore() = runTest(testDispatcher) {
        pokemonRepository.pageToEmit = listOf(pokemon(1))
        pokemonRepository.nextPageResult = Result.Success(false)
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        advanceUntilIdle() // initial load completes

        val gate = CompletableDeferred<Unit>()
        pokemonRepository.fetchNextPageGate = gate
        viewModel.onAction(PokedexAction.OnLoadMore)
        runCurrent() // pagination fetch is in flight, suspended on the gate

        assertFalse(viewModel.state.value.isInitialLoading)
        assertTrue(viewModel.state.value.isLoadingMore)

        gate.complete(Unit)
        advanceUntilIdle()
        collector.cancel()
    }

    @Test
    fun loadMoreTriggeredWhileLoadingRunsAgainAfterCompletion() = runTest(testDispatcher) {
        pokemonRepository.pageToEmit = listOf(pokemon(1))
        pokemonRepository.nextPageResult = Result.Success(false)
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        advanceUntilIdle() // initial load completes (fetchNextPageCount == 1)

        val gate = CompletableDeferred<Unit>()
        pokemonRepository.fetchNextPageGate = gate
        viewModel.onAction(PokedexAction.OnLoadMore)
        runCurrent() // first load-more is now in flight (suspended on the gate)
        assertTrue(viewModel.state.value.isLoadingMore)

        // A second trigger arrives mid-load: it must be remembered, not dropped.
        viewModel.onAction(PokedexAction.OnLoadMore)
        runCurrent()

        gate.complete(Unit)
        advanceUntilIdle() // first load finishes and the pending one catches up
        assertEquals(3, pokemonRepository.fetchNextPageCount)
        collector.cancel()
    }

    @Test
    fun addingToFullTeamEmitsTeamFullEvent() = runTest(testDispatcher) {
        teamRepository.team.value = (1..AddPokemonToTeamUseCase.MAX_TEAM_SIZE)
            .map { TeamMember(pokemon(it), emptyList(), 0L) }
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        val events = mutableListOf<PokedexEvent>()
        val eventCollector = launch { viewModel.events.toList(events) }
        advanceUntilIdle()

        viewModel.onAction(PokedexAction.OnAddToTeam(pokemon(99)))
        advanceUntilIdle()

        assertTrue(events.any { it is PokedexEvent.TeamFull })
        collector.cancel()
        eventCollector.cancel()
    }

    @Test
    fun addingToTeamEmitsAddedEventAndPersists() = runTest(testDispatcher) {
        val viewModel = createViewModel()

        val collector = launch { viewModel.state.collect {} }
        val events = mutableListOf<PokedexEvent>()
        val eventCollector = launch { viewModel.events.toList(events) }
        advanceUntilIdle()

        viewModel.onAction(PokedexAction.OnAddToTeam(pokemon(1)))
        advanceUntilIdle()

        assertTrue(events.any { it is PokedexEvent.AddedToTeam })
        assertEquals(listOf(1), teamRepository.team.value.map { it.pokemon.id })
        collector.cancel()
        eventCollector.cancel()
    }
}

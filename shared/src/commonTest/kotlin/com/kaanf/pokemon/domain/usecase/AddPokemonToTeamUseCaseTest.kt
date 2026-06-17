package com.kaanf.pokemon.domain.usecase

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.TeamMember
import com.kaanf.pokemon.testutil.FakeTeamRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AddPokemonToTeamUseCaseTest {

    private fun pokemon(id: Int) = Pokemon(id = id, name = "p$id", imageUrl = "url$id")

    @Test
    fun addsToTeamWhenSpaceAvailable() = runTest {
        val repository = FakeTeamRepository()
        val useCase = AddPokemonToTeamUseCase(repository)

        val result = useCase(pokemon(1))

        assertTrue(result is Result.Success)
        assertEquals(listOf(1), repository.team.value.map { it.pokemon.id })
    }

    @Test
    fun returnsAlreadyInTeamWhenPokemonAlreadyPresent() = runTest {
        val repository = FakeTeamRepository()
        repository.team.value = listOf(TeamMember(pokemon(1), emptyList(), 0L))
        val useCase = AddPokemonToTeamUseCase(repository)

        val result = useCase(pokemon(1))

        assertEquals(Result.Failure(AddToTeamError.ALREADY_IN_TEAM), result)
        assertEquals(1, repository.team.value.size)
    }

    @Test
    fun returnsTeamFullWhenTeamAtMaxSize() = runTest {
        val repository = FakeTeamRepository()
        repository.team.value = (1..AddPokemonToTeamUseCase.MAX_TEAM_SIZE)
            .map { TeamMember(pokemon(it), emptyList(), 0L) }
        val useCase = AddPokemonToTeamUseCase(repository)

        val result = useCase(pokemon(99))

        assertEquals(Result.Failure(AddToTeamError.TEAM_FULL), result)
        assertEquals(AddPokemonToTeamUseCase.MAX_TEAM_SIZE, repository.team.value.size)
    }

    @Test
    fun mapsStorageFailureToStorageError() = runTest {
        val repository = FakeTeamRepository()
        repository.addResult = Result.Failure(DataError.Local.UNKNOWN)
        val useCase = AddPokemonToTeamUseCase(repository)

        val result = useCase(pokemon(1))

        assertEquals(Result.Failure(AddToTeamError.STORAGE), result)
        assertTrue(repository.team.value.isEmpty())
    }
}

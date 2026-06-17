package com.kaanf.pokemon.data.repository

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.Result
import com.kaanf.pokemon.data.local.entity.PokemonEntity
import com.kaanf.pokemon.domain.model.Ability
import com.kaanf.pokemon.domain.model.Pokemon
import com.kaanf.pokemon.domain.model.PokemonDetail
import com.kaanf.pokemon.domain.model.PokemonPage
import com.kaanf.pokemon.domain.model.PokemonStat
import com.kaanf.pokemon.domain.model.PokemonType
import com.kaanf.pokemon.domain.model.StatType
import com.kaanf.pokemon.testutil.FakePokeApiService
import com.kaanf.pokemon.testutil.FakePokemonDao
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OfflineFirstPokemonRepositoryTest {

    private fun page(itemCount: Int, totalCount: Int) = PokemonPage(
        items = (1..itemCount).map { Pokemon(id = it, name = "p$it", imageUrl = "url$it") },
        totalCount = totalCount,
    )

    private val detail = PokemonDetail(
        id = 1,
        name = "bulbasaur",
        imageUrl = "img",
        shinyImageUrl = "shiny",
        heightDecimeters = 7,
        weightHectograms = 69,
        types = listOf(PokemonType.GRASS),
        stats = listOf(PokemonStat(StatType.HP, 45)),
        abilities = listOf(Ability("overgrow", isHidden = false)),
    )

    @Test
    fun fetchNextPageRequestsOffsetFromCachedCountAndPersistsItems() = runTest {
        val api = FakePokeApiService()
        val dao = FakePokemonDao()
        dao.pokemon.value = listOf(PokemonEntity(1, "p1", "url1"), PokemonEntity(2, "p2", "url2"))
        api.pageResult = Result.Success(page(itemCount = 3, totalCount = 100))
        val repository = OfflineFirstPokemonRepository(api, dao)

        repository.fetchNextPage()

        assertEquals(2, api.lastOffset)
        assertEquals(OfflineFirstPokemonRepository.PAGE_SIZE, api.lastLimit)
        assertEquals(3, dao.upsertedPokemon?.size)
    }

    @Test
    fun fetchNextPageReportsEndReachedWhenLastPageLoaded() = runTest {
        val api = FakePokeApiService()
        val dao = FakePokemonDao()
        api.pageResult = Result.Success(page(itemCount = 5, totalCount = 5))
        val repository = OfflineFirstPokemonRepository(api, dao)

        val result = repository.fetchNextPage()

        assertEquals(Result.Success(true), result)
    }

    @Test
    fun fetchNextPageReportsNotEndedWhenMorePagesRemain() = runTest {
        val api = FakePokeApiService()
        val dao = FakePokemonDao()
        api.pageResult = Result.Success(page(itemCount = 5, totalCount = 50))
        val repository = OfflineFirstPokemonRepository(api, dao)

        val result = repository.fetchNextPage()

        assertEquals(Result.Success(false), result)
    }

    @Test
    fun fetchNextPageDoesNotPersistOnNetworkFailure() = runTest {
        val api = FakePokeApiService()
        val dao = FakePokemonDao()
        api.pageResult = Result.Failure(DataError.Remote.NO_INTERNET)
        val repository = OfflineFirstPokemonRepository(api, dao)

        val result = repository.fetchNextPage()

        assertEquals(Result.Failure(DataError.Remote.NO_INTERNET), result)
        assertNull(dao.upsertedPokemon)
    }

    @Test
    fun fetchDetailPersistsOnSuccess() = runTest {
        val api = FakePokeApiService()
        val dao = FakePokemonDao()
        api.detailResult = Result.Success(detail)
        val repository = OfflineFirstPokemonRepository(api, dao)

        val result = repository.fetchDetail(1)

        assertTrue(result is Result.Success)
        assertEquals(1, dao.upsertDetailCount)
    }

    @Test
    fun fetchDetailDoesNotPersistOnFailure() = runTest {
        val api = FakePokeApiService()
        val dao = FakePokemonDao()
        api.detailResult = Result.Failure(DataError.Remote.NOT_FOUND)
        val repository = OfflineFirstPokemonRepository(api, dao)

        val result = repository.fetchDetail(1)

        assertEquals(Result.Failure(DataError.Remote.NOT_FOUND), result)
        assertEquals(0, dao.upsertDetailCount)
    }
}

package com.kaanf.pokemon.data.remote.mappers

import com.kaanf.pokemon.data.remote.dto.AbilitySlotDto
import com.kaanf.pokemon.data.remote.dto.NamedResourceDto
import com.kaanf.pokemon.data.remote.dto.PokemonDetailDto
import com.kaanf.pokemon.data.remote.dto.PokemonPageDto
import com.kaanf.pokemon.data.remote.dto.StatSlotDto
import com.kaanf.pokemon.data.remote.dto.TypeSlotDto
import com.kaanf.pokemon.domain.model.PokemonType
import com.kaanf.pokemon.domain.model.StatType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PokemonDtoMappersTest {

    @Test
    fun getIdFromUrlParsesTrailingId() {
        assertEquals(25, getIdFromUrl("https://pokeapi.co/api/v2/pokemon/25/"))
        assertEquals(25, getIdFromUrl("https://pokeapi.co/api/v2/pokemon/25"))
    }

    @Test
    fun getIdFromUrlReturnsNullForNonNumericId() {
        assertNull(getIdFromUrl("https://pokeapi.co/api/v2/pokemon/bad/"))
    }

    @Test
    fun pageToDomainDropsEntriesWithUnparseableId() {
        val dto = PokemonPageDto(
            count = 2,
            results = listOf(
                NamedResourceDto(name = "pikachu", url = "https://pokeapi.co/api/v2/pokemon/25/"),
                NamedResourceDto(name = "broken", url = "https://pokeapi.co/api/v2/pokemon/x/"),
            ),
        )

        val page = dto.toDomain()

        assertEquals(1, page.items.size)
        assertEquals(25, page.items.first().id)
        assertEquals(getArtworkUrl(25), page.items.first().imageUrl)
        assertEquals(2, page.totalCount)
    }

    @Test
    fun detailToDomainSortsTypesBySlotAndMapsUnknown() {
        val dto = PokemonDetailDto(
            id = 1,
            name = "bulbasaur",
            height = 7,
            weight = 69,
            types = listOf(
                TypeSlotDto(slot = 2, type = NamedResourceDto("poison", "")),
                TypeSlotDto(slot = 1, type = NamedResourceDto("grass", "")),
                TypeSlotDto(slot = 3, type = NamedResourceDto("mystery", "")),
            ),
            stats = listOf(StatSlotDto(baseStat = 45, stat = NamedResourceDto("hp", ""))),
            abilities = listOf(AbilitySlotDto(ability = NamedResourceDto("overgrow", ""), isHidden = true)),
        )

        val detail = dto.toDomain()

        assertEquals(
            listOf(PokemonType.GRASS, PokemonType.POISON, PokemonType.UNKNOWN),
            detail.types,
        )
        assertEquals(StatType.HP, detail.stats.first().type)
        assertEquals(45, detail.stats.first().value)
        assertEquals("overgrow", detail.abilities.first().name)
        assertEquals(true, detail.abilities.first().isHidden)
    }
}

package com.kaanf.pokemon.core.di

import com.kaanf.pokemon.presentation.detail.DetailViewModel
import com.kaanf.pokemon.presentation.home.HomeViewModel
import com.kaanf.pokemon.presentation.pokedex.PokedexViewModel
import com.kaanf.pokemon.presentation.settings.SettingsViewModel
import com.kaanf.pokemon.presentation.team.TeamViewModel
import org.koin.core.parameter.parametersOf
import org.koin.mp.KoinPlatform

fun pokedexViewModel(): PokedexViewModel = KoinPlatform.getKoin().get()

fun teamViewModel(): TeamViewModel = KoinPlatform.getKoin().get()

fun homeViewModel(): HomeViewModel = KoinPlatform.getKoin().get()

fun settingsViewModel(): SettingsViewModel = KoinPlatform.getKoin().get()

fun detailViewModel(pokemonId: Int): DetailViewModel =
    KoinPlatform.getKoin().get { parametersOf(pokemonId) }

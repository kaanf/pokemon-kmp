package com.kaanf.pokemon.core.di

import com.kaanf.pokemon.core.database.createPokemonDatabase
import com.kaanf.pokemon.data.local.dao.PokemonDao
import com.kaanf.pokemon.data.local.dao.SqlDelightPokemonDao
import com.kaanf.pokemon.data.local.dao.SqlDelightTeamDao
import com.kaanf.pokemon.data.local.dao.TeamDao
import com.kaanf.pokemon.data.remote.KtorPokeApiService
import com.kaanf.pokemon.data.repository.DataStoreSettingsRepository
import com.kaanf.pokemon.data.repository.OfflineFirstPokemonRepository
import com.kaanf.pokemon.data.repository.SqlDelightTeamRepository
import com.kaanf.pokemon.domain.repository.PokemonRepository
import com.kaanf.pokemon.domain.repository.SettingsRepository
import com.kaanf.pokemon.domain.repository.TeamRepository
import com.kaanf.pokemon.domain.service.PokeApiService
import com.kaanf.pokemon.domain.usecase.AddPokemonToTeamUseCase
import com.kaanf.pokemon.domain.usecase.ObserveTeamCountUseCase
import com.kaanf.pokemon.presentation.detail.DetailViewModel
import com.kaanf.pokemon.presentation.home.HomeViewModel
import com.kaanf.pokemon.presentation.pokedex.PokedexViewModel
import com.kaanf.pokemon.presentation.settings.SettingsViewModel
import com.kaanf.pokemon.presentation.team.TeamViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val coreModule = module {
    single { createPokemonDatabase(get()) }
    single<PokemonDao> { SqlDelightPokemonDao(get()) }
    single<TeamDao> { SqlDelightTeamDao(get()) }
}

val dataModule = module {
    singleOf(::KtorPokeApiService) bind PokeApiService::class
    singleOf(::OfflineFirstPokemonRepository) bind PokemonRepository::class
    singleOf(::SqlDelightTeamRepository) bind TeamRepository::class
    singleOf(::DataStoreSettingsRepository) bind SettingsRepository::class
}

val domainModule = module {
    factoryOf(::AddPokemonToTeamUseCase)
    factoryOf(::ObserveTeamCountUseCase)
}

val presentationModule = module {
    viewModelOf(::PokedexViewModel)
    viewModelOf(::TeamViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SettingsViewModel)
    viewModel { (pokemonId: Int) ->
        DetailViewModel(
            pokemonId = pokemonId,
            pokemonRepository = get(),
            settingsRepository = get(),
        )
    }
}

val sharedModules = listOf(
    platformModule,
    coreModule,
    dataModule,
    domainModule,
    presentationModule,
)

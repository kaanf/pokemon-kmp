package com.kaanf.pokemon.core.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import com.kaanf.pokemon.core.database.DatabaseFactory
import com.kaanf.pokemon.core.network.ConnectivityObserver
import com.kaanf.pokemon.core.network.HttpClientFactory
import com.kaanf.pokemon.core.network.IosConnectivityObserver
import com.kaanf.pokemon.core.preferences.createDataStore
import io.ktor.client.engine.darwin.Darwin
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.Platform
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(ExperimentalNativeApi::class)
actual val platformModule: Module = module {
    single {
        HttpClientFactory.create(
            engine = Darwin.create(),
            enableLogging = Platform.isDebugBinary,
        )
    }
    single<SqlDriver> { DatabaseFactory().create() }
    single<DataStore<Preferences>> { createDataStore() }
    single<ConnectivityObserver> { IosConnectivityObserver() }
}

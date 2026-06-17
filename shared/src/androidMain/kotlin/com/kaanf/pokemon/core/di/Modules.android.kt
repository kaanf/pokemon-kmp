package com.kaanf.pokemon.core.di

import android.content.pm.ApplicationInfo
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import app.cash.sqldelight.db.SqlDriver
import com.kaanf.pokemon.core.database.DatabaseFactory
import com.kaanf.pokemon.core.network.AndroidConnectivityObserver
import com.kaanf.pokemon.core.network.ConnectivityObserver
import com.kaanf.pokemon.core.network.HttpClientFactory
import com.kaanf.pokemon.core.preferences.createDataStore
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single {
        HttpClientFactory.create(
            engine = OkHttp.create(),
            enableLogging = androidContext().isDebuggable,
        )
    }
    single<SqlDriver> { DatabaseFactory(androidContext()).create() }
    single<DataStore<Preferences>> { createDataStore(androidContext()) }
    single<ConnectivityObserver> { AndroidConnectivityObserver(androidContext()) }
}

private val android.content.Context.isDebuggable: Boolean
    get() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

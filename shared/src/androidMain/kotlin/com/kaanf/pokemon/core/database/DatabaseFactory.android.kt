package com.kaanf.pokemon.core.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseFactory(
    private val context: Context,
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(
            schema = PokemonDatabase.Schema,
            context = context.applicationContext,
            name = DB_NAME,
        )
    }
}

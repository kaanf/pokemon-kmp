package com.kaanf.pokemon.core.database

import app.cash.sqldelight.db.SqlDriver

const val DB_NAME = "pokemon.db"

expect class DatabaseFactory {
    fun create(): SqlDriver
}

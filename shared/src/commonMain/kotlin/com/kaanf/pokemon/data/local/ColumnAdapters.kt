package com.kaanf.pokemon.data.local

import app.cash.sqldelight.ColumnAdapter
import com.kaanf.pokemon.data.local.entity.AbilityField
import com.kaanf.pokemon.data.local.entity.StatField
import kotlinx.serialization.json.Json

val stringListAdapter: ColumnAdapter<List<String>, String>
    get() = jsonColumnAdapter()
val statFieldsAdapter: ColumnAdapter<List<StatField>, String>
    get() = jsonColumnAdapter()
val abilityFieldsAdapter: ColumnAdapter<List<AbilityField>, String>
    get() = jsonColumnAdapter()

private inline fun <reified T : Any> jsonColumnAdapter(): ColumnAdapter<T, String> {
    return object : ColumnAdapter<T, String> {
        override fun decode(databaseValue: String): T = Json.decodeFromString(databaseValue)
        override fun encode(value: T): String = Json.encodeToString(value)
    }
}

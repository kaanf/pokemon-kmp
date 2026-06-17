package com.kaanf.pokemon.core.database

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.core.common.Result
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> safeDatabaseUpdate(update: suspend () -> T): Result<T, DataError.Local> {
    return try {
        Result.Success(update())
    } catch (e: CancellationException) {
        throw e
    } catch (_: Exception) {
        Result.Failure(DataError.Local.UNKNOWN)
    }
}

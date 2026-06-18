package com.kaanf.pokemon

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import com.kaanf.pokemon.core.di.initKoin
import okio.Path.Companion.toOkioPath
import org.koin.android.ext.koin.androidContext

class PokemonApplication : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PokemonApplication)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, percent = 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache").toOkioPath())
                    .maxSizeBytes(128L * 1024 * 1024)
                    .build()
            }
            .build()
}

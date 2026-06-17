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

private const val IMAGE_DISK_CACHE_MAX_BYTES = 128L * 1024 * 1024

class PokemonApplication : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PokemonApplication)
        }
    }

    // Replaces Coil's implicit defaults with explicitly sized caches: the
    // Pokédex grid scrolls through hundreds of ~475x475 artwork PNGs, so cap
    // memory at a quarter of the app budget and give the disk cache enough
    // room that previously seen pages never re-download.
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
                    .maxSizeBytes(IMAGE_DISK_CACHE_MAX_BYTES)
                    .build()
            }
            .build()
}

package ru.limeek.profirutest.interactors

import android.graphics.Bitmap
import ru.limeek.profirutest.repository.LruBitmapCache

class FriendViewHolderInteractor {
    private val cache = LruBitmapCache.instance

    fun getBitmapFromCache(url: String): Bitmap?{
        return cache.getBitmap(url)
    }
}
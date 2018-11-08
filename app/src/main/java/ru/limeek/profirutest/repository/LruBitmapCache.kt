package ru.limeek.profirutest.repository

import android.graphics.Bitmap
import android.support.v4.util.LruCache

class LruBitmapCache : LruCache<String, Bitmap>((Runtime.getRuntime().maxMemory() / 1024).toInt()/8) {
    companion object {
        var instance = LruBitmapCache()
    }

    override fun sizeOf(key: String?, value: Bitmap?): Int {
            return value!!.byteCount/1024
    }

    fun putBitmap(url: String, bitmap: Bitmap){
        put(url, bitmap)
    }

    fun getBitmap(url: String): Bitmap?{
        return get(url)
    }
}
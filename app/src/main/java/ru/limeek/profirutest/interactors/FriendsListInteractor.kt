package ru.limeek.profirutest.interactors

import android.graphics.Bitmap
import com.vk.sdk.api.VKApi
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.model.VKApiUser
import ru.limeek.profirutest.repository.LruBitmapCache

class FriendsListInteractor{
    private var cache = LruBitmapCache.instance

    fun getFriendList() : VKRequest{
        return VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, VKApiUser.FIELD_PHOTO_200))
    }

    fun putBitmapInCache(url: String, bitmap: Bitmap){
        cache.putBitmap(url, bitmap)
    }
}
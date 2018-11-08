package ru.limeek.profirutest.interactors

import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.model.VKPhotoArray

class PhotoInteractor {
    fun getProfilePhotos(userId: Int): VKRequest{
        return VKRequest("photos.get", VKParameters.from("owner_id", userId.toString(), "album_id", "profile", "rev", "1", "extended", "1",
                VKApiConst.FIELDS, "photo_604", "likes"), VKPhotoArray::class.java)
    }
}
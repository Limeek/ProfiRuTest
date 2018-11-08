package ru.limeek.profirutest.view

import com.vk.sdk.api.model.VKApiUser

interface IFriendViewHolder {
    fun update(user: VKApiUser)
}
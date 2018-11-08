package ru.limeek.profirutest.presenters

import android.graphics.Bitmap

interface IFriendViewHolderPresenter {
    fun getBitmap(url: String): Bitmap?
}
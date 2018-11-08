package ru.limeek.profirutest.view

import android.graphics.Bitmap

interface IPhotoView {
    fun setBitmap(bitmap: Bitmap)
    fun getUserId() : Int
    fun showProgressBar()
    fun hideProgressBar()
    fun showErrorLoading()
    fun updatePhotoDescr(likes: Int)
}
package ru.limeek.profirutest.view

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_photo.*
import ru.limeek.profirutest.R
import ru.limeek.profirutest.presenters.IPhotoPresenter
import ru.limeek.profirutest.presenters.PhotoPresenter

class PhotoActivity : IPhotoView, AppCompatActivity() {
    private val presenter : IPhotoPresenter = PhotoPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_photo)
        progressBar.indeterminateDrawable.setColorFilter(Color.parseColor("#FFFFFF"), android.graphics.PorterDuff.Mode.MULTIPLY)
        presenter.onCreate()
    }

    override fun getUserId(): Int {
        return intent.extras.getInt("userId")
    }

    override fun setBitmap(bitmap: Bitmap) {
        ivPhoto.setImageBitmap(bitmap)
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showErrorLoading() {
        Toast.makeText(this,R.string.error_photo_request,Toast.LENGTH_SHORT).show()
    }

    override fun updatePhotoDescr(likes: Int) {
        tvLikeCount.text = likes.toString()
    }
}
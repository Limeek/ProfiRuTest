package ru.limeek.profirutest.presenters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import com.vk.sdk.api.VKError
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKPhotoArray
import ru.limeek.profirutest.interactors.PhotoInteractor
import ru.limeek.profirutest.view.IPhotoView
import java.net.URL

class PhotoPresenter(val view: IPhotoView): IPhotoPresenter {
    var interactor = PhotoInteractor()

    override fun onCreate(){
        view.showProgressBar()
        getPhoto()
    }

    private fun getPhoto(){
        interactor.getProfilePhotos(view.getUserId()).executeWithListener(object : VKRequest.VKRequestListener(){
            override fun onError(error: VKError?) {
                super.onError(error)
                view.showErrorLoading()
            }

            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                val photos = response!!.parsedModel as VKPhotoArray
                if(photos.isNotEmpty()) {
                    DownloadPhoto(photos[0].photo_604, this@PhotoPresenter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    view.updatePhotoDescr(photos[0].likes)
                }
                else {
                    DownloadPhoto("http://vk.com/images/camera_a.gif", this@PhotoPresenter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                    view.updatePhotoDescr(0)
                }
            }
        })
    }

    class DownloadPhoto(var url: String,var presenter: PhotoPresenter): AsyncTask<String,Any,Bitmap>(){
        override fun doInBackground(vararg params: String?): Bitmap{
            return BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
        }

        override fun onPostExecute(result: Bitmap) {
            super.onPostExecute(result)
            presenter.view.hideProgressBar()
            presenter.view.setBitmap(result)
        }
    }
}
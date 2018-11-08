package ru.limeek.profirutest.presenters

import android.graphics.Bitmap
import ru.limeek.profirutest.interactors.FriendViewHolderInteractor
import ru.limeek.profirutest.view.IFriendViewHolder

class FriendViewHolderPresenter(val view: IFriendViewHolder): IFriendViewHolderPresenter {
    private var interactor = FriendViewHolderInteractor()

    override fun getBitmap(url: String): Bitmap? {
        return interactor.getBitmapFromCache(url)
    }
}
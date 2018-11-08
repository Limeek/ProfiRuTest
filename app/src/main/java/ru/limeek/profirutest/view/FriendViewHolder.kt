package ru.limeek.profirutest.view

import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.vk.sdk.api.model.VKApiUser
import kotlinx.android.synthetic.main.item_friend.view.*
import ru.limeek.profirutest.R
import ru.limeek.profirutest.presenters.FriendViewHolderPresenter
import ru.limeek.profirutest.presenters.IFriendViewHolderPresenter


class FriendViewHolder(view: View) : IFriendViewHolder,RecyclerView.ViewHolder(view) {
    var presenter :IFriendViewHolderPresenter = FriendViewHolderPresenter(this)

    override fun update(user: VKApiUser){
        itemView.tvName.text = itemView.context.getString(R.string.first_name_last_name, user.first_name, user.last_name)
        itemView.ivPhoto.setImageBitmap(presenter.getBitmap(user.photo_200))
        itemView.ivPhoto.setOnClickListener{
            startPhotoActivity(user.id, presenter.getBitmap(user.photo_200)!!)
        }
    }

    private fun startPhotoActivity(userId: Int, bitmap: Bitmap){
        Log.wtf("id",userId.toString())
        val intent = Intent(itemView.context, PhotoActivity::class.java)
        intent.putExtra("userId", userId)
        itemView.context.startActivity(intent,ActivityOptionsCompat.makeThumbnailScaleUpAnimation(itemView.ivPhoto, bitmap, 0,0).toBundle())
    }
}
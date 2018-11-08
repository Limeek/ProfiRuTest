package ru.limeek.profirutest.presenters

import android.support.v7.widget.RecyclerView
import ru.limeek.profirutest.view.IFriendViewHolder

interface IFriendsListPresenter {
    fun getItemCount(): Int
    fun onBind(position: Int, viewHolder: IFriendViewHolder)
    fun getScrollListener() : RecyclerView.OnScrollListener
}
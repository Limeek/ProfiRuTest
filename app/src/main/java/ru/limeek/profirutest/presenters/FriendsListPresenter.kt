package ru.limeek.profirutest.presenters

import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse
import com.vk.sdk.api.model.VKApiUser
import ru.limeek.profirutest.adapters.FriendsListAdapter
import ru.limeek.profirutest.interactors.FriendsListInteractor
import ru.limeek.profirutest.view.IFriendViewHolder
import java.net.URL

class FriendsListPresenter(var adapter: FriendsListAdapter): IFriendsListPresenter {
    private val interactor = FriendsListInteractor()
    private var allFriendsList : List<VKApiUser> = listOf()
    private var visibleFriendList : MutableList<VKApiUser?> = mutableListOf()
    private val friendsIteration = 30

    private var loading = false

    init {
        getFriends()
    }

    private fun getFriends(){
        interactor.getFriendList().executeWithListener(object: VKRequest.VKRequestListener(){
            override fun attemptFailed(request: VKRequest?, attemptNumber: Int, totalAttempts: Int) {
                super.attemptFailed(request, attemptNumber, totalAttempts)
                adapter.showErrorFriendsLoad()
            }
            override fun onComplete(response: VKResponse?) {
                super.onComplete(response)
                allFriendsList = response?.parsedModel as List<VKApiUser>
                LoadPhotosToCache(listNextIterationUsers(),this@FriendsListPresenter).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }
        })
    }

    private fun listNextIterationUsers() : List<VKApiUser>{
        return if(allFriendsList.size - visibleFriendList.size >= friendsIteration)
            allFriendsList.subList(visibleFriendList.size, visibleFriendList.size+ friendsIteration)
        else allFriendsList.subList(visibleFriendList.size, visibleFriendList.size + (allFriendsList.size - visibleFriendList.size))
    }

    override fun getItemCount(): Int {
        return visibleFriendList.size
    }

    override fun onBind(position: Int, viewHolder: IFriendViewHolder){
        viewHolder.update(visibleFriendList[position]!!)
    }

    override fun getScrollListener() : RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                adapter.moveProgressBarToBottom()
                val totalItem = recyclerView!!.layoutManager.itemCount
                val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                when {
                    loading && lastVisibleItem != totalItem - 1 -> adapter.hideProgressBar()
                    loading && lastVisibleItem == totalItem - 1 -> adapter.showProgressBar()
                    !loading && lastVisibleItem == totalItem - 1 -> {
                        loading = true
                        LoadPhotosToCache(listNextIterationUsers(),this@FriendsListPresenter).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR)
                    }
                }
            }
        }
    }

    class LoadPhotosToCache(var users: List<VKApiUser>,var presenter: FriendsListPresenter) : AsyncTask<String, Any, Any>(){
        override fun onPreExecute() {
            super.onPreExecute()
            if(presenter.visibleFriendList.size != presenter.allFriendsList.size) presenter.adapter.showProgressBar()
        }

        override fun doInBackground(vararg params: String?) {
            for(user : VKApiUser in users){
                val bitmap = BitmapFactory.decodeStream(URL(user.photo_200).openConnection().getInputStream())
                presenter.interactor.putBitmapInCache(user.photo_200, bitmap)
            }
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            presenter.visibleFriendList.addAll(users)
            presenter.loading = false
            if(presenter.visibleFriendList.size == presenter.allFriendsList.size) presenter.adapter.setRecViewPaddingToZero()
            presenter.adapter.hideProgressBar()
            presenter.adapter.notifyDataSetChanged()
        }
    }
}
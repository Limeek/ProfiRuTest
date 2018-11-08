package ru.limeek.profirutest.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import ru.limeek.profirutest.R
import ru.limeek.profirutest.presenters.FriendsListPresenter
import ru.limeek.profirutest.presenters.IFriendsListPresenter
import ru.limeek.profirutest.view.FriendViewHolder

class FriendsListAdapter(var progressBar: ProgressBar) : RecyclerView.Adapter<FriendViewHolder>() {
    private lateinit var context: Context
    private var presenter: IFriendsListPresenter = FriendsListPresenter(this)
    private lateinit var recyclerView : RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_friend, parent, false))
    }

    override fun getItemCount(): Int {
        return presenter.getItemCount()
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        presenter.onBind(position, holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
        recyclerView.addOnScrollListener(presenter.getScrollListener())
        this.recyclerView = recyclerView
    }

    fun showErrorFriendsLoad(){
        Toast.makeText(context,R.string.error_friends_request,Toast.LENGTH_LONG).show()
    }

    fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(){
        progressBar.visibility = View.GONE
    }

    fun setRecViewPaddingToZero(){
        recyclerView.setPadding(0,0,0,0)
    }

    fun moveProgressBarToBottom(){
        val layoutParams = progressBar.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topToTop = ConstraintLayout.LayoutParams.UNSET
        progressBar.layoutParams = layoutParams
    }
}
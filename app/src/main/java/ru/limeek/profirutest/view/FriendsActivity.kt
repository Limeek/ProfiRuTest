package ru.limeek.profirutest.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.vk.sdk.VKSdk
import kotlinx.android.synthetic.main.activity_friends_list.*
import ru.limeek.profirutest.R
import ru.limeek.profirutest.adapters.FriendsListAdapter

class FriendsActivity : IFriendsView, AppCompatActivity() {
    lateinit var recAdapter: FriendsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_list)
        progressBar.indeterminateDrawable.setColorFilter(Color.parseColor("#3F51B5"), android.graphics.PorterDuff.Mode.MULTIPLY)
        initRecycler()
    }

    override fun showErrorFriendsLoad() {
        Toast.makeText(this,R.string.error_friends_request,Toast.LENGTH_SHORT).show()
    }

    private fun initRecycler(){
        recAdapter = FriendsListAdapter(progressBar)
        recView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(this@FriendsActivity)
            adapter = recAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_friends, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.itemLogout -> {
                logOutAndStartLoginActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun refreshRecycler() {
        recAdapter.notifyDataSetChanged()
    }

    private fun logOutAndStartLoginActivity(){
        VKSdk.logout()
        val intent =  Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
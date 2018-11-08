package ru.limeek.profirutest.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import kotlinx.android.synthetic.main.activity_login.*
import ru.limeek.profirutest.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if(VKSdk.isLoggedIn()){
           startFriendsActivity()
        }
        btnLogin.setOnClickListener {
            VKSdk.login(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(VKSdk.onActivityResult(requestCode,resultCode,data,object:VKCallback<VKAccessToken>{
                        override fun onResult(res: VKAccessToken?) {
                            startFriendsActivity()
                        }

                        override fun onError(error: VKError?) {
                            Toast.makeText(this@LoginActivity, R.string.error_login, Toast.LENGTH_LONG).show()
                        }
                    }
                )
        ){
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun startFriendsActivity(){
        val intent = Intent(this@LoginActivity, FriendsActivity::class.java)
        startActivity(intent)
        finish()
    }
}

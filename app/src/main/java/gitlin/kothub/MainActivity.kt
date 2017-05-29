package gitlin.kothub

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

import gitlin.kothub.R.layout.activity_main
import gitlin.kothub.github.LoginActivity
import gitlin.kothub.github.OAuthValues
import gitlin.kothub.ui.AppDrawer
import gitlin.kothub.utilities.getOAuthToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug

class MainActivity : AppCompatActivity(), AnkoLogger {

    fun initOAuth () {
        OAuthValues.REDIRECT_URL = "oauth://kothub"
        OAuthValues.GITHUB_CLIENT = getString(R.string.github_client)
        OAuthValues.GITHUB_SECRET = getString(R.string.github_secret)

        val token: String? = getOAuthToken()
        if (token != null) {
            OAuthValues.GITHUB_TOKEN = token
            OAuthValues.isLoggedIn = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        setSupportActionBar(toolbar)
//        AppDrawer(this, toolbar)
        tv_hello.text = "Kothub"
    }

    override fun onStart() {
        super.onStart()
        initOAuth()

        debug(OAuthValues.isLoggedIn)
        if (OAuthValues.isLoggedIn) {
            startActivity(Intent(this, ProfileActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}

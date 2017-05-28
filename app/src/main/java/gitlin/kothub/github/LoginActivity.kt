package gitlin.kothub.github


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import gitlin.kothub.R
import gitlin.kothub.utilities.editSharedPreferences


class LoginActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun openWebView () {

        val intent = Intent(this, LoginWebViewActivity::class.java)
        intent.putExtra("url", authorizeUrl())
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {

        super.onActivityResult(requestCode, resultCode, intent)

        if (intent != null && requestCode == 0) {

            val code = intent.data.getQueryParameter("code")

            requestAccessToken(code, { error, token ->
                if (error == null) {
                    Log.d("OAuthToken", token)
                    OAuthValues.isLoggedIn = true
                    OAuthValues.GITHUB_TOKEN = token!!

                    editSharedPreferences {
                        getString(R.string.oauth_github_token) to token
                    }
                } else {
                    // Handle error
                }
            })


        }
    }

    fun onLoginClick (view: View) {
        openWebView()
    }
}
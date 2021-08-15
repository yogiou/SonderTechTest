package jie.wen.sonder

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import jie.wen.sonder.other.Constants.Companion.DELAY
import org.jetbrains.anko.intentFor
import java.util.*
import kotlin.concurrent.schedule

// splash screen
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        supportActionBar?.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window?.setDecorFitsSystemWindows(false)
        } else {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        transitToMainActivity()
    }

    private fun transitToMainActivity() {
        Timer("SplashTimer", false).schedule(DELAY) {
            startActivity(intentFor<MainActivity>())
            finish()
        }
    }
}
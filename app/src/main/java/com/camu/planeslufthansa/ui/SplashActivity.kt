package com.camu.planeslufthansa.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.camu.planeslufthansa.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DURATION: Long = 1000 // Duración del splash (3 segundos)
    private lateinit var mp: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Después del tiempo especificado, inicia la actividad principal

            //Reproducción de un audio local
            mp = MediaPlayer.create(this, R.raw.avion)
            mp.start()

            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DURATION)
    }
}
package com.kelompoktiga.cutiepaw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getStarted: Button = findViewById(R.id.btnGetStarted)

        getStarted.setOnClickListener {
            val sendIntent = Intent(this, RegisActivity::class.java)
            startActivity(sendIntent)
        }
    }
}
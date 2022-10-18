package com.kelompoktiga.cutiepaw

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val email: Button = findViewById(R.id.btnMail)

        email.setOnClickListener() {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val emailRecipient: String = "s32200091@student.ubm.ac.id"
        val emailSubject: String = Uri.encode("Cutie Paw")
        val emailMsg: String = Uri.encode("Hello, I have a question about your app.")
        val sendTo = "mailto:$emailRecipient?&subject=$emailSubject&body=$emailMsg"
        val sendEmailIntent = Intent(Intent.ACTION_SENDTO)
        sendEmailIntent.setData(Uri.parse(sendTo))
        try {
            startActivity(Intent.createChooser(sendEmailIntent, "Sending email..."))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error sending email...", Toast.LENGTH_SHORT).show()
        }
    }
}
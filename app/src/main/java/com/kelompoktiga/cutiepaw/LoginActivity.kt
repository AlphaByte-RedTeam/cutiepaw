package com.kelompoktiga.cutiepaw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val etEmail: EditText by lazy { findViewById(R.id.etEmailLogin) }
    private val etPassword: EditText by lazy { findViewById(R.id.etPasswordLogin) }

    private lateinit var email: String
    private lateinit var password: String

    // Init Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvFooter: TextView = findViewById(R.id.txtFooterLogin)
        val clearButton: Button = findViewById(R.id.btnClearLogin)
        val cancelButton: Button = findViewById(R.id.btnCancel)
        val loginButton: Button = findViewById(R.id.btnLogin)

        // Initialize Firebase Auth
        auth = Firebase.auth

        clearButton.setOnClickListener {
            clearForm()
            focusToEmail()
        }

        cancelButton.setOnClickListener {
            finish()
            System.exit(0)
        }

        loginButton.setOnClickListener {
            email = etEmail.text.toString()
            password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty())
                Snackbar.make(it, "Email dan Password tidak boleh kosong", Snackbar.LENGTH_SHORT).show()
            else {
               auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            Snackbar.make(it, "Email atau Password salah", Snackbar.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        val foregroundColorSpan = ForegroundColorSpan(getColor(R.color.pine_tree))
        val spannableString = SpannableString(tvFooter.text.toString())
        val fontWeightSpan = StyleSpan(R.font.inter_bold)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                toRegis()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        spannableString.setSpan(
            fontWeightSpan,
            23,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            foregroundColorSpan,
            23,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            clickableSpan,
            23,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvFooter.text = spannableString
        tvFooter.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun toRegis() {
        val sendIntent = Intent(this@LoginActivity, RegisActivity::class.java)
        startActivity(sendIntent)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val sendIntent = Intent(this@LoginActivity, CatalogueActivity::class.java)
            startActivity(sendIntent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly
        val currentUser = auth.currentUser
        if (currentUser != null)
            reload()
    }

    private fun reload() {
        
    }

    private fun clearForm() {
        email.text.clear()
        password.text.clear()
    }

    private fun focusToEmail() {
        email.focusable = View.FOCUSABLE
        email.requestFocus()
    }
}
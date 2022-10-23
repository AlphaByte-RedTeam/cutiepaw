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
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisActivity : AppCompatActivity() {

    private val etName: EditText by lazy { findViewById(R.id.etNameRegis) }
    private val etEmail: EditText by lazy { findViewById(R.id.etEmailRegis) }
    private val etPassword: EditText by lazy { findViewById(R.id.etPasswordRegis) }

    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String

    // Init Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regis)

        val tvFooter: TextView = findViewById(R.id.txtFooterRegis)
        val clearButton: Button = findViewById(R.id.btnClearRegis)
        val backButton: Button = findViewById(R.id.btnBack)
        val registerButton: Button = findViewById(R.id.btnRegis)

        // Initialize Firebase Auth
        auth = Firebase.auth

        clearButton.setOnClickListener {
            clearForm()
            focusToName()
        }

        backButton.setOnClickListener {
            toLogin()
        }

        registerButton.setOnClickListener {
            name = etName.text.toString()
            email = etEmail.text.toString()
            password = etPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty())
                Snackbar.make(it, "Semua field harus diisi", Snackbar.LENGTH_SHORT).show()
            else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            updateUI(user, name)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:falure", task.exception)
                            Snackbar.make(it, "Authentication failed.", Snackbar.LENGTH_SHORT)
                                .show()
                            updateUI(null, null)
                        }
                    }
            }
        }

        val foregroundColorSpan = ForegroundColorSpan(getColor(R.color.pine_tree))
        val spannableString = SpannableString(tvFooter.text.toString())
        val fontWeightSpan = StyleSpan(R.font.inter_bold)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                toLogin()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        spannableString.setSpan(
            fontWeightSpan,
            25,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            foregroundColorSpan,
            25,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            clickableSpan,
            25,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvFooter.text = spannableString
        tvFooter.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun updateUI(currentUser: FirebaseUser?, name: String?) {
        if (currentUser != null) {
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }

            currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener {
                    toCatalogue()
                }
        }
    }

    private fun updateUsername(user: FirebaseUser?, name: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }
    }

    private fun toCatalogue() {
        val intent = Intent(this, CatalogueActivity::class.java)
        startActivity(intent)
    }

    private fun toLogin() {
        val sendIntent = Intent(this@RegisActivity, LoginActivity::class.java)
        startActivity(sendIntent)
    }

    private fun focusToName() {
        etName.focusable = View.FOCUSABLE
        etName.requestFocus()
    }

    private fun clearForm() {
        etName.text.clear()
        etEmail.text.clear()
        etPassword.text.clear()
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

    companion object {
        private const val TAG = "EmailPassword"
    }
}
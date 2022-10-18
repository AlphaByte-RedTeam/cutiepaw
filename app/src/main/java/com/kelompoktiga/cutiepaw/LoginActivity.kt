package com.kelompoktiga.cutiepaw

import android.content.Intent
import android.graphics.Typeface
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
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvFooter: TextView = findViewById(R.id.txtFooterLogin)

        val foregroundColorSpan: ForegroundColorSpan = ForegroundColorSpan(getColor(R.color.pine_tree))
        val spannableString: SpannableString = SpannableString(tvFooter.text.toString())
        val fontWeightSpan = StyleSpan(R.font.inter_bold)
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val sendIntent: Intent = Intent(this@LoginActivity, CatalogueActivity::class.java)
                startActivity(sendIntent)
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
}
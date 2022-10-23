package com.kelompoktiga.cutiepaw

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val ITEM_INDEX = "item_index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val itemIndex = intent.getIntExtra(ITEM_INDEX, -1)

        val backArrow = findViewById<ImageButton>(R.id.btnBack)

        val productName = findViewById<TextView>(R.id.txtProductName)
        val productPrice = findViewById<TextView>(R.id.txtProductPrice)
        val productDesc = findViewById<TextView>(R.id.txtProductDesc)
        val productImg = findViewById<ImageView>(R.id.imgProduct)

        backArrow.setOnClickListener {
            val sendIntent = Intent(this, CatalogueActivity::class.java)
            startActivity(sendIntent)
        }

        productName.text = productsList[itemIndex].name

        productPrice.text = productsList[itemIndex].price

        productDesc.text = productsList[itemIndex].desc

        productImg.setImageResource(productsList[itemIndex].img)
    }
}
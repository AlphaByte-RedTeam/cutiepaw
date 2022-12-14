package com.kelompoktiga.cutiepaw

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CatalogueActivity : AppCompatActivity() {
    private var myProductList = ArrayList<Product>()

    private var customAdapter: CustomAdapter? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        val greeting: TextView = findViewById(R.id.greetingMsg)
        val gridView: GridView = findViewById(R.id.gridView)
        val contactUs: ImageButton = findViewById(R.id.contactUs)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        val name: String = auth.currentUser!!.displayName.toString()

        if (currentUser == null) {
            toLogin()
        }

        greeting.text = "Hi, $name 👋"

        contactUs.setOnClickListener{
            val sendIntent = Intent(this, ContactActivity::class.java)
            startActivity(sendIntent)
        }

        for (product in productsList) {
            myProductList.add(product)
        }

        customAdapter = CustomAdapter(myProductList, this)

        gridView.adapter = customAdapter

        gridView.setOnItemClickListener { _, _, i, _ -> toDetail(i) }
    }

    private fun toLogin() {
        val sendIntent = Intent(this, LoginActivity::class.java)
        startActivity(sendIntent)
    }

    private fun toDetail(itemIndex: Int) {
        val sendIntent = Intent(this, DetailsActivity::class.java)
        sendIntent.putExtra(DetailsActivity.ITEM_INDEX, itemIndex)
        startActivity(sendIntent)
    }

    class CustomAdapter(
        private var imgList: ArrayList<Product>,
        var context: Context
    ) : BaseAdapter() {

        override fun getCount(): Int {
            return imgList.size
        }

        override fun getItem(position: Int): Any {
            return imgList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, p1: View?, viewGroup: ViewGroup?): View {
            var view = p1
            if (view == null)
                view =
                    LayoutInflater.from(context).inflate(R.layout.row_grid_item, viewGroup, false)
            val imgView = view!!.findViewById<ImageView>(R.id.imgProduct)
            val tvProduct = view.findViewById<TextView>(R.id.txtProductName)
            val tvPrice = view.findViewById<TextView>(R.id.txtProductPrice)

            val imgModal = imgList[position]

            imgView.setImageResource(imgModal.img)
            tvProduct.text = imgModal.name
            tvPrice.text = imgModal.price

            return view
        }

    }
}
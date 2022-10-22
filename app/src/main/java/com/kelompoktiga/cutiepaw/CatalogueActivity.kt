package com.kelompoktiga.cutiepaw

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView

class CatalogueActivity : AppCompatActivity() {

    private val productsList = arrayOf(
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
        Product(R.drawable.pet_food, "Pawly - Dog Food DQP", "Rp 99.999"),
    )

    private var myProductList = ArrayList<Product>()

    private var customAdapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogue)

        val greeting: TextView = findViewById(R.id.greetingMsg)
        val gridView: GridView = findViewById(R.id.gridView)

        for (product in productsList) {
            myProductList.add(product)
        }

        customAdapter = CustomAdapter(myProductList, this)

        gridView.adapter = customAdapter
    }

    class CustomAdapter(
        private var imgList: ArrayList<Product>,
        var context: Context
        ): BaseAdapter() {

        override fun getCount(): Int {
            TODO("Not yet implemented")
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
                view = LayoutInflater.from(context).inflate(R.layout.row_grid_item, viewGroup, false)
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
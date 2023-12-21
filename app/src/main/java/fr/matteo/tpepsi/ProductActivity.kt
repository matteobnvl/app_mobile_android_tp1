package fr.matteo.tpepsi

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ProductActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        val url_products = intent.getStringExtra("url")
        val title = intent.getStringExtra("title")
        setHeaderTitle(title)
        showBack()
        if (url_products != null) {
            Log.d("URL", url_products)
        }

        val products = arrayListOf<Product>()
        val recyclerViewProduct = findViewById<RecyclerView>(R.id.recyclerViewProducts)
        recyclerViewProduct.layoutManager = LinearLayoutManager(this)
        val productAdapter = ProductAdapter(products)
        recyclerViewProduct.adapter = productAdapter

        val okHttpClient:OkHttpClient = OkHttpClient.Builder().build()
        val mRequestUrl = url_products ?: ""
        val request = Request.Builder().url(mRequestUrl).get().cacheControl(CacheControl.FORCE_NETWORK).build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OkHttp", "Request failed$e")
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()
                if (data != null) {
                    val jsProducts = JSONObject(data)
                    val jsArrayProducts = jsProducts.getJSONArray("items")
                    for (i in 0 until jsArrayProducts.length()) {
                        val js = jsArrayProducts.getJSONObject(i)
                        val product = Product(
                            js.optString("name", ""),
                            js.optString("description", ""),
                            js.optString("picture_url", "")
                        )
                        products.add(product)
                    }
                    runOnUiThread {
                        productAdapter.notifyDataSetChanged()
                    }

                }
            }
        })
        Log.d("Products","   products.count() :"+products.count())
    }
}
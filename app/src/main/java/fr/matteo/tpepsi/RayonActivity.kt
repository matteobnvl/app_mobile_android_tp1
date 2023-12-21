package fr.matteo.tpepsi

import android.content.Intent
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

class RayonActivity : BaseActivity(), RayonAdapter.OnRayonClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rayon)
        val rayons = arrayListOf<Rayon>()
        val recyclerViewRayons = findViewById<RecyclerView>(R.id.recyclerViewRayons)
        recyclerViewRayons.layoutManager = LinearLayoutManager(this)
        val rayonAdapter = RayonAdapter(rayons)
        rayonAdapter.onRayonClickListener = this
        recyclerViewRayons.adapter = rayonAdapter

        val okHttpClient:OkHttpClient = OkHttpClient.Builder().build()
        val mRequestUrl = "https://www.ugarit.online/epsi/categories.json"
        val request = Request.Builder().url(mRequestUrl).get().build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OkHttp", "Request failed$e")
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()
                if (data != null) {
                    val jsRayons = JSONObject(data)
                    val jsArrayRayons = jsRayons.getJSONArray("items")
                    for (i in 0 until jsArrayRayons.length()) {
                        val js = jsArrayRayons.getJSONObject(i)
                        val rayon = Rayon(
                            js.optInt("category_id", 0),
                            js.optString("title", ""),
                            js.optString("products_url", "")
                        )
                        rayons.add(rayon)
                    }
                    runOnUiThread {
                        rayonAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
        Log.d("Rayons","   rayons.count() :"+rayons.count())
    }

    override fun onRayonClick(rayon: Rayon) {
        val newIntent = Intent(application,ProductActivity::class.java)
        newIntent.putExtra("url", rayon.products_url)
        newIntent.putExtra("title", rayon.title)
        startActivity(newIntent)
    }
}
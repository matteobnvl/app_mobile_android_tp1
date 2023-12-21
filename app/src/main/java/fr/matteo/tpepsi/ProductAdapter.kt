package fr.matteo.tpepsi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductAdapter (val products: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val textFieldName = view.findViewById<TextView>(R.id.textViewName)
        val textFieldDescription = view.findViewById<TextView>(R.id.textViewDescription)
        val imageViewProduct = view.findViewById<ImageView>(R.id.imageViewProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_product, parent, false)
        Log.e("Adapter","onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val product = products.get(position)
        holder.textFieldName.text=product.name
        holder.textFieldDescription.text = product.description
        Glide.with(holder.imageViewProduct.context).load(product.picture_url).into(holder.imageViewProduct);
    }

    override fun getItemCount(): Int {
        return products.size
    }
}
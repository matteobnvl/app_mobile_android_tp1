package fr.matteo.tpepsi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class RayonAdapter (val rayons: ArrayList<Rayon>): RecyclerView.Adapter<RayonAdapter.ViewHolder>() {

    interface OnRayonClickListener {
        fun onRayonClick(rayon: Rayon)
    }

    var onRayonClickListener: OnRayonClickListener? = null

    class ViewHolder(view:View) :RecyclerView.ViewHolder(view) {
        val buttonRayons = view.findViewById<Button>(R.id.buttonRayons)
        var currentRayon: Rayon? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RayonAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_rayons, parent, false)
        Log.e("Adapter", "onCreateViewHolder")
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rayons.size
    }

    override fun onBindViewHolder(holder: RayonAdapter.ViewHolder, position: Int) {
        val rayon = rayons.get(position)
        holder.buttonRayons.text=rayon.title
        holder.currentRayon = rayon

        holder.buttonRayons.setOnClickListener {
            val selectedRayon = holder.currentRayon
            selectedRayon?.let {
                onRayonClickListener?.onRayonClick(it)
            }
        }
    }
}
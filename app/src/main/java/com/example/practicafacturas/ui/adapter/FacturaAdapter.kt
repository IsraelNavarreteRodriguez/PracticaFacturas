package com.example.practicafacturas.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R
import com.example.practicafacturas.data.model.Factura

class FacturaAdapter(list: List<Factura>, listener: FacturaAdapterOnClickListener) : RecyclerView.Adapter<FacturaAdapter.ViewHolder>() {

    val list = list
    val listener = listener

    interface FacturaAdapterOnClickListener : View.OnClickListener {
        override fun onClick(v: View?)
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_factura, parent, false)
        view.setOnClickListener(listener)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFecha.text = list[position].fecha.toString()
        holder.tvImporteOrdenacion.text = "${list[position].importeOrdenacion}€"
        holder.tvDescEstado.text = list[position].descEstado
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvImporteOrdenacion: TextView = itemView.findViewById(R.id.tvImporteOrdenacion)
        val tvDescEstado: TextView = itemView.findViewById(R.id.tvDescEstado)
    }

}
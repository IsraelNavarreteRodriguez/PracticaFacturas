package com.example.practicafacturas.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.ui.factura.utils.JsonToFactura

class FacturaAdapter(list: List<Factura>, listener: FacturaAdapterOnClickListener) :
    RecyclerView.Adapter<FacturaAdapter.ViewHolder>() {

    val list = list
    val listener = listener

    interface FacturaAdapterOnClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_factura, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getItem(position: Int): Factura {
        return list[position]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvFecha.text = JsonToFactura.dateParserPrint(list[position].fecha.toString())
        holder.tvImporteOrdenacion.text = "${list[position].importeOrdenacion}€"
        holder.tvDescEstado.text = list[position].descEstado
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvImporteOrdenacion: TextView = itemView.findViewById(R.id.tvImporteOrdenacion)
        val tvDescEstado: TextView = itemView.findViewById(R.id.tvDescEstado)
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION)
                listener.onItemClick(position)
        }
    }

}
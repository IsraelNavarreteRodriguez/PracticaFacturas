package com.example.practicafacturas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.ui.factura.utils.JsonToFactura

/**
 * Es el adapter del RecyclerView de facturas
 */
class FacturaAdapter(context : Context,list: List<Factura>) :
    RecyclerView.Adapter<FacturaAdapter.ViewHolder>() {

    val context = context
    val list = list



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_factura, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    /**
     * Es el viewholder del adapter del recyclerView
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val tvImporteOrdenacion: TextView = itemView.findViewById(R.id.tvImporteOrdenacion)
        private val tvDescEstado: TextView = itemView.findViewById(R.id.tvDescEstado)

        /**
         * bindea los elementos de cada factura
         */
        fun bind(position: Int) {
            tvFecha.text = JsonToFactura.dateParserPrint(list[position].fecha.toString())
            tvImporteOrdenacion.text = "${list[position].importeOrdenacion}€"
            tvDescEstado.text = if (list[position].descEstado == "Pagada") "" else list[position].descEstado
            itemView.setOnClickListener { Toast.makeText(context, "Fecha: ${tvFecha.text}\n" +
                    "Estado: ${tvDescEstado.text}\n" +
                    "Importe: ${tvImporteOrdenacion.text}", Toast.LENGTH_SHORT).show() }
        }
    }

}
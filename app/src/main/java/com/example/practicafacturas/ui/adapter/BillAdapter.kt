package com.example.practicafacturas.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R
import com.example.practicafacturas.data.model.Bill
import com.example.practicafacturas.ui.factura.utils.JsonToBill

/**
 * Es el adapter del RecyclerView de facturas
 */
class BillAdapter(context: Context, list: List<Bill>) :
    RecyclerView.Adapter<BillAdapter.ViewHolder>() {

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
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        private val tvImporteOrdenacion: TextView = itemView.findViewById(R.id.tvImporteOrdenacion)
        private val tvDescEstado: TextView = itemView.findViewById(R.id.tvDescEstado)

        /**
         * bindea los elementos de cada factura
         */
        fun bind(position: Int) {
            tvFecha.text = JsonToBill.dateParserForPrinting(list[position].fecha.toString())
            tvImporteOrdenacion.text = "${list[position].importeOrdenacion}€"
            tvDescEstado.text = checkEstado(position)
            itemView.setOnClickListener {
                Toast.makeText(
                    context, "Fecha: ${tvFecha.text}\n" +
                            "Estado: ${printEstado(tvDescEstado.text.toString())}\n" +
                            "Importe: ${tvImporteOrdenacion.text}", Toast.LENGTH_SHORT
                ).show()
            }
        }

        private fun printEstado(param: String): String {
            return if (param.isBlank())
                "Pagada"
            else
                param
        }

        private fun checkEstado(position: Int): String {
            return if (list[position].descEstado == "Pagada") {
                tvDescEstado.visibility = View.GONE
                ""
            } else
                list[position].descEstado
        }
    }

}
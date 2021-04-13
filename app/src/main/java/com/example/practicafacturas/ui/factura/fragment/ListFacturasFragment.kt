package com.example.practicafacturas.ui.factura.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.databinding.FragmentListfacturasBinding
import com.example.practicafacturas.ui.adapter.FacturaAdapter
import com.example.practicafacturas.ui.factura.viewmodel.FacturaViewModel
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFacturasFragment : Fragment() {
    private lateinit var llLoading: LinearLayout
    private lateinit var binding: FragmentListfacturasBinding
    private lateinit var listener: FacturaAdapter.FacturaAdapterOnClickListener
    private lateinit var adapter: FacturaAdapter
    private lateinit var bundle: Bundle
    private lateinit var facturaViewModel: FacturaViewModel
    private var desde: Date? = null
    private var hasta: Date? = null
    private lateinit var estado: BooleanArray
    private var importe = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (arguments != null) {
            bundle = arguments as Bundle
            getFilters(bundle)
        }
        facturaViewModel = ViewModelProvider(this).get(FacturaViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_listfacturas, container, false)
        return binding.root
    }

    private fun getFilters(bundle: Bundle) {
        if (bundle.get("desde").toString() != "dia/mes/año" || bundle.get("hasta").toString() != "dia/mes/año") {
            desde = Date(bundle.get("desde").toString())
            hasta = Date(bundle.get("hasta").toString())
        }
        estado = bundle.getBooleanArray("estado")!!
        importe = bundle.getFloat("importe").toDouble()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llLoading = view.findViewById(R.id.llLoading)
        //todo inicializar listener
        facturaViewModel.getList(desde, hasta, importe, estado)
            .observe(viewLifecycleOwner, Observer {
                initializeRecycler(it)
            })

    }

    private fun initializeRecycler(it: List<Factura>) {
        if (binding.rvFacturas.adapter == null) {
            adapter = FacturaAdapter(it, listener)
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.rvFacturas.layoutManager = layoutManager
            binding.rvFacturas.adapter = adapter
        }
    }

    private fun showProgress() {
        llLoading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        llLoading.visibility = View.GONE
    }

    private fun noData() {
        binding.llNoData.visibility = View.VISIBLE
    }
}
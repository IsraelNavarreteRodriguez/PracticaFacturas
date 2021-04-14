package com.example.practicafacturas.ui.factura.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R
import com.example.practicafacturas.data.model.Factura
import com.example.practicafacturas.databinding.FragmentListfacturasBinding
import com.example.practicafacturas.ui.adapter.FacturaAdapter
import com.example.practicafacturas.ui.factura.utils.JsonToFactura
import com.example.practicafacturas.ui.factura.utils.SpacingItemDecorator
import com.example.practicafacturas.ui.factura.viewmodel.FacturaViewModel
import java.time.LocalDate
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFacturasFragment : Fragment(), FacturaAdapter.FacturaAdapterOnClickListener {
    private lateinit var llLoading: LinearLayout
    private lateinit var binding: FragmentListfacturasBinding
    private lateinit var adapter: FacturaAdapter
    private lateinit var bundle: Bundle
    private lateinit var facturaViewModel: FacturaViewModel
    private var desde: LocalDate? = null
    private var hasta: LocalDate? = null
    private lateinit var estado: BooleanArray
    private var importe = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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
        if (bundle.get("desde").toString() != "dia/mes/año" || bundle.get("hasta")
                .toString() != "dia/mes/año"
        ) {
            desde = JsonToFactura.dateParser(bundle.get("desde").toString())
            hasta = JsonToFactura.dateParser(bundle.get("hasta").toString())
        }
        estado = bundle.getBooleanArray("estado")!!
        importe = bundle.getFloat("importe").toDouble()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llLoading = view.findViewById(R.id.llLoading)
        facturaViewModel.getList(desde, hasta, importe, estado)
        facturaViewModel.lista.observe(viewLifecycleOwner, Observer {
            initializeRecycler(it)
            if (it.isEmpty())
                noData()
            else
                hasData()
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter)
            view?.findNavController()
                ?.navigate(R.id.action_listFacturaFragment_to_facturaFiltroFragment)
        return super.onOptionsItemSelected(item)
    }

    private fun initializeRecycler(it: List<Factura>) {
        if (binding.rvFacturas.adapter == null) {
            val decoration = SpacingItemDecorator(
                context = requireContext(),
                verticalSpaceHeight = 20
            )
            adapter = FacturaAdapter(it, this)
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.rvFacturas.layoutManager = layoutManager
            binding.rvFacturas.adapter = adapter
            binding.rvFacturas.addItemDecoration(decoration)
        }
    }

    private fun showProgress() {
        llLoading.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        llLoading.visibility = View.GONE
    }

    private fun noData() {
        binding.llData.visibility = View.GONE
        binding.llNoData.visibility = View.VISIBLE
    }

    private fun hasData() {
        binding.llData.visibility = View.VISIBLE
        binding.llNoData.visibility = View.GONE
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show()
    }

}
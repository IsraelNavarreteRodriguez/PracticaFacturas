package com.example.practicafacturas.ui.factura.fragment

import android.os.Bundle
import android.view.*
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
import com.example.practicafacturas.ui.factura.utils.HeaderDecoration
import com.example.practicafacturas.ui.factura.utils.SpacingItemDecorator
import com.example.practicafacturas.ui.factura.viewmodel.FacturaViewModel
import java.time.LocalDate

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListFacturasFragment : Fragment() {
    private lateinit var binding: FragmentListfacturasBinding
    private lateinit var adapter: FacturaAdapter
    private lateinit var facturaViewModel: FacturaViewModel
    private var desde: LocalDate? = null
    private var hasta: LocalDate? = null
    private lateinit var estado: BooleanArray
    private var importe = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        facturaViewModel = ViewModelProvider(this).get(FacturaViewModel::class.java)
        facturaViewModel.downloadJson()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            FragmentListfacturasBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cancel).isVisible = false
        menu.findItem(R.id.action_filter).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        facturaViewModel.liveData.observe(viewLifecycleOwner, Observer {
            initializeRecycler(it)
            if (it.isEmpty())
                noData()
            else
                hasData()
            hideProgress()
        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter)
            view?.findNavController()
                ?.navigate(R.id.action_listFacturaFragment_to_facturaFiltroFragment)
        return super.onOptionsItemSelected(item)
    }


    //region Methods

    /**
     * Inicializa el recycler cuando hay un cambio en la lista y no se ha inicializado todavida
     */
    private fun initializeRecycler(it: List<Factura>) {
        if (binding.rvFacturas.adapter == null) {
            val decoration = SpacingItemDecorator(
                context = requireContext(),
                verticalSpaceHeight = 20
            )
            adapter = FacturaAdapter(requireContext(),it)
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.rvFacturas.layoutManager = layoutManager
            binding.rvFacturas.adapter = adapter
            binding.rvFacturas.addItemDecoration(decoration)
            binding.rvFacturas.addItemDecoration(HeaderDecoration(requireContext(),binding.rvFacturas,R.layout.item_header))
        }
    }

    /**
     * Muestra el layout de carga mientras descarga y filtra los datos
     */
    private fun showProgress() {
        binding.llLoading.llLoading.visibility = View.VISIBLE
    }

    /**
     * Oculta el layout de carga
     */
    private fun hideProgress() {
        binding.llLoading.llLoading.visibility = View.GONE
    }

    /**
     * Si no hay datos muestra el mensaje de noData
     */
    private fun noData() {
        binding.NoData.visibility = View.VISIBLE
    }

    /**
     * Si hay datos oculta el mensaje de noData
     */
    private fun hasData() {
        binding.NoData.visibility = View.GONE
    }

    //endregion

}
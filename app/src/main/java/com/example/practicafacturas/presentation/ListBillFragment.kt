package com.example.practicafacturas.presentation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practicafacturas.R
import com.example.practicafacturas.databinding.FragmentListbillBinding
import com.example.practicafacturas.domain.model.Bill
import com.example.practicafacturas.presentation.adapter.BillAdapter
import com.example.practicafacturas.presentation.utils.HeaderDecoration
import com.example.practicafacturas.presentation.utils.SpacingItemDecorator

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ListBillFragment : Fragment() {
    private lateinit var binding: FragmentListbillBinding
    private lateinit var adapter: BillAdapter
    private lateinit var billViewModel: BillViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        billViewModel = activity?.run {
            ViewModelProvider(this).get(BillViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        billViewModel.getAllBills()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding =
            FragmentListbillBinding.inflate(inflater, container, false)
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
        billViewModel.liveDataBill.observe(viewLifecycleOwner, Observer {
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
    private fun initializeRecycler(it: List<Bill>) {
        if (binding.rvFacturas.adapter == null) {
            val decoration =
                SpacingItemDecorator(
                    context = requireContext(),
                    verticalSpaceHeight = 20
                )
            adapter = BillAdapter(requireContext(), it)
            val layoutManager: RecyclerView.LayoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            binding.rvFacturas.layoutManager = layoutManager
            binding.rvFacturas.adapter = adapter
            binding.rvFacturas.addItemDecoration(decoration)
            binding.rvFacturas.addItemDecoration(
                HeaderDecoration(
                    requireContext(),
                    binding.rvFacturas,
                    R.layout.item_header
                )
            )
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
        binding.noData.NoData.visibility = View.VISIBLE
    }

    /**
     * Si hay datos oculta el mensaje de noData
     */
    private fun hasData() {
        binding.noData.NoData.visibility = View.GONE
    }

    //endregion

}
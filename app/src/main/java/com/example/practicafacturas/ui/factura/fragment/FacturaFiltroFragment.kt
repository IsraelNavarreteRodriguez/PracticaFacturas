package com.example.practicafacturas.ui.factura.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.practicafacturas.R
import com.example.practicafacturas.databinding.FragmentFacturafiltroBinding
import com.example.practicafacturas.ui.factura.viewmodel.FacturaViewModel
import com.google.android.material.slider.Slider
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FacturaFiltroFragment : Fragment() {
    lateinit var facturaViewModel: FacturaViewModel
    private lateinit var binding: FragmentFacturafiltroBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        facturaViewModel = ViewModelProvider(this).get(FacturaViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_facturafiltro, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btDesde.setOnClickListener {
            setDatePicker(binding.btDesde)
        }
        binding.btHasta.setOnClickListener {
            setDatePicker(binding.btHasta)
        }
        binding.btFilter.setOnClickListener {
            val bundle = Bundle()
            putBundle(bundle)
            view.findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment, bundle)
        }
        binding.btEliminateFilter.setOnClickListener {
            eliminateFilters(
                binding.btHasta,
                binding.btDesde,
                binding.chkPagada,
                binding.chkPendiente,
                binding.sldImporte
            )
        }
    }

    /**
     * Introduce los valores de los filtros en un bundle y los pasa al fragment de ListFacturas
     */
    private fun putBundle(bundle: Bundle) {
        bundle.putString("desde", binding.btDesde.text.toString())
        bundle.putString("hasta", binding.btHasta.text.toString())
        bundle.putBooleanArray("estado", getEstado())
        bundle.putFloat("importe", binding.sldImporte.value)
    }

    /**
     * Crea el array de estados para pasarlo por el bundle
     */
    private fun getEstado(): BooleanArray? {
        val result = mutableListOf<Boolean>()
        result.add(binding.chkPagada.isChecked)
        result.add(binding.chkPagada.isChecked)
        return result.toBooleanArray()
    }

    /**
     * Elimina los filtros seleccionados (los pone por defecto)
     */
    private fun eliminateFilters(
        btHasta: Button,
        btDesde: Button,
        chkPagada: CheckBox,
        chkPendiente: CheckBox,
        sldImporte: Slider
    ) {
        btHasta.text = "dia/mes/año"
        btDesde.text = "dia/mes/año"
        chkPagada.isChecked = false
        chkPendiente.isChecked = false
        sldImporte.value = 0.0F
    }

    /**
     * Crea el calendario para recoger la fecha
     */
    private fun setDatePicker(button: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                button.text = "$dayOfMonth/$month/$year"
            },
            year,
            month,
            day
        )
        datePickerDialog.show()

    }
}
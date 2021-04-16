package com.example.practicafacturas.ui.factura.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.practicafacturas.R
import com.example.practicafacturas.databinding.FragmentFacturafiltroBinding
import com.example.practicafacturas.ui.factura.utils.JsonToFactura
import com.example.practicafacturas.ui.factura.viewmodel.FacturaViewModel
import com.example.practicafacturas.ui.factura.viewmodel.InvoiceFilter
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
        setHasOptionsMenu(true)
        facturaViewModel = ViewModelProvider(this).get(FacturaViewModel::class.java)
        binding = FragmentFacturafiltroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cancel).isVisible = true
        menu.findItem(R.id.action_filter).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btDesde.text = facturaViewModel.actualFilter.desde.toString()
        binding.btHasta.text = facturaViewModel.actualFilter.hasta.toString()
        binding.sldImporte.value = facturaViewModel.actualFilter.importe.toFloat()
        binding.chkPagada.isChecked = facturaViewModel.actualFilter.pagado
        binding.chkPendiente.isChecked = facturaViewModel.actualFilter.pendiente

        binding.btDesde.setOnClickListener {
            setDatePicker(binding.btDesde)
        }
        binding.btHasta.setOnClickListener {
            setDatePicker(binding.btHasta)
        }
        binding.btFilter.setOnClickListener {

            with(binding) {
                val strDesde =
                    correctDate(btDesde.text.toString())
                val strHasta =
                    correctDate(btHasta.text.toString())
                if (checkDates(strHasta, strDesde)) {
                    val desde = JsonToFactura.dateParser(btDesde.text.toString())
                    val hasta = JsonToFactura.dateParser(btHasta.text.toString())
                    facturaViewModel.setFilter(
                        InvoiceFilter(
                            desde,
                            hasta,
                            sldImporte.value.toDouble(),
                            chkPagada.isChecked,
                            chkPendiente.isChecked
                        )
                    )
                    view.findNavController()
                        .navigate(R.id.action_facturaFiltroFragment_to_listFacturaFragment)
                } else
                    Toast.makeText(
                        requireContext(),
                        "La fechas deben ser validas",
                        Toast.LENGTH_SHORT
                    )
                        .show()
            }

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

    private fun correctDate(strDate: String): String? {
        if (strDate == "dia/mes/año")
            return null
        return strDate
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cancel) {
            view?.findNavController()
                ?.navigate(R.id.action_facturaFiltroFragment_to_listFacturaFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    //region Methods
    /**
     * Comprueba que las fechas sean ambas validas
     * Ambas deben de ser o nulas (dia/mes/año) o validas
     * Si ambas son validas hasta debe ser despues de desde (07/05/2021) > (06/05/2021)
     */
    private fun checkDates(hasta: String?, desde: String?): Boolean {
        if (hasta != null && desde != null)
            return JsonToFactura.dateParser(hasta).isAfter(JsonToFactura.dateParser(desde))
        if (hasta == null && desde == null)
            return true
        return false
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
                makedate(dayOfMonth, month, button, year)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()

    }

    /**
     * Crea la fecha en un formato correcto (Si el mes no es de dos digitos lo pasa de 7 -> 07 e igual con el dia)
     */
    private fun makedate(
        dayOfMonth: Int,
        month: Int,
        button: Button,
        year: Int
    ) {
        if (dayOfMonth.toString().length == 1)
            if (month.toString().length == 1)
                button.text = "0$dayOfMonth/0${month + 1}/$year"
            else
                button.text = "$dayOfMonth/0${month + 1}/$year"
        else
            if (month.toString().length == 1)
                button.text = "$dayOfMonth/0${month + 1}/$year"
            else
                button.text = "$dayOfMonth/${month + 1}/$year"

    }


    //endregion
}
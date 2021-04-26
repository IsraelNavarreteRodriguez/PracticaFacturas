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
import com.example.practicafacturas.ui.factura.utils.JsonToBill
import com.example.practicafacturas.ui.factura.viewmodel.BillViewModel
import com.example.practicafacturas.ui.factura.viewmodel.InvoiceFilter
import com.google.android.material.slider.Slider
import java.time.ZoneId
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class BillFilterFragment : Fragment() {
    lateinit var billViewModel: BillViewModel
    private lateinit var binding: FragmentFacturafiltroBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        billViewModel = activity?.run {
            ViewModelProvider(this).get(BillViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
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
        setParams()
        binding.btDesde.setOnClickListener {
            setDatePicker(binding.btDesde)
        }
        binding.btHasta.setOnClickListener {
            setDatePicker(binding.btHasta)
        }
        binding.btFilter.setOnClickListener {

            with(binding) {
                val desde = JsonToBill.dateParserToCompare(btDesde.text.toString())
                val hasta = JsonToBill.dateParserToCompare(btHasta.text.toString())
                if (desde.isBefore(hasta)) {
                    billViewModel.setFilter(
                        InvoiceFilter(
                            desde,
                            hasta,
                            sldImporte.value,
                            chkPagada.isChecked,
                            chkPendiente.isChecked
                        )
                    )
                    view.findNavController()
                        .navigate(R.id.action_facturaFiltroFragment_to_listFacturaFragment)
                } else
                    Toast.makeText(
                        requireContext(),
                        "Las fechas deben de ser validas",
                        Toast.LENGTH_SHORT
                    ).show()
            }

        }
        binding.btEliminateFilter.setOnClickListener {
            setDefaultFilters(
                binding.btHasta,
                binding.btDesde,
                binding.chkPagada,
                binding.chkPendiente,
                binding.sldImporte
            )
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cancel) {
            view?.findNavController()
                ?.navigate(R.id.action_facturaFiltroFragment_to_listFacturaFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    //region Methods

    private fun setParams() {
        with(binding) {
            btDesde.text =
                JsonToBill.dateParseForTextButton(billViewModel.actualFilter.desde.toString())
            btHasta.text =
                JsonToBill.dateParseForTextButton(billViewModel.actualFilter.hasta.toString())
            sldImporte.valueFrom = billViewModel.getMinImporte()
            if (billViewModel.actualFilter.importe != billViewModel.defaultFilter.importe)
                sldImporte.value = billViewModel.actualFilter.importe
            else
                sldImporte.value = billViewModel.defaultFilter.importe
            sldImporte.valueTo = billViewModel.defaultFilter.importe
            chkPagada.isChecked = billViewModel.actualFilter.pagado
            chkPendiente.isChecked = billViewModel.actualFilter.pendiente
        }
    }


    /**
     * Elimina los filtros seleccionados (los pone por defecto)
     */
    private fun setDefaultFilters(
        btHasta: Button,
        btDesde: Button,
        chkPagada: CheckBox,
        chkPendiente: CheckBox,
        sldImporte: Slider
    ) {
        btDesde.text =
            JsonToBill.dateParseForTextButton(billViewModel.defaultFilter.desde.toString())
        btHasta.text =
            JsonToBill.dateParseForTextButton(billViewModel.defaultFilter.hasta.toString())
        sldImporte.valueFrom = billViewModel.getMinImporte()
        sldImporte.value = billViewModel.defaultFilter.importe
        sldImporte.valueTo = billViewModel.defaultFilter.importe
        chkPagada.isChecked = billViewModel.defaultFilter.pagado
        chkPendiente.isChecked = billViewModel.defaultFilter.pendiente
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
                datePickerDateToValidDate(dayOfMonth, month, button, year)
            },
            year,
            month,
            day
        )
        setMinAndMaxForDatePicker(datePickerDialog)
        datePickerDialog.show()

    }

    private fun setMinAndMaxForDatePicker(datePickerDialog: DatePickerDialog) {
        var min = billViewModel.defaultFilter.desde!!.atStartOfDay(
            ZoneId.systemDefault()
        ).toInstant().toEpochMilli()
        var max = billViewModel.defaultFilter.hasta!!.atStartOfDay(
            ZoneId.systemDefault()
        ).toInstant().toEpochMilli()

        datePickerDialog.datePicker.minDate = min

        datePickerDialog.datePicker.maxDate = max
    }

    /**
     * Crea la fecha en un formato correcto (Si el mes no es de dos digitos lo pasa de 7 -> 07 e igual con el dia)
     */
    private fun datePickerDateToValidDate(
        dayOfMonth: Int,
        month: Int,
        button: Button,
        year: Int
    ) {
        if (dayOfMonth.toString().length == 1)
            if ((month + 1).toString().length == 1)
                button.text = "0$dayOfMonth/0${month + 1}/$year"
            else
                button.text = "$dayOfMonth/${month + 1}/$year"
        else
            if ((month + 1).toString().length == 1)
                button.text = "$dayOfMonth/0${month + 1}/$year"
            else
                button.text = "$dayOfMonth/${month + 1}/$year"

    }


    //endregion
}
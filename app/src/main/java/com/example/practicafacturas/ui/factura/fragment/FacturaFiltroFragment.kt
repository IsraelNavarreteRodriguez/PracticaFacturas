package com.example.practicafacturas.ui.factura.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.practicafacturas.R
import com.example.practicafacturas.databinding.FragmentFacturafiltroBinding
import com.example.practicafacturas.ui.factura.utils.JsonToFactura
import com.example.practicafacturas.ui.factura.viewmodel.FacturaViewModel
import com.google.android.material.slider.Slider
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FacturaFiltroFragment : Fragment() {
    lateinit var facturaViewModel: FacturaViewModel
    private lateinit var binding: FragmentFacturafiltroBinding
    private lateinit var bundle: Bundle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        facturaViewModel = ViewModelProvider(this).get(FacturaViewModel::class.java)
        binding =            DataBindingUtil.inflate(inflater, R.layout.fragment_facturafiltro, container, false)
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_cancel).isVisible = true
        menu.findItem(R.id.action_filter).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            bundle = arguments as Bundle
            getFilters(bundle)
        }
        binding.btDesde.setOnClickListener {
            setDatePicker(binding.btDesde)
        }
        binding.btHasta.setOnClickListener {
            setDatePicker(binding.btHasta)
        }
        binding.btFilter.setOnClickListener {
            val bundle = Bundle()
            putBundle(bundle)
            if (checkDates(binding.btHasta.text.toString(), binding.btDesde.text.toString()))
                view.findNavController()
                    .navigate(R.id.action_facturaFiltroFragment_to_listFacturaFragment, bundle)
            else
                Toast.makeText(requireContext(), "La fechas deben ser validas", Toast.LENGTH_SHORT)
                    .show()
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



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cancel) {
            val bundle  = Bundle()
            putBundle(bundle)
            view?.findNavController()
                ?.navigate(R.id.action_facturaFiltroFragment_to_listFacturaFragment,bundle)
        }
        return super.onOptionsItemSelected(item)
    }

    //region Methods
    /**
     * Comprueba que las fechas sean ambas validas
     * Ambas deben de ser o nulas (dia/mes/año) o validas
     * Si ambas son validas hasta debe ser despues de desde (07/05/2021) > (06/05/2021)
     */
    private fun checkDates(hasta: String, desde: String): Boolean {
        if (hasta != "dia/mes/año" && desde != "dia/mes/año")
            return JsonToFactura.dateParser(hasta).isAfter(JsonToFactura.dateParser(desde))
        if (hasta == "dia/mes/año" && desde == "dia/mes/año")
            return true
        return false
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
        result.add(binding.chkPendiente.isChecked)
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
                button.text = "0$dayOfMonth/0$month/$year"
            else
                button.text = "$dayOfMonth/0$month/$year"
        else
            if (month.toString().length == 1)
                button.text = "$dayOfMonth/0$month/$year"
            else
                button.text = "$dayOfMonth/0$month/$year"

        if (month.toString().length != 1 && dayOfMonth.toString().length != 1)
            button.text = "$dayOfMonth/$month/$year"
    }

    /**
     * Recoge los filtros mandados por el bundle desde ListFacturaFragments
     */
    private fun getFilters(bundle: Bundle) {
        if (bundle.get("desde").toString() != "dia/mes/año" || bundle.get("hasta")
                .toString() != "dia/mes/año"
        ) {
            binding.btDesde.text = bundle.get("desde").toString()
            binding.btHasta.text = bundle.get("hasta").toString()
        }
        val estados = bundle.getBooleanArray("estado")!!
        binding.chkPagada.isChecked = estados[0]
        binding.chkPendiente.isChecked = estados[1]
        binding.sldImporte.value = bundle.getFloat("importe")
    }
    //endregion
}
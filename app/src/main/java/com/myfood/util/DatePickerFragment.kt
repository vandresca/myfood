package com.myfood.util

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.myfood.R
import java.util.*

//Clase que crea un dialogo con un calendario donde seleccionar una fecha
//La clase recibe un listener con un fecha.
class DatePickerFragment(val listener: (year: Int, month: Int, day: Int) -> Unit) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {

    //Al crear el dialogo abrimos un panel con el calendario en la fecha actual
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        //Capturamos la fecha actual
        val c = Calendar.getInstance()

        //Obtenemos de esa fecha el año, mes y día de mes
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        //Abrimos el dialogo con la fecha actual
        return DatePickerDialog(
            requireActivity(), R.style.datePickerTheme, this, year, month, day
        )
    }

    //Al seleccionar una fecha la devolvemos en el listener-
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener(year, month + 1, day)
    }
}
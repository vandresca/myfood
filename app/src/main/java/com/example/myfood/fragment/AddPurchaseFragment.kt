package com.example.myfood.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.pec1.R
import com.google.zxing.integration.android.IntentIntegrator


class AddPurchaseFragment : Fragment() {
    lateinit var btnBarcode: Button
    lateinit var textBarcode: TextView
    lateinit var quantityUnit: Spinner
    lateinit var datePickerExpirationDate: ImageButton
    lateinit var editTextExpirationDate: EditText
    lateinit var datePickerPreferenceDate: ImageButton
    lateinit var editTextPreferenceDate: EditText

    val dropdownItems = arrayOf(
        "Vic 1",
        "Vic 2",
        "Vic 3",
        "Vic 4",
        "Vic 5",
        "Vic 6",
        "Vic 7",
        "Vic 8",
        "Vic 9",
        "Vic 10",
        "Vic 11",
        "Vic 12",
        "Vic 13"
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_purchase_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBarcode = view.findViewById(R.id.buttonScan)
        textBarcode = view.findViewById(R.id.editTextBarcode)
        quantityUnit = view.findViewById(R.id.spinnerQuantityUnit)
        datePickerExpirationDate = view.findViewById(R.id.datePickerExpirationDate)
        editTextExpirationDate = view.findViewById(R.id.editTextExpirationDate)
        datePickerPreferenceDate = view.findViewById(R.id.datePickerPreferenceDate)
        editTextPreferenceDate = view.findViewById(R.id.editTextPreferenceDate)
        datePickerExpirationDate.setOnClickListener {
            var dialogDate =
                DatePickerFragment { year, month, day -> showExpirationResult(year, month, day) }
            dialogDate.show(childFragmentManager, "DataPicker")
        }
        datePickerPreferenceDate.setOnClickListener {
            var dialogDate =
                DatePickerFragment { year, month, day -> showPreferenceResult(year, month, day) }
            dialogDate.show(childFragmentManager, "DataPicker")
        }
        btnBarcode.setOnClickListener { initScanner() }
        initCombos()
    }

    private fun initScanner() {
        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt("SCAN")
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                textBarcode.text = "Cancelado"
            } else {
                textBarcode.text = "El valor escaneado es: " + result.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
        commit()
    }

    fun commit() {
        val transaction = this.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, this)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun initCombos() {
        this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                dropdownItems
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                quantityUnit.adapter = adapter
                quantityUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        textBarcode.text = dropdownItems[position]
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
            }
        }
    }

    private fun showExpirationResult(year: Int, month: Int, day: Int) {
        editTextExpirationDate.setText("$day/$month/$year")
    }

    private fun showPreferenceResult(year: Int, month: Int, day: Int) {
        editTextPreferenceDate.setText("$day/$month/$year")
    }
}
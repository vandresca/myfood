package com.example.myfood.mvp.addpurchaseproduc

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.databinding.AddPurchaseFragmentBinding
import com.example.myfood.util.DatePickerFragment
import com.google.zxing.integration.android.IntentIntegrator


class AddPurchaseFragment(private val mode: Int) : Fragment() {

    private var _binding: AddPurchaseFragmentBinding? = null
    private val binding get() = _binding!!

    private var dropdownItems = ArrayList<String>()

    companion object {
        const val MODE_UPDATE = 1
        const val MODE_ADD = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = AddPurchaseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillCombo()
        setTextAddButton()
        initDataPickers()
        initBtnScan()
        initCombos()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                binding.etBarcode.setText("")
            } else {
                binding.etBarcode.setText(result.contents)
            }
        } else {
            binding.etBarcode.setText("")
            //super.onActivityResult(requestCode, resultCode, data)
        }
        commit()
    }

    private fun setTextAddButton() {
        if (mode == MODE_ADD) {
            binding.btnAddPurchaseProduct.text = "Add"
        } else {
            binding.btnAddPurchaseProduct.text = "Update"
        }
    }

    private fun initDataPickers() {
        binding.dpExpirationDate.setOnClickListener {
            var dialogDate =
                DatePickerFragment { year, month, day -> showExpirationResult(year, month, day) }
            dialogDate.show(childFragmentManager, "DataPicker")
        }
        binding.dpPreferenceDate.setOnClickListener {
            var dialogDate =
                DatePickerFragment { year, month, day -> showPreferenceResult(year, month, day) }
            dialogDate.show(childFragmentManager, "DataPicker")
        }
    }

    private fun initBtnScan() {
        binding.btnScan.setOnClickListener { initScanner() }
    }

    private fun initScanner() {
        val intentIntegrator = IntentIntegrator.forSupportFragment(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.setCameraId(0)
        intentIntegrator.setPrompt("SCAN")
        intentIntegrator.setBarcodeImageEnabled(false)
        intentIntegrator.initiateScan()
    }

    private fun initCombos() {
        this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                dropdownItems
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.sQuantityUnit.adapter = adapter
                binding.sQuantityUnit.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            binding.etBarcode.setText(dropdownItems[position])
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }
        }
    }

    private fun commit() {
        val transaction = this.activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.container, this)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    private fun showExpirationResult(year: Int, month: Int, day: Int) {
        binding.etExpirationDate.setText("$day/$month/$year")
    }

    private fun showPreferenceResult(year: Int, month: Int, day: Int) {
        binding.etPreferenceDate.setText("$day/$month/$year")
    }

    private fun fillCombo() {
        dropdownItems = arrayListOf(
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
    }
}
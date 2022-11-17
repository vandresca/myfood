package com.example.myfood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pec1.R


class OptionAddPurchaseProductFragment : Fragment() {
    lateinit var buttonOptionBarcode: ImageButton
    lateinit var buttonOptionKeyboard: ImageButton
    lateinit var header: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.option_add_purchase_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonOptionBarcode = view.findViewById(R.id.buttonOptionBarCode)
        buttonOptionKeyboard = view.findViewById(R.id.buttonOptionKeyboard)
        header = (requireActivity()).findViewById(R.id.title_header)

        buttonOptionBarcode.setOnClickListener {
            loadFragment(AddPurchaseFragment())
            header.text = "HH!"
        }
        buttonOptionKeyboard.setOnClickListener {
            loadFragment(AddPurchaseFragment())
            header.text = "OOO!"
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
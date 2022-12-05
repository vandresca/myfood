package com.example.myfood.mvp.optionaddpurchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myfood.R
import com.example.myfood.databinding.OptionAddPurchaseProductBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment


class OptionAddPurchaseFragment : Fragment() {
    private var _binding: OptionAddPurchaseProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = OptionAddPurchaseProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.titleHeader.text = "Add Purchase"
        binding.btnOptionBarCode.setOnClickListener {
            loadFragment(AddPantryFragment(AddPantryFragment.MODE_SCAN))
        }
        binding.btnOptionKeyboard.setOnClickListener {
            loadFragment(AddPantryFragment(AddPantryFragment.MODE_ADD))
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
package com.example.myfood.purchaselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databinding.PurchaseListFragmentBinding
import com.example.myfood.view.fragment.AddPurchaseFragment
import com.example.myfood.view.fragment.OptionAddPurchaseFragment

class PurchaseListFragment : Fragment(), PurchaseListContract.View {
    private var _binding: PurchaseListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var header: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PurchaseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun showUpdatePurchaseScreen() {
        loadFragment(AddPurchaseFragment(AddPurchaseFragment.MODE_UPDATE))
        header.text = "Update Purchase"
    }

    private fun initialize() {
        header = (requireActivity()).findViewById(R.id.title_header)
        PurchaseListPresenter(this, PurchaseListModel()).initData()
        initAddPurchaseClick()
        initSearcher()
    }

    private fun initSearcher() {
        binding.etFilterPL.addTextChangedListener { watchText ->
            PurchaseListPresenter(this, PurchaseListModel()).doFilter(watchText)
        }
    }

    override fun initRecyclerView(purchaseAdapter: PurchaseListAdapter) {
        binding.rvPL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPL.adapter = purchaseAdapter
    }

    private fun initAddPurchaseClick() {
        binding.addPLItem.setOnClickListener {
            loadFragment(OptionAddPurchaseFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}

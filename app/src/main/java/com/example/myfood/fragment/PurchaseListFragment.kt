package com.example.myfood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.adapter.PurchaseListAdapter
import com.example.myfood.entity.PurchaseList
import com.example.pec1.R
import com.example.pec1.databinding.PurchaseListFragmentBinding


class PurchaseListFragment : Fragment() {
    private var _binding: PurchaseListFragmentBinding? = null
    private val binding get() = _binding!!
    lateinit var purchaseAdapter: PurchaseListAdapter
    lateinit var purchasesMutableList: MutableList<PurchaseList>
    lateinit var data: ArrayList<PurchaseList>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PurchaseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillData()
        initData()
        initAdapter()
        searcher()
        initRecyclerView(view)
        initAddItem()
    }


    private fun initData() {
        data.add(PurchaseList(" ", " ", " ", " ", " "))
        purchasesMutableList = data.toMutableList()
    }

    private fun initAdapter() {
        purchaseAdapter = PurchaseListAdapter(
            purchaseList = purchasesMutableList,
            onClickListener = { purchaseItem -> onItemSelected(purchaseItem) },
            onClickDelete = { position -> onDeleteItem(position) },
            onClickUpdate = { purchaseItem -> onUpdateItem(purchaseItem) })
    }

    private fun searcher() {
        binding.etFilterPL.addTextChangedListener { userFilter ->
            val purchasesFiltered = purchasesMutableList.filter { purchase ->
                purchase.name.lowercase().contains(userFilter.toString().lowercase())
            }
            purchaseAdapter.updatePurchaseList(purchasesFiltered)
        }
    }

    private fun initRecyclerView(view: View) {
        binding.rvPL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPL.adapter = purchaseAdapter
    }

    private fun initAddItem() {
        binding.addPLItem.setOnClickListener {
            loadFragment(AddPurchaseFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onItemSelected(purchaseList: PurchaseList) {
        Toast.makeText(this.context, purchaseList.name, Toast.LENGTH_LONG)
    }

    private fun onDeleteItem(position: Int) {
        purchasesMutableList.removeAt(position)
        purchaseAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(purchaseList: PurchaseList) {
        // Toast.makeText(view, purchaseList.name, Toast.LENGTH_SHORT)
    }

    private fun fillData() {
        data = ArrayList<PurchaseList>()

        ///// REPLACE /////
        for (i in 1..20) {
            data.add(PurchaseList("Item " + i, "1", "Unidad", "10,00", "€"))
        }

        //////////////////
    }
}
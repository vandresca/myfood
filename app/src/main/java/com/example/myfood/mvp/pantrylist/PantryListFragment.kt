package com.example.myfood.mvp.pantrylist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.PantryListFragmentBinding
import com.example.myfood.mvp.addpantryproduct.AddPantryFragment
import com.example.myfood.mvp.optionaddpantry.OptionAddPantryFragment
import com.example.myfood.mvp.pantryfeature.PantryFeatureFragment

class PantryListFragment : Fragment(), PantryListContract.View {
    private var _binding: PantryListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pantryListPresenter: PantryListPresenter
    private lateinit var pantryListModel: PantryListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PantryListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    override fun showUpdatePurchaseScreen(idPurchase: String) {
        loadFragment(AddPantryFragment(Constant.MODE_UPDATE, idPurchase))
    }

    private fun initialize() {
        binding.layoutPantryList.visibility = View.INVISIBLE
        pantryListModel = PantryListModel()
        pantryListPresenter = PantryListPresenter(
            this,
            pantryListModel, this, requireContext()
        )
        val idUser = pantryListPresenter.getUserId()
        pantryListPresenter.setIdUser(idUser)
        initAddPurchaseClick()
        initSearcher()
        val currentLanguage = pantryListPresenter.getCurrentLanguage()
        this.mutableTranslations = pantryListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
    }

    override fun onUserIdLoaded(idUser: String?) {
        pantryListPresenter.setIdUser(idUser!!)
        initAddPurchaseClick()
        initSearcher()
    }


    private fun initSearcher() {
        binding.etFilterPL.addTextChangedListener { watchText ->
            pantryListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(purchaseAdapter: PantryListAdapter) {
        binding.rvPL.layoutManager = LinearLayoutManager(this.context)
        binding.rvPL.adapter = purchaseAdapter
        binding.tvPricePLProduct.text = String.format("%.2f", purchaseAdapter.getTotalPrice())
        binding.tvTotalPLProduct.visibility = View.VISIBLE
        binding.tvPricePLProduct.visibility = View.VISIBLE
        binding.tvCurrencyPLProduct.visibility = View.VISIBLE
    }

    private fun initAddPurchaseClick() {
        binding.addPLItem.setOnClickListener {
            loadFragment(OptionAddPantryFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    @SuppressLint("SetTextI18n")
    override fun setTranslations() {
        binding.layoutPantryList.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(Constant.TITLE_PANTRY_LIST)!!.text
        binding.etFilterPL.hint = mutableTranslations?.get(Constant.FIELD_SEARCH)!!.text
        binding.tvTotalPLProduct.text =
            "${mutableTranslations?.get(Constant.LABEL_TOTAL)!!.text}:  "
        binding.tvCurrencyPLProduct.text = " ${pantryListPresenter.getCurrentCurrency()}"
    }

    override fun showPantryProduct(idPantry: String) {
        loadFragment(PantryFeatureFragment(idPantry))
    }
}

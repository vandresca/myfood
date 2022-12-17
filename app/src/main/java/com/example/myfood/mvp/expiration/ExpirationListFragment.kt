package com.example.myfood.mvp.expiration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.ExpirationListFragmentBinding
import com.example.myfood.popup.Popup

class ExpirationListFragment : Fragment(), ExpirationListContract.View {

    private var _binding: ExpirationListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var expirationListPresenter: ExpirationListPresenter
    private lateinit var expirationListModel: ExpirationListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = ExpirationListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        binding.layoutExpiration.visibility = View.INVISIBLE
        expirationListModel = ExpirationListModel()
        expirationListPresenter = ExpirationListPresenter(
            this,
            expirationListModel, this, requireContext()
        )
        val idUser = expirationListPresenter.getUserId()
        expirationListPresenter.setIdUser(idUser)
        initSearcher()
        val currentLanguage = expirationListPresenter.getCurrentLanguage()
        this.mutableTranslations = expirationListPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()
        initButtons()
    }

    private fun initButtons() {
        binding.btnAllEL.setOnClickListener { expirationListPresenter.filterAll() }
        binding.btnExpiredEL.setOnClickListener { expirationListPresenter.filterExpired() }
        binding.btnZeroToTenEL.setOnClickListener { expirationListPresenter.filter0to10days() }
        binding.btnMoreThanTenEL.setOnClickListener { expirationListPresenter.filterMore10days() }
        binding.btnRemoveAlLExpired.setOnClickListener {
            Popup.showConfirm(
                requireContext(),
                resources,
                mutableTranslations?.get(Constant.MSG_REMOVE_ALL_EXPIRED_QUESTION)!!.text,
                mutableTranslations?.get(Constant.BTN_YES)!!.text,
                mutableTranslations?.get(Constant.BTN_NO)!!.text
            )
            { expirationListPresenter.removeExpired() }
        }
    }

    private fun initSearcher() {
        binding.etFilterEL.addTextChangedListener { watchText ->
            expirationListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(expirationListAdapter: ExpirationListAdapter) {
        binding.rvEL.layoutManager = LinearLayoutManager(this.context)
        binding.rvEL.adapter = expirationListAdapter
    }

    @SuppressLint("SetTextI18n")
    override fun setTranslations() {
        binding.layoutExpiration.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(Constant.TITLE_EXPIRATION_LIST)!!.text
        binding.etFilterEL.hint = mutableTranslations?.get(Constant.FIELD_SEARCH)!!.text
        binding.lELTotal.text = "${mutableTranslations?.get(Constant.LABEL_TOTAL)!!.text}:  "
        binding.lELPrice.text = mutableTranslations?.get(Constant.LABEL_PRICE)!!.text
        binding.lELRemain.text = mutableTranslations?.get(Constant.LABEL_REMAIN)!!.text
        binding.btnAllEL.text = mutableTranslations?.get(Constant.BTN_ALL)!!.text
        binding.btnExpiredEL.text = mutableTranslations?.get(Constant.BTN_EXPIRED)!!.text
        binding.btnZeroToTenEL.text = mutableTranslations?.get(Constant.BTN_0_TO_10_DAYS)!!.text
        binding.btnMoreThanTenEL.text = mutableTranslations?.get(Constant.BTN_MORE_10_DAYS)!!.text
        binding.btnRemoveAlLExpired.text =
            mutableTranslations?.get(Constant.BTN_REMOVE_ALL_EXPIRED)!!.text
        binding.tvELCurrency.text = expirationListPresenter.getCurrentCurrency()
    }
}
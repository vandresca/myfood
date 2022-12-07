package com.example.myfood.mvp.expiration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ExpirationListFragmentBinding
import com.example.myfood.popup.Popup

class ExpirationListFragment : Fragment(), ExpirationListContract.View {

    private var _binding: ExpirationListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var expirationListPresenter: ExpirationListPresenter
    private lateinit var expirationListModel: ExpirationListModel
    private lateinit var mutableTranslations: MutableMap<String, Translation>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        expirationListModel.getInstance(requireContext())
        expirationListModel.getUserId(this)
        expirationListModel.getCurrentLanguage(this)
    }

    override fun onUserIdLoaded(idUser: String) {
        expirationListPresenter = ExpirationListPresenter(this, ExpirationListModel(), idUser)
        initSearcher()
        binding.btnAllEL.setOnClickListener { expirationListPresenter.filterAll() }
        binding.btnExpiredEL.setOnClickListener { expirationListPresenter.filterExpired() }
        binding.btnZeroToTenEL.setOnClickListener { expirationListPresenter.filter0to10days() }
        binding.btnMoreThanTenEL.setOnClickListener { expirationListPresenter.filterMore10days() }
        binding.btnRemoveAlLExpired.setOnClickListener {
            Popup.showConfirm(
                requireContext(),
                resources,
                mutableTranslations["removeAllExpiredQuestion"]!!.text,
                mutableTranslations["yes"]!!.text,
                mutableTranslations["no"]!!.text
            )
            { expirationListPresenter.removeExpired() }
        }
    }

    private fun initSearcher() {
        binding.etFilterEL.addTextChangedListener { watchText ->
            expirationListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(expirationAdapter: ExpirationListAdapter) {
        binding.rvEL.layoutManager = LinearLayoutManager(this.context)
        binding.rvEL.adapter = expirationAdapter
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onTranslationsLoaded(translations: List<Translation>) {
        mutableTranslations = mutableMapOf<String, Translation>()
        translations.forEach {
            mutableTranslations.put(it.word, it)
        }
        setTranslations()
    }

    private fun setTranslations() {
        binding.layoutExpiration.visibility = View.VISIBLE
        binding.header.titleHeader.text = mutableTranslations["expirationTitle"]!!.text
        binding.etFilterEL.hint = mutableTranslations["search"]!!.text
        binding.lELTotal.text = mutableTranslations["total"]!!.text + ": "
        binding.lELPrice.text = mutableTranslations["price"]!!.text
        binding.lELRemain.text = mutableTranslations["remain"]!!.text
        binding.btnAllEL.text = mutableTranslations["all"]!!.text
        binding.btnExpiredEL.text = mutableTranslations["expirated"]!!.text
        binding.btnZeroToTenEL.text = mutableTranslations["zeroToTenDays"]!!.text
        binding.btnMoreThanTenEL.text = mutableTranslations["moreThanTenDays"]!!.text
        binding.btnRemoveAlLExpired.text = mutableTranslations["removeAllExpired"]!!.text
    }

    override fun onCurrentLanguageLoaded(language: String) {
        expirationListModel.getTranslations(this, language.toInt())
    }
}
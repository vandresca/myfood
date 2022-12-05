package com.example.myfood.mvp.expiration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfood.R
import com.example.myfood.databinding.ExpirationListFragmentBinding
import com.example.myfood.popup.Popup

class ExpirationListFragment : Fragment(), ExpirationListContract.View {

    private var _binding: ExpirationListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var expirationListPresenter: ExpirationListPresenter

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
        binding.header.titleHeader.text = "Expiration List"
        val expirationListModel = ExpirationListModel()
        expirationListModel.getInstance(requireContext())
        expirationListModel.getUserId(this)
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
                "¿Deseas eliminar todos los productos caducados en despensa?"
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
}
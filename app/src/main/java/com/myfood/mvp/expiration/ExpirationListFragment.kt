package com.myfood.mvp.expiration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfood.constants.Constant
import com.myfood.databinding.ExpirationListFragmentBinding
import com.myfood.popup.Popup

class ExpirationListFragment : Fragment(), ExpirationListContract.View {

    //Declaración variables globales
    private var _binding: ExpirationListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var expirationListPresenter: ExpirationListPresenter
    private lateinit var mutableTranslations: MutableMap<String, String>

    //Método onCreateView
    //Mientras se está creando la vista
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Usamos un binding para utilizar en la clase los elementos de la pantalla
        //Caducidad
        _binding = ExpirationListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Método onViewCreated
    //Cuando la vista está creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Hacemos que el layout principal sea invisible hasta que no se carguen los datos
        binding.layoutExpiration.visibility = View.INVISIBLE

        //Creamos el presentador
        expirationListPresenter = ExpirationListPresenter(this, requireContext())

        //Inicializamos el buscador
        initSearcher()

        //Obtenemos las traducciones de pantalla
        mutableTranslations = expirationListPresenter.getTranslationsScreen()
        setTranslations()

        //Inicializamos los botones
        initButtons()
    }

    private fun initButtons() {

        //Incializamos el click para mostrar todos los tipos de caducidad de producto
        binding.btnAllEL.setOnClickListener { expirationListPresenter.filterAll() }

        //Inicializamos el click para mostrar los productos caducados
        binding.btnExpiredEL.setOnClickListener { expirationListPresenter.filterExpired() }

        //Inicializamos el click para mostrar los productos que le faltan entre 0 y 10 dias de
        //caducidad
        binding.btnZeroToTenEL.setOnClickListener { expirationListPresenter.filter0to10days() }

        //Inicializamos el click para mostrar los productos que le faltan más de 10 dias para
        //caducar
        binding.btnMoreThanTenEL.setOnClickListener { expirationListPresenter.filterMore10days() }

        //Inicializamos el click para eliminar todos los productos caducados del usuario
        binding.btnRemoveAlLExpired.setOnClickListener {

            //Preguntamos al usuario si realmente desea eliminar los productos caducados y en
            //caso afirmativo realizamos la acción
            Popup.showConfirm(
                requireContext(),
                resources,
                mutableTranslations[Constant.MSG_REMOVE_ALL_EXPIRED_QUESTION]!!,
                mutableTranslations[Constant.BTN_YES]!!,
                mutableTranslations[Constant.BTN_NO]!!
            )
            { expirationListPresenter.removeExpired() }
        }
    }

    private fun initSearcher() {
        //Inicializamos un evento que esta pendiente de cambios en el campo de texto del buscador
        //y cuando detecta algo llama al metodo dofilter del presentador
        binding.etFilterEL.addTextChangedListener { watchText ->
            expirationListPresenter.doFilter(watchText)
        }
    }

    override fun initRecyclerView(expirationListAdapter: ExpirationListAdapter) {
        //Inicializamos un  linear layour manager y el adapter del recyclerview
        binding.rvEL.layoutManager = LinearLayoutManager(this.context)
        binding.rvEL.adapter = expirationListAdapter

        //Incializamos la cabecera que va justo encima del recyclerview
        binding.tvELPrice.text = String.format("%.2f", expirationListAdapter.getTotalPrice())
    }

    //Establecemos las traducciones
    @SuppressLint("SetTextI18n")
    override fun setTranslations() {
        binding.layoutExpiration.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations[Constant.TITLE_EXPIRATION_LIST]!!
        binding.etFilterEL.hint =
            mutableTranslations[Constant.FIELD_SEARCH]!!
        binding.lELTotal.text =
            "${mutableTranslations[Constant.LABEL_TOTAL]!!}:  "
        binding.lELPrice.text =
            mutableTranslations[Constant.LABEL_PRICE]!!
        binding.lELRemain.text =
            mutableTranslations[Constant.LABEL_REMAIN]!!
        binding.btnAllEL.text =
            mutableTranslations[Constant.BTN_ALL]!!
        binding.btnExpiredEL.text =
            mutableTranslations[Constant.BTN_EXPIRED]!!
        binding.btnZeroToTenEL.text =
            mutableTranslations[Constant.BTN_0_TO_10_DAYS]!!
        binding.btnMoreThanTenEL.text =
            mutableTranslations[Constant.BTN_MORE_10_DAYS]!!
        binding.btnRemoveAlLExpired.text =
            mutableTranslations[Constant.BTN_REMOVE_ALL_EXPIRED]!!
        binding.tvELCurrency.text = expirationListPresenter.getCurrentCurrency()
    }
}
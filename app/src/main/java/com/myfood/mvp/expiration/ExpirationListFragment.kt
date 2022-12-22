package com.myfood.mvp.expiration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.myfood.databases.databasesqlite.entity.Translation
import com.myfood.databinding.ExpirationListFragmentBinding
import com.myfood.popup.Popup

class ExpirationListFragment : Fragment(), ExpirationListContract.View {

    //Declaración variables globales
    private var _binding: ExpirationListFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var expirationListPresenter: ExpirationListPresenter
    private lateinit var expirationListModel: ExpirationListModel
    private var mutableTranslations: MutableMap<String, Translation>? = null

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

        //Creamos el modelo
        expirationListModel = ExpirationListModel()

        //Creamos el presentador
        expirationListPresenter = ExpirationListPresenter(
            this,
            expirationListModel, this, requireContext()
        )

        //Obtenemos el id de usuario de la App
        val idUser = expirationListPresenter.getUserId()

        //Establecemos el usuario para mostrar la lista de productos
        expirationListPresenter.setIdUser(idUser)

        //Inicializamos el buscador
        initSearcher()

        //Obtenemos el idioma de la App y establecemos las traducciones
        val currentLanguage = expirationListPresenter.getCurrentLanguage()
        this.mutableTranslations = expirationListPresenter.getTranslations(currentLanguage.toInt())
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
                mutableTranslations?.get(com.myfood.constants.Constant.MSG_REMOVE_ALL_EXPIRED_QUESTION)!!.text,
                mutableTranslations?.get(com.myfood.constants.Constant.BTN_YES)!!.text,
                mutableTranslations?.get(com.myfood.constants.Constant.BTN_NO)!!.text
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
    }

    //Establecemos las traducciones
    @SuppressLint("SetTextI18n")
    override fun setTranslations() {
        binding.layoutExpiration.visibility = View.VISIBLE
        binding.header.titleHeader.text =
            mutableTranslations?.get(com.myfood.constants.Constant.TITLE_EXPIRATION_LIST)!!.text
        binding.etFilterEL.hint =
            mutableTranslations?.get(com.myfood.constants.Constant.FIELD_SEARCH)!!.text
        binding.lELTotal.text =
            "${mutableTranslations?.get(com.myfood.constants.Constant.LABEL_TOTAL)!!.text}:  "
        binding.lELPrice.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_PRICE)!!.text
        binding.lELRemain.text =
            mutableTranslations?.get(com.myfood.constants.Constant.LABEL_REMAIN)!!.text
        binding.btnAllEL.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_ALL)!!.text
        binding.btnExpiredEL.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_EXPIRED)!!.text
        binding.btnZeroToTenEL.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_0_TO_10_DAYS)!!.text
        binding.btnMoreThanTenEL.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_MORE_10_DAYS)!!.text
        binding.btnRemoveAlLExpired.text =
            mutableTranslations?.get(com.myfood.constants.Constant.BTN_REMOVE_ALL_EXPIRED)!!.text
        binding.tvELCurrency.text = expirationListPresenter.getCurrentCurrency()
    }
}
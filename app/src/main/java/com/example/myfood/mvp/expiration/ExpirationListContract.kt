package com.example.myfood.mvp.expiration

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.ExpirationListEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Caducidad
interface ExpirationListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun initRecyclerView(expirationListAdapter: ExpirationListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun initData()
        fun loadData(expirationListEntity: ExpirationListEntity)
        fun doFilter(userFilter: Editable?)
        fun onRemovedExpired(result: SimpleResponseEntity)
        fun getCurrentCurrency(): String
        fun getUserId(): String
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getUserId(): String
        fun getCurrentCurrency(): String
        fun getExpirationList(
            expiration: String,
            idUser: String
        ): MutableLiveData<ExpirationListEntity>

        fun removeExpired(idUser: String): MutableLiveData<SimpleResponseEntity>
    }
}
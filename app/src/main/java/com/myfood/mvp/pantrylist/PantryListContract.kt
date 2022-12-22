package com.myfood.mvp.pantrylist

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.myfood.databases.databasemysql.entity.PantryListEntity
import com.myfood.databases.databasemysql.entity.SimpleResponseEntity
import com.myfood.interfaces.Translatable

// Interfaz que obliga a implementar los siguientes métodos para la pantalla
// Despensa
interface PantryListContract {

    //Vista
    //Implementa la interfaz Translable.View
    interface View : Translatable.View {
        fun showUpdatePantryScreen(idPurchase: String)
        fun showPantryProduct(idPantry: String)
        fun initRecyclerView(purchaseAdapter: PantryListAdapter)
    }

    //Presentador
    //Implementa la interfaz Translable.Presenter
    interface Presenter : Translatable.Presenter {
        fun loadData(pantryListEntity: PantryListEntity)
        fun initData()
        fun doFilter(userFilter: Editable?)
        fun getUserId(): String
        fun getPantryList(idUser: String): MutableLiveData<PantryListEntity>
        fun getCurrentCurrency(): String
    }

    //Modelo
    //Implementa la interfaz Translable.Model
    interface Model : Translatable.Model {
        fun getInstance(context: Context)
        fun getPantryList(idUser: String): MutableLiveData<PantryListEntity>
        fun getCurrentCurrency(): String
        fun deletePantry(id: String): MutableLiveData<SimpleResponseEntity>
        fun getUserId(): String
    }
}
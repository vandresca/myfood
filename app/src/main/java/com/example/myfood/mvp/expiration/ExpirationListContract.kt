package com.example.myfood.mvp.expiration

import android.content.Context
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.example.myfood.interfaces.Translatable
import com.example.myfood.mvvm.data.model.ExpirationListEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity

interface ExpirationListContract {
    interface View : Translatable.View {
        fun initRecyclerView(expirationListAdapter: ExpirationListAdapter)
        fun setTranslations()
    }

    interface Presenter : Translatable.Presenter {
        fun initData()
        fun loadData(expirationListEntity: ExpirationListEntity)
        fun doFilter(userFilter: Editable?)
        fun onRemovedExpired(result: SimpleResponseEntity)
        fun getCurrentCurrency(): String
        fun getUserId(): String
    }

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
package com.example.myfood.mvp.pantrylist

import android.text.Editable
import androidx.lifecycle.LifecycleOwner
import com.example.myfood.interfaces.Translatable

interface PantryListContract {
    interface View : Translatable.View {
        fun onUserIdLoaded(idUser: String)
        fun showUpdatePurchaseScreen(idPurchase: String)
        fun initRecyclerView(purchaseAdapter: PantryListAdapter)
    }

    interface Presenter {
        fun loadData(response: String?)
        fun initData()
        fun doFilter(userFilter: Editable?)
    }

    interface Model : Translatable.Model {
        fun getPantryList(idUser: String, callback: (String?) -> Unit)
        fun deletePantry(id: String)
        fun getUserId(application: LifecycleOwner, callback: (String) -> Unit)
    }
}
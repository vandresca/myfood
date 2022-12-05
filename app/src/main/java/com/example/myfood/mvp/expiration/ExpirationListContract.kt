package com.example.myfood.mvp.expiration

import android.text.Editable

interface ExpirationListContract {
    interface View {
        fun onUserIdLoaded(idUser: String)
        fun initRecyclerView(expirationListAdapter: ExpirationListAdapter)
    }

    interface Presenter {
        fun loadData(response: String?)
        fun initData()
        fun doFilter(watchText: Editable?)
        fun onRemovedExpired(response: String?)
    }

    interface Model {
        fun getUserId(application: ExpirationListFragment)
        fun getExpirationList(
            application: ExpirationListPresenter,
            expiration: String,
            idUser: String
        )

        fun removeExpired(application: ExpirationListPresenter, idUser: String)
    }
}
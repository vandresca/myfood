package com.example.myfood.mvp.expiration

import android.text.Editable
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.LanguageType

interface ExpirationListContract {
    interface View {
        fun onUserIdLoaded(idUser: String)
        fun initRecyclerView(expirationListAdapter: ExpirationListAdapter)
        fun onTranslationsLoaded(translations: List<Translation>)
        fun onCurrentLanguageLoaded(language: String)
    }

    interface Presenter {
        fun loadData(response: String?)
        fun initData()
        fun doFilter(watchText: Editable?)
        fun onRemovedExpired(response: String?)
    }

    interface Model {
        fun getUserId(application: ExpirationListFragment)
        fun getCurrentLanguage(application: ExpirationListFragment)
        fun getTranslations(
            application: ExpirationListFragment,
            language: Int = LanguageType.ENGLISH.int
        )

        fun getExpirationList(
            application: ExpirationListPresenter,
            expiration: String,
            idUser: String
        )

        fun removeExpired(application: ExpirationListPresenter, idUser: String)
    }
}
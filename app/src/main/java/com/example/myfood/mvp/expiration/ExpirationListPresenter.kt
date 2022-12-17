package com.example.myfood.mvp.expiration

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.enum.ExpirationType
import com.example.myfood.mvvm.data.model.ExpirationListEntity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity


class ExpirationListPresenter(
    private val expirationListView: ExpirationListContract.View,
    private val expirationListModel: ExpirationListContract.Model,
    private val expirationListFragment: ExpirationListFragment,
    context: Context
) : ExpirationListContract.Presenter {
    private lateinit var expirationAdapter: ExpirationListAdapter
    private var expirationMutableList: MutableList<ExpirationList> = mutableListOf()
    private lateinit var idUser: String

    init {
        expirationListModel.getInstance(context)
    }

    fun setIdUser(idUser: String) {
        this.idUser = idUser
        filterAll()
    }

    fun filterExpired() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_EXPIRED.type, idUser)
            .observe(expirationListFragment) { data -> loadData(data) }
    }

    fun filterMore10days() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_MORE10DAYS.type, idUser)
            .observe(expirationListFragment) { data -> loadData(data) }
    }

    fun filter0to10days() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_OTO10DAYS.type, idUser)
            .observe(expirationListFragment) { data -> loadData(data) }
    }

    fun filterAll() {
        expirationListModel.getExpirationList(ExpirationType.EXPIRATION_ALL.type, idUser)
            .observe(expirationListFragment) { data -> loadData(data) }
    }

    fun removeExpired() {
        expirationListModel.removeExpired(idUser)
            .observe(expirationListFragment) { response -> onRemovedExpired(response) }
    }

    override fun onRemovedExpired(result: SimpleResponseEntity) {
        if (result.status == Constant.OK) {
            filterAll()
        }
    }

    override fun loadData(expirationListEntity: ExpirationListEntity) {
        expirationMutableList = expirationListEntity.toMVP().toMutableList()
        initData()
    }

    override fun initData() {
        expirationAdapter = ExpirationListAdapter(
            expirationList = expirationMutableList,
            currency = getCurrentCurrency()
        )
        Handler(Looper.getMainLooper()).post {
            expirationListView.initRecyclerView(expirationAdapter)
        }
    }

    override fun doFilter(userFilter: Editable?) {
        val expirationFiltered = expirationMutableList.filter { expiration ->
            expiration.name.lowercase().contains(userFilter.toString().lowercase())
        }
        expirationAdapter.updateExpirationList(expirationFiltered)
    }

    override fun getUserId(): String {
        return expirationListModel.getUserId()
    }

    override fun getCurrentLanguage(): String {
        return expirationListModel.getCurrentLanguage()
    }

    override fun getCurrentCurrency(): String {
        return expirationListModel.getCurrentCurrency()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = expirationListModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

}

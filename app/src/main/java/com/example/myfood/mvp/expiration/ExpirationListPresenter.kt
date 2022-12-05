package com.example.myfood.mvp.expiration

import android.os.Handler
import android.os.Looper
import android.text.Editable
import org.json.JSONObject

class ExpirationListPresenter(
    private val expirationListView: ExpirationListContract.View,
    private val expirationListModel: ExpirationListContract.Model,
    private var idUser: String
) : ExpirationListContract.Presenter {
    private lateinit var expirationAdapter: ExpirationListAdapter
    private var expirationMutableList: MutableList<ExpirationList> = mutableListOf()

    companion object {
        private const val EXPIRATION_ALL = "All"
        private const val EXPIRATION_OTO10DAYS = "0to10days"
        private const val EXPIRATION_MORE10DAYS = "more10days"
        private const val EXPIRATION_EXPIRED = "expired"
    }

    init {
        filterAll()
    }

    fun filterExpired() {
        expirationListModel.getExpirationList(this, EXPIRATION_EXPIRED, idUser)
    }

    fun filterMore10days() {
        expirationListModel.getExpirationList(this, EXPIRATION_MORE10DAYS, idUser)
    }

    fun filter0to10days() {
        expirationListModel.getExpirationList(this, EXPIRATION_OTO10DAYS, idUser)
    }

    fun filterAll() {
        expirationListModel.getExpirationList(this, EXPIRATION_ALL, idUser)
    }

    fun removeExpired() {
        expirationListModel.removeExpired(this, idUser)
    }

    override fun onRemovedExpired(response: String?) {
        filterAll()
    }

    override fun loadData(response: String?) {
        val json = JSONObject(response)
        val products = json.getJSONArray("products")
        val expirationList: ArrayList<ExpirationList> = ArrayList()
        for (i in 0 until products.length()) {
            val item = products.get(i) as JSONObject
            expirationList.add(
                ExpirationList(
                    item.get("name").toString(),
                    item.get("days").toString(),
                    item.get("expiration").toString(),
                    item.get("price").toString()
                )
            )
        }
        expirationMutableList = expirationList.toMutableList()
        initData()
    }

    override fun initData() {
        expirationAdapter = ExpirationListAdapter(
            expirationList = expirationMutableList
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


}

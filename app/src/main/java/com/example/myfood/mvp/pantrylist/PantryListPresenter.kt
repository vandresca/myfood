package com.example.myfood.mvp.pantrylist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.example.myfood.constants.Constant
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.PantryListEntity

class PantryListPresenter(
    private val pantryListView: PantryListContract.View,
    private val pantryListModel: PantryListContract.Model,
    private val pantryListFragment: PantryListFragment,
    context: Context
) : PantryListContract.Presenter {
    private lateinit var pantryAdapter: PantryListAdapter
    private var pantryMutableList: MutableList<PantryList> = mutableListOf()
    private lateinit var idUser: String

    init {
        pantryListModel.getInstance(context)
    }

    fun setIdUser(idUser: String) {
        this.idUser = idUser
        pantryListModel.getPantryList(idUser).observe(pantryListFragment) { data -> loadData(data) }
    }

    override fun loadData(pantryListEntity: PantryListEntity) {
        if (pantryListEntity.status == Constant.OK) {
            pantryMutableList = pantryListEntity.toMVP().toMutableList()
            initData()
        }
    }

    override fun getPantryList(idUser: String): MutableLiveData<PantryListEntity> {
        return pantryListModel.getPantryList(idUser)
    }

    override fun initData() {
        pantryAdapter = PantryListAdapter(
            purchaseList = pantryMutableList,
            onClickListener = { purchaseItem -> onItemSelected(purchaseItem) },
            onClickDelete = { position, purchaseItem -> onDeleteItem(position, purchaseItem) },
            onClickUpdate = { purchaseItem -> onUpdateItem(purchaseItem) },
            currency = "  ${getCurrentCurrency()}"
        )
        Handler(Looper.getMainLooper()).post {
            pantryListView.initRecyclerView(pantryAdapter)
        }
    }

    override fun doFilter(userFilter: Editable?) {
        Handler(Looper.getMainLooper()).post {
            val purchasesFiltered = pantryMutableList.filter { purchase ->
                purchase.name.lowercase().contains(userFilter.toString().lowercase())
            }
            pantryAdapter.updatePantryList(purchasesFiltered)
        }
    }

    override fun getUserId(): String {
        return pantryListModel.getUserId()
    }

    override fun getCurrentLanguage(): String {
        return pantryListModel.getCurrentLanguage()
    }

    override fun getCurrentCurrency(): String {
        return pantryListModel.getCurrentCurrency()
    }

    override fun getTranslations(language: Int): MutableMap<String, Translation> {
        val mutableTranslations: MutableMap<String, Translation> = mutableMapOf()
        val translations = pantryListModel.getTranslations(language)
        translations.forEach {
            mutableTranslations[it.word] = it
        }
        return mutableTranslations
    }

    private fun onItemSelected(purchaseList: PantryList) {
        pantryListView.showPantryProduct(purchaseList.id)
    }

    private fun onDeleteItem(position: Int, purchase: PantryList) {
        pantryListModel.deletePantry(purchase.id)
        pantryMutableList.removeAt(position)
        pantryAdapter.notifyItemRemoved(position)
    }

    private fun onUpdateItem(purchaseList: PantryList) {
        pantryListView.showUpdatePurchaseScreen(purchaseList.id)
    }
}

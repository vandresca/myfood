package com.example.myfood.mvp.pantrylist

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.mvvm.data.model.PantryListEntity

class PantryListPresenter(
    private val pantryListView: PantryListContract.View,
    private val pantryListModel: PantryListContract.Model,
    private val pantryListFragment: PantryListFragment,
    context: Context
) : PantryListContract.Presenter {
    private lateinit var pantryAdapter: PantryListAdapter
    private var pantryMutableList: MutableList<PantryList> = mutableListOf()
    private var pantryFiltered: MutableList<PantryList> = mutableListOf()
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
            pantryFiltered = pantryMutableList.filter { purchase ->
                purchase.name.lowercase().contains(userFilter.toString().lowercase())
            }.toMutableList()
            pantryAdapter.updatePantryList(pantryFiltered)
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

    private fun onItemSelected(pantryList: PantryList) {
        pantryListView.showPantryProduct(pantryList.id)
    }

    private fun onDeleteItem(position: Int, pantry: PantryList) {
        pantryListModel.deletePantry(pantry.id)
            .observe(pantryListFragment.viewLifecycleOwner) { result ->
                if (result.status == Constant.OK) {
                    var count = 0
                    var pos: Int = 0
                    pantryMutableList.forEach {
                        if (it.id == pantry.id) {
                            pos = count
                        }
                        count += 1
                    }
                    pantryMutableList.removeAt(pos)
                    pantryFiltered.removeAt(position)
                    pantryAdapter.notifyItemRemoved(position)
                }
            }

    }

    private fun onUpdateItem(pantryList: PantryList) {
        pantryListView.showUpdatePurchaseScreen(pantryList.id)
    }
}

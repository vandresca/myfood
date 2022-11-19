package com.example.myfood.mvp.purchaselist

class PurchaseListModel : PurchaseListContract.Model {
    override fun getList(): ArrayList<PurchaseList> {
        return PurchaseListProvider.getData()
    }
}
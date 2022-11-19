package com.example.myfood.purchaselist

class PurchaseListModel : PurchaseListContract.Model {
    override fun getList(): ArrayList<PurchaseList> {
        return PurchaseListProvider.getData()
    }
}
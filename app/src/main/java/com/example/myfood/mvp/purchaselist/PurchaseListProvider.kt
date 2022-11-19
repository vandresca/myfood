package com.example.myfood.mvp.purchaselist

class PurchaseListProvider {
    companion object {
        fun getData(): ArrayList<PurchaseList> {
            val data: ArrayList<PurchaseList> = ArrayList()
            for (i in 1..20) {
                data.add(PurchaseList("Item " + i, "1", "Unidad", "10,00", "€"))
            }
            return data
        }
    }
}
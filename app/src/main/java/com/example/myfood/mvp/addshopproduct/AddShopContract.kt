package com.example.myfood.mvp.addshopproduct

interface AddShopContract {
    interface View {
        fun onInsertedShop(response: String?)
        fun onUpdatedShop(response: String?)
        fun onLoadShopToUpdate(response: String?)
    }

    interface Model {
        fun getShopProduct(application: AddShopFragment, idShop: String)
        fun getUserId(application: AddShopFragment)
        fun insertShop(
            application: AddShopFragment,
            name: String,
            quantity: String,
            quantityUnit: String,
            userId: String
        )

        fun updateShop(
            application: AddShopFragment,
            name: String,
            quantity: String,
            quantityUnit: String,
            idShop: String
        )

    }
}
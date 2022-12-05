package com.example.myfood.mvp.config

class ConfigPresenter(
    private val ConfigView: ConfigContract.View,
    private val ConfigModel: ConfigContract.Model,
    idUser: String
) : ConfigContract.Presenter {

    init {
        //shopListModel.getShopList(this, idUser)
    }


}

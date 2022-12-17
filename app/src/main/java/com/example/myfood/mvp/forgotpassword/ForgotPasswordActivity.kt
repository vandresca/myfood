package com.example.myfood.mvp.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.constants.Constant
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityForgotPasswordBinding
import com.example.myfood.mvp.login.LoginActivity
import com.example.myfood.mvvm.data.model.SimpleResponseEntity
import com.example.myfood.popup.Popup

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContract.View {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var forgotPasswordModel: ForgotPasswordModel
    private var mutableTranslations: MutableMap<String, Translation>? = null
    private lateinit var idLanguage: String
    private lateinit var forgotPasswordPresenter: ForgotPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutForgotPassword.visibility = View.INVISIBLE
        forgotPasswordModel = ForgotPasswordModel()
        forgotPasswordPresenter = ForgotPasswordPresenter(this, forgotPasswordModel, this)
        idLanguage = forgotPasswordPresenter.getCurrentLanguage()
        this.mutableTranslations = forgotPasswordPresenter.getTranslations(idLanguage.toInt())
        setTranslations()
        initButtons()
    }

    private fun initButtons() {
        binding.btnSendLink.setOnClickListener {
            val email = binding.etEmailForgotPass.text.toString()
            forgotPasswordPresenter.sendLink(idLanguage, email).observe(this)
            { response -> onSendLink(response) }
        }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    override fun setTranslations() {
        binding.layoutForgotPassword.visibility = View.VISIBLE
        binding.tvForgotPasswordTitle.text =
            mutableTranslations?.get(Constant.TITLE_FORGOTTEN_PASSWORD)!!.text
        binding.tvForgotPasswordText.text =
            mutableTranslations?.get(Constant.MSG_FORGOTTEN_PASSWORD_TEXT)!!.text
        binding.etEmailForgotPass.hint = mutableTranslations?.get(Constant.FIELD_EMAIL)!!.text
        binding.btnSendLink.text =
            mutableTranslations?.get(Constant.BTN_SEND_LINK_FORGOTTEN_PASSWORD)!!.text
    }

    override fun onSendLink(result: SimpleResponseEntity) {
        if (result.status == Constant.OK) {
            Popup.showInfo(
                this, resources,
                mutableTranslations?.get(Constant.MSG_FORGOTTEN_PASSWORD_TEXT_OK)!!.text
            )
        } else {
            Popup.showInfo(
                this, resources,
                mutableTranslations?.get(Constant.MSG_FORGOTTEN_PASSWORD_TEXT_KO)!!.text
            )
        }
    }
}
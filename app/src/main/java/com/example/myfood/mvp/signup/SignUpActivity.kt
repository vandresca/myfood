package com.example.myfood.mvp.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.constants.Constant
import com.example.myfood.constants.Constant.Companion.FIELD_SIGN_UP
import com.example.myfood.databases.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivitySignupBinding
import com.example.myfood.mvp.login.LoginActivity
import com.example.myfood.mvvm.data.model.OneValueEntity
import com.example.myfood.popup.Popup

class SignUpActivity : AppCompatActivity(), SignUpContract.View {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signUpModel: SignUpModel
    private lateinit var signUpPresenter: SignUpPresenter
    private var mutableTranslations: MutableMap<String, Translation>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutSignUp.visibility = View.INVISIBLE
        signUpModel = SignUpModel()
        signUpPresenter = SignUpPresenter(this, signUpModel, this)
        val currentLanguage = signUpPresenter.getCurrentLanguage()
        this.mutableTranslations = signUpPresenter.getTranslations(currentLanguage.toInt())
        setTranslations()

        binding.btnSignUp.setOnClickListener { signup() }
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun signup() {
        val name = binding.etNameSignUp.text.toString()
        val surnames = binding.etSurnamesSignUp.text.toString()
        val email = binding.etEmailSignUp.text.toString()
        val password = binding.etPasswordSignup.text.toString()
        val confirmPassword = binding.etConfirmPasswordSignUp.text.toString()
        val msg = signUpPresenter.getMsgResult(
            name, surnames, email, password, confirmPassword,
            mutableTranslations
        )
        if (msg.isNotEmpty()) {
            Popup.showInfo(this, resources, msg)
        } else {
            signUpModel.insertUser(name, surnames, email, password).observe(this)
            { result -> onInsertedUser(result) }
        }

    }

    override fun onInsertedUser(result: OneValueEntity) {
        if (result.status == Constant.OK) {
            Popup.showInfo(
                this,
                resources,
                mutableTranslations?.get(Constant.MSG_USER_INSERTED)!!.text
            ) { onClickOKButton() }
        }
    }

    private fun onClickOKButton() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    override fun setTranslations() {
        binding.layoutSignUp.visibility = View.VISIBLE
        binding.etConfirmPasswordSignUp.hint =
            mutableTranslations?.get(Constant.FIELD_CONFIRM_PASSWORD)!!.text
        binding.etEmailSignUp.hint = mutableTranslations?.get(Constant.FIELD_EMAIL)!!.text
        binding.etSurnamesSignUp.hint = mutableTranslations?.get(Constant.FIELD_SURNAMES)!!.text
        binding.etNameSignUp.hint = mutableTranslations?.get(Constant.FIELD_NAME)!!.text
        binding.etPasswordSignup.hint = mutableTranslations?.get(Constant.FIELD_PASSWORD)!!.text
        binding.btnSignUp.text = mutableTranslations?.get(FIELD_SIGN_UP)!!.text
    }
}
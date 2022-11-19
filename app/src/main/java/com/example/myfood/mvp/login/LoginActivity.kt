package com.example.myfood.mvp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.databasesqlite.entity.Translation
import com.example.myfood.databinding.ActivityLoginBinding
import com.example.myfood.enum.LanguageType
import com.example.myfood.mvp.main.MainActivity
import com.example.myfood.mvp.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        LoginModel.getInstance(this)
        LoginModel.getLanguages(this)
        LoginModel.getTranslations(this)

        binding.btnSignUpLogin.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun onTranslationsLoaded(translations: List<Translation>) {
        translations.forEach {
            when (it.word) {
                "name" -> binding.etNameLogin.hint = it.text
                "password" -> binding.etPasswordLogin.hint = it.text
                "signUp" -> binding.btnSignUpLogin.text = it.text
                "forgotPass" -> binding.btnPasswordForgotten.text = it.text
                "login" -> binding.btnLogin.text = it.text

            }
        }
    }

    fun onLanguagesLoaded(languages: List<String>) {
        spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sLanguageLogin.adapter = spinnerAdapter
        binding.sLanguageLogin.setSelection(LanguageType.ENGLISH.int - 1)
        initOnItemSelectedListener()
    }

    fun initOnItemSelectedListener() {
        binding.sLanguageLogin.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    updateLanguage(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    fun updateLanguage(position: Int) {
        LoginModel.getTranslations(this, position + 1)
    }

    /*
    fun onLanguagesLoaded(languages: List<String>){

        this?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                languages
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.sLanguageLogin.adapter = adapter
                binding.sLanguageLogin.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            updateLanguage(position)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
            }
        }
    }

     */

}
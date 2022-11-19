package com.example.myfood.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myfood.R
import com.example.myfood.databasesqlite.Language
import com.example.myfood.databasesqlite.RoomSingleton

class LoginActivity : AppCompatActivity() {

    private lateinit var db: RoomSingleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignUp = findViewById<Button>(R.id.buttonRegister)
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        val btnLogin = findViewById<Button>(R.id.buttonLogin)
        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        db = RoomSingleton.getInstance(application)

        var values: List<Language> = db.sqliteDao().getLanguages()

        Log.i("INFO", "============= > " + values)
    }
}
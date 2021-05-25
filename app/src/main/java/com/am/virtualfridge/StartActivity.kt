package com.am.virtualfridge

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class StartActivity : AppCompatActivity() {


    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser?.let{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private lateinit var login : Button
    private lateinit var  register : Button
    private lateinit var firebaseUser : FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        login = findViewById(R.id.login)
        register = findViewById(R.id.register)
        login.setOnClickListener{

            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        register. setOnClickListener{
            intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
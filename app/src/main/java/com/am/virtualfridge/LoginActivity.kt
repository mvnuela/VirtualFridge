package com.am.virtualfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.am.virtualfridge.fridge.MyFridgeActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.rengwuxian.materialedittext.MaterialEditText

class LoginActivity : AppCompatActivity() {

    private lateinit var password : MaterialEditText
    private lateinit var email : MaterialEditText
    private lateinit var btnLogin : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       val toolbar = findViewById<Toolbar>(R.id.toolbar)
       setSupportActionBar(toolbar)
        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        password = findViewById(R.id.password)
        email = findViewById(R.id.email)
        btnLogin = findViewById((R.id.btn_login))
        auth= FirebaseAuth.getInstance()

        btnLogin.setOnClickListener{
            val em = email.text.toString()
            val pass = password.text.toString()
            if(TextUtils.isEmpty(em) or TextUtils.isEmpty(pass))
                Toast.makeText(this,"All fields are required",Toast.LENGTH_LONG).show()
            else {
                auth.signInWithEmailAndPassword(em,pass).addOnCompleteListener { task : Task<AuthResult> ->

                    if(task.isSuccessful){
                        val intent = Intent(this, MyFridgeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }

                    else {
                        Toast.makeText(this, "Authentication Failed",Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }
}
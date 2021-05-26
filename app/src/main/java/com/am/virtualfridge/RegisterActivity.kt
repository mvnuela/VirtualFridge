package com.am.virtualfridge

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
//import android.widget.Toolbar
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rengwuxian.materialedittext.MaterialEditText
import androidx.appcompat.widget.Toolbar;

class RegisterActivity : AppCompatActivity() {

    private lateinit var username : MaterialEditText
    private lateinit var password : MaterialEditText
    private lateinit var email : MaterialEditText
    private lateinit var btnRegister : Button
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        email = findViewById(R.id.email)
        btnRegister = findViewById((R.id.btn_register))
        auth= FirebaseAuth.getInstance()

        btnRegister.setOnClickListener{

            val txt_username = username.text.toString()
            val txt_email = email.text.toString()
            val txt_password = password.text.toString()
            if(TextUtils.isEmpty(txt_username) or TextUtils.isEmpty(txt_email) or TextUtils.isEmpty(txt_password))

                Toast.makeText(this, "All things are required",Toast.LENGTH_LONG).show()
            else {
                register(txt_username,txt_password,txt_email)
            }

        }
    }

    fun register(username : String, pass : String, em : String ){

        auth.createUserWithEmailAndPassword(em,pass).addOnCompleteListener{task : Task<AuthResult> ->
            if (task.isSuccessful) {
                //Registration OK
                val firebaseUser = this.auth.currentUser!!
                val userid = firebaseUser.uid
                reference = FirebaseDatabase.getInstance("https://virtualfridge-47aca-default-rtdb.europe-west1.firebasedatabase.app/").getReference("users").child(userid)
                var hashmap = HashMap<String,String>()
                hashmap.put("id",userid)
                hashmap.put("username",username)
                hashmap.put("imageUrl","default")

                reference.setValue(hashmap).addOnCompleteListener{
                    task : Task<Void> ->
                    if(task.isSuccessful){
                        //TUTAJ MOZNA ZMIENIC AKTYWNOSC, ZEBY SIE INNA ODPALAŁA PO ZALOGOWANIU
                            //DOMYSLNIE ŁADUJE SIĘ LODÓWKA, ale nie ma opcji logout z tej aktywnosci
                                //trzeba uruchomic od nowa appke
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }


            } else {
                Toast.makeText(this,"You cant register",Toast.LENGTH_LONG).show()
            }

        }

    }
}
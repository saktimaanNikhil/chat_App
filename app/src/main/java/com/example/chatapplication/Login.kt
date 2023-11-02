 package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

 class Login : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSinUp: Button
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSinUp = findViewById(R.id.btnSignup)

        supportActionBar?.hide()

        btnSinUp.setOnClickListener {
            val intent = Intent(this, SinUp::class.java)
            startActivity(intent)
            finish()
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if(email.isNotEmpty()&& password.isNotEmpty()) {
                login(email,password)
            }else{
                Toast.makeText(applicationContext, "Please enter email and password! ", Toast.LENGTH_SHORT).show()
            }


        }
        if (mAuth.currentUser != null)
        {
            startActivity(Intent(this@Login, MainActivity::class.java))
            finish()
        }
    }

     private fun login (email: String, password: String){
         mAuth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     // code for login user
                    val intent = Intent(this@Login,MainActivity::class.java)
                     startActivity(intent)
                     finish()

                 } else {
                     Toast.makeText(this@Login,"User does not exist", Toast.LENGTH_SHORT).show()
                 }
             }

     }
}
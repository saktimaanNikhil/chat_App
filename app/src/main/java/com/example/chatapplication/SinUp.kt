package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SinUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSinUp: Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_up)

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSinUp = findViewById(R.id.btnSignup)

        supportActionBar?.hide()




        btnSinUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val name = edtName.text.toString()
            val password = edtPassword.text.toString()

            if(email.isNotEmpty()&& password.isNotEmpty()) {
                sinUp(name,email,password)
            }else{
                Toast.makeText(applicationContext, "Please enter email,name and password! ", Toast.LENGTH_SHORT).show()
            }

            sinUp(name,email,password)
        }

    }

    private fun sinUp(name: String, email: String, password: String) {
        // logic for creating user

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for jumping home
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SinUp,MainActivity::class.java)
                    startActivity(intent)
                    finish()


                } else {
                    Toast.makeText(this@SinUp,"Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}
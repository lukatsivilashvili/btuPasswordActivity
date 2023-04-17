package com.example.firebaseappbtu1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var loginEmailEditText: EditText
    private lateinit var loginPasswordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var notRegisteredButton: TextView
    private lateinit var forgotPasswordButton: TextView

    private val auth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        initListeners()
    }

    private fun init() {
        loginButton = findViewById(R.id.loginButton)
        loginEmailEditText = findViewById(R.id.loginEmailEditText)
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText)
        notRegisteredButton = findViewById(R.id.notRegisteredButton)
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton)
    }

    private fun initListeners() {

        loginButton.setOnClickListener {
            val email = loginEmailEditText.text.toString()
            val password = loginPasswordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "One of the fields empty", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Logged in!!!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Error logging you in! Check credentials",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        
        notRegisteredButton.setOnClickListener { 
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        
        forgotPasswordButton.setOnClickListener {
            val email = loginEmailEditText.text.toString()
            if (email.isNotEmpty()){
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Password reset link sent to email!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Error sending password reset link!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}
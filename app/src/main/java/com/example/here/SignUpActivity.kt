package com.example.here

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.here.databinding.ActivitySignUpBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.sinUpMakeButton.setOnClickListener {
            signUp()
        }
    }

    private fun signUp() {
        val email = binding.signUpEmailEditText.editText?.text.toString()
        val password = binding.signUpPasswordEditText.editText?.text.toString()

        Log.d("email", email)
        Log.d("password", password)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Success", "createUserWithEmail:success")
                } else {
                    Log.d("Fail", "Authentication failed")
                }
            }
    }
}
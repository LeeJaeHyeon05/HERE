package com.example.here

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.here.databinding.ActivityFindPasswordBinding
import com.google.firebase.auth.FirebaseAuth


class FindPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.findPasswordButton.setOnClickListener {
            val email = binding.passwordFindEditText.editText?.text.toString()
            findPassword(email)
        }

    }

    private fun findPassword(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "재설정 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "일치하는 메일이 없어 다시 확인해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
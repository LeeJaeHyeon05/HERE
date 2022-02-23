package com.example.here

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.here.databinding.ActivityFindEmailBinding
import com.example.here.databinding.ActivityFindPasswordBinding


class FindEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
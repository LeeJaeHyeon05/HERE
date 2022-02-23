package com.example.here

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.here.databinding.ActivityLoginBinding
import com.example.here.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //로그인 버튼
        binding.checkLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //경기도 버튼
        binding.geongGIDoTextButton.setOnClickListener {
            Log.d("msg", "강원도 성공")
        }
        //강원도 버튼
        binding.gangWonDoTextButton.setOnClickListener {
            Log.d("msg", "강원도 성공")
        }
        //충청도 버튼
        binding.chungcheongdoTextButton.setOnClickListener {
            Log.d("msg", "충청도 성공")
        }
        //전라북도 버튼
        binding.jonLaBuckDoTextButton.setOnClickListener {
            Log.d("msg", "전라북도 성공")
        }
        //전라남도 버튼
        binding.jonLaNamDoTextButton.setOnClickListener {
            Log.d("msg", "전라남도 성공")
        }
        //경상북도 버튼
        binding.geongSangBuckDoTextButton.setOnClickListener {
            Log.d("msg", "경상북도 성공")
        }
        //경상남도 버튼
        binding.geongSangNamDoTextButton.setOnClickListener {
            Log.d("msg", "경상남도 성공")
        }
        binding.jeJuDoTextButton.setOnClickListener {
            Log.d("msg", "제주도 성공")
        }

        //리사이클러뷰 불러와서 매니저 적용
        val festivalLayoutManager = LinearLayoutManager(this).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        binding.festivalRecyclerview.layoutManager = festivalLayoutManager

        val data = ArrayList<ItemModel>()
        binding.festivalRecyclerview.run {
            adapter = MainFestivalAdapter(data)
            layoutManager = festivalLayoutManager
        }

        data.add(
            ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        )
        )
        data.add(ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        ))
        data.add(ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        ))
        data.add(ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        ))
        data.add(ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        ))
        data.add(ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        ))

    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding = null
    }

//    override fun onStart() {
//        super.onStart()
//
//        if (auth.currentUser == null) {
//            startActivity(Intent(this, LoginActivity::class.java))
//        } else {
//            startActivity(Intent(this, MainActivity::class.java))
//        }
//    }
}
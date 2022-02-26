package com.example.here.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.here.ItemModel
import com.example.here.LoginActivity
import com.example.here.MainFestivalAdapter
import com.example.here.R
import com.example.here.databinding.ActivityMainBinding
import com.example.here.databinding.FragmentHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() : HomeFragment {
            return HomeFragment()
        }
    }
    private var mBinding: FragmentHomeBinding? = null
    private val binding get() = mBinding!!

    var auth : FirebaseAuth?= null
    var googleSignInClient : GoogleSignInClient?= null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()




        //로그인 버튼
        binding.checkLoginButton.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
        //로그아웃 버튼
        binding.logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            googleSignInClient?.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))

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
        val festivalLayoutManager = LinearLayoutManager(requireContext()).also { it.orientation = LinearLayoutManager.HORIZONTAL }
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
        data.add(
            ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        )
        )
        data.add(
            ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        )
        )
        data.add(
            ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        )
        )
        data.add(
            ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        )
        )
        data.add(
            ItemModel(
            R.drawable.facebook_icon,
            R.string.appNameText
        )
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}
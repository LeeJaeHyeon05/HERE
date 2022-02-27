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
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}
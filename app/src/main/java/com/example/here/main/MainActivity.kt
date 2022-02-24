package com.example.here

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.here.databinding.ActivityLoginBinding
import com.example.here.databinding.ActivityMainBinding
import com.example.here.main.FavoriteFragment
import com.example.here.main.HomeFragment
import com.example.here.main.LocationFragment
import com.example.here.main.PlanFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var locationFragment: LocationFragment
    private lateinit var planFragment: PlanFragment
    private lateinit var favoriteFragment: FavoriteFragment

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnNav = binding.bottomNav
        btnNav.setOnNavigationItemSelectedListener (onBottomNavItemSelectedListener)

        homeFragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragments_frame, homeFragment).commit()



    }
    private val onBottomNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {

        when (it.itemId) {
            R.id.menu_home -> {
                homeFragment = HomeFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, homeFragment).commit()
            }
            R.id.menu_location -> {
                locationFragment = LocationFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, locationFragment).commit()
            }
        }
        true
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
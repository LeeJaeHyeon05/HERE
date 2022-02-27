package com.example.here

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.here.databinding.ActivityMainBinding
import com.example.here.main.HomeFragment
import com.example.here.main.LocationFragment
import com.example.here.main.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var locationFragment: LocationFragment
    private lateinit var profileFragment: ProfileFragment

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
            R.id.menu_profile -> {
                profileFragment = ProfileFragment.newInstance()
                supportFragmentManager.beginTransaction().replace(R.id.fragments_frame, profileFragment).commit()
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
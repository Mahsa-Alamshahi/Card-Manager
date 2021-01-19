package com.bentoak.cardmanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.bentoak.cardmanager.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        val inflater = navHostFragment!!.navController.navInflater
        val graph = inflater.inflate(R.navigation.app_nav)
        navHostFragment.navController.graph = graph
    }


}
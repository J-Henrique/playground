package com.jhbb.navigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val host = supportFragmentManager
            .findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navigationController = host.navController
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.fragment1, R.id.loginFragment))

        findViewById<Toolbar>(R.id.toolbar)
            .setupWithNavController(navigationController, appBarConfiguration)
    }
}
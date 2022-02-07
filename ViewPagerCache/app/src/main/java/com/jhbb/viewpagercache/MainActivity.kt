package com.jhbb.viewpagercache

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jhbb.viewpagercache.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(ViewPagerFragment(), "MainActivity")
            .commit()
    }
}
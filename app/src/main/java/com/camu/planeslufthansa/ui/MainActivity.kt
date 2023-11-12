package com.camu.planeslufthansa.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.camu.planeslufthansa.R
import com.camu.planeslufthansa.databinding.ActivityMainBinding
import com.camu.planeslufthansa.ui.fragments.PlanesListFragment

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,PlanesListFragment())
                .commit()
        }
    }
}
package com.example.githubsearchmajneric.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.githubsearchmajneric.R
import com.example.githubsearchmajneric.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.navHostFragment.findNavController()
//            .addOnDestinationChangedListener { _, destination, _ ->
//
//
//            }
//        if (savedInstanceState == null){
//            val fragment = MainFragment()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.navHostFragment, fragment)
//                .commit()
//        }


    }






}
package com.technipixl.exo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.technipixl.exo1.databinding.ActivityMainBinding
import com.technipixl.network.timeStamp
import com.technipixl.network.trueKey

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
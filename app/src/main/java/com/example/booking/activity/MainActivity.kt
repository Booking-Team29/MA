package com.example.booking.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.accountButtonHome.setOnClickListener {
            val intent = Intent(this, AccountManagementActivity::class.java)
            startActivity(intent)
        }
        binding.searchButtonHome.setOnClickListener {
            val intent = Intent(this, MainSearch::class.java)
            startActivity(intent)
        }
        binding.registerButtonHome.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.loginButtonHome.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

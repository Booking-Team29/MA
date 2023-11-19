package com.example.booking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.booking.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Assuming you have a button in your MainActivity that starts the AccountActivity
        binding.startAccountActivityButton.setOnClickListener {
            val intent = Intent(this, AccountManagementActivity::class.java)
            startActivity(intent)
        }
    }
}

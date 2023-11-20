package com.example.booking

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.booking.databinding.ActivityAccountManagementBinding

class AccountManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonConfirmInfoChange.setOnClickListener {
            val fname = binding.editTextFirstName.text.toString()
            val password = binding.editTextPassword.text.toString()
            Toast.makeText(this, "First Name: $fname, Password: $password", Toast.LENGTH_SHORT).show()
            // business logic
        }
    }
}
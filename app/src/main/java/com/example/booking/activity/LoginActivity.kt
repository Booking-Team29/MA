package com.example.booking.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.booking.R
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.ActivityLoginBinding
import com.example.booking.databinding.ActivityMainBinding
import com.example.booking.model.Accommodation.AccommodationFilterDTO
import com.example.booking.model.Account.LoginDTO
import com.example.booking.model.Account.TokenDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            login()
        }
    }

    fun login() {
        var username = binding.usernameText.text.toString()
        var password = binding.passwordTextPassword.text.toString()
        var loginDTO = LoginDTO(username, password)

        val call =  ClientUtils.userService.login(loginDTO)
        call.enqueue(object : Callback<TokenDTO> {
            override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
                if (response.isSuccessful) {
                    val tokenDTO = response.body()
                    if (tokenDTO == null ) {
                        Toast.makeText(this@LoginActivity, "Error logging in", Toast.LENGTH_SHORT).show()
                    } else {
                        ClientUtils.setToken(tokenDTO.jwt)
                        val intent = Intent(this@LoginActivity, MainSearch::class.java)
                        startActivity(intent)
                    }
                } else {
                    if (response.code() == 500) {
                        Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(this@LoginActivity, "Error logging in: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
                System.out.println(t)
                Toast.makeText(this@LoginActivity, "Failed to log in", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
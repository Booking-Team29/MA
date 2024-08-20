package com.example.booking.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booking.adapter.OwnerReservationViewPagerAdapter
import com.example.booking.databinding.ActivityOwnerReservationBinding
import com.google.android.material.tabs.TabLayoutMediator

class OwnerReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOwnerReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOwnerReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val adapter = OwnerReservationViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Requests"
                1 -> "Reservations"
                else -> "Settings"
            }
        }.attach()


    }
}
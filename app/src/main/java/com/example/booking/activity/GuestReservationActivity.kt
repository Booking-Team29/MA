package com.example.booking.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.booking.adapter.ReservationViewPagerAdapter
import com.example.booking.databinding.ActivityGuestReservationBinding
import com.google.android.material.tabs.TabLayoutMediator

class GuestReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestReservationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager
        val adapter = ReservationViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Requests"
                1 -> "Reservations"
                else -> "Favorite"
            }
        }.attach()


    }
}
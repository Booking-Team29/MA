package com.example.booking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.booking.fragment.GuestReservationScreen.OwnerReservationFragment
import com.example.booking.fragment.OwnerReservationScreen.OwnerRequestFragment
import com.example.booking.fragment.OwnerReservationScreen.OwnerSettingsFragment

class OwnerReservationViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OwnerRequestFragment()
            1 -> OwnerReservationFragment()
            else -> OwnerSettingsFragment()
        }
    }
}
package com.example.booking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.booking.fragment.GuestReservationScreen.GuestFavoriteFragment
import com.example.booking.fragment.GuestReservationScreen.GuestRequestFragment
import com.example.booking.fragment.GuestReservationScreen.GuestReservationFragment

class ReservationViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GuestRequestFragment()
            1 -> GuestReservationFragment()
            else -> GuestFavoriteFragment()
        }
    }
}
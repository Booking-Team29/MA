package com.example.booking.fragment.GuestReservationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.booking.R

class GuestFavoriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_favorite, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}
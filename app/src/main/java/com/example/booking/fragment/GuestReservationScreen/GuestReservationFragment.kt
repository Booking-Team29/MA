package com.example.booking.fragment.GuestReservationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.booking.R

class GuestReservationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_reservation, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    }
}
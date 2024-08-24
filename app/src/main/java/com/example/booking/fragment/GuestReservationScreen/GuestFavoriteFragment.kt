package com.example.booking.fragment.GuestReservationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.booking.R
import com.example.booking.adapter.GuestFavoriteAccommodationAdapter
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.FragmentGuestFavoriteBinding
import com.example.booking.databinding.FragmentOwnerSettingsBinding
import com.example.booking.model.Accommodation.Accommodation
import com.example.booking.model.Accommodation.AccommodationFilterDTO
import com.example.booking.model.Accommodation.GetAccommodationDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestFavoriteFragment : Fragment() {
    private lateinit var binding: FragmentGuestFavoriteBinding
    private var accommodations: List<Accommodation> = emptyList()
    private lateinit var adapter: GuestFavoriteAccommodationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuestFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var listView = binding.userFavoriteAccommodationstList
        adapter = GuestFavoriteAccommodationAdapter(requireContext(), emptyList())
        listView.adapter = adapter

        fetchAccommodations()
    }

    override fun onPause() {
        super.onPause()
        adapter.updateItems(emptyList())
    }

    override fun onStop() {
        super.onStop()
        adapter.updateItems(emptyList())
    }

    override fun onResume() {
        super.onResume()
        fetchAccommodations()
    }

    private fun fetchAccommodations() {
        val call =  ClientUtils.accommodationService.getFavoriteAccommodations()
        call.enqueue(object : Callback<List<Accommodation>> {
            override fun onResponse(call: Call<List<Accommodation>>, response: Response<List<Accommodation>>) {
                if (response.isSuccessful) {
                    val accs = response.body() ?: emptyList()
                    accommodations = accs
                    adapter.updateItems(accs)
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Accommodation>>, t: Throwable) {
                System.out.println(t)
                Toast.makeText(requireContext(), "Failed to fetch favorite accommodations", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
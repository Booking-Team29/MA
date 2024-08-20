package com.example.booking.fragment.GuestReservationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.booking.adapter.OwnerReservationAdapter
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.FragmentGuestRequestBinding
import com.example.booking.model.Reservation.Reservation
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class OwnerReservationFragment : Fragment() {
    private lateinit var binding: FragmentGuestRequestBinding
    private var requests: List<Reservation>? = null
    private lateinit var adapter: OwnerReservationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGuestRequestBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OwnerReservationAdapter(requireContext(), emptyList())
        binding.resViewViewList.adapter = adapter

        val spinnerItmes = listOf("", "REQUESTED", "APPROVED", "DENIED", "ACTIVE", "COMPLETED", "DELETED", "CANCELLED")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItmes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.resViewStatusSpinner.adapter = spinnerAdapter

        binding.resViewClearButton.setOnClickListener{ adapter.updateItems(requests!!) }
        binding.resViewFilterButton.setOnClickListener{ filter() }

        fetchReservations()
    }

    private fun filter() {
        var res = ArrayList<Reservation>()
        var checkIn: LocalDate? = null;
        var checkOut: LocalDate? = null;
        try {
            checkIn = LocalDate.parse(binding.resViewCheckin.text)
            checkOut = LocalDate.parse(binding.resViewCheckout.text)
        } catch (e: Exception) {}
        var status = binding.resViewStatusSpinner.selectedItem.toString()
        var name = binding.resViewAccommodation.text.toString()
        for (req in requests!!) {
            if (checkIn != null && checkIn.isAfter(LocalDate.parse(req.startDate))) continue
            if (checkOut != null && checkOut.isBefore(LocalDate.parse(req.endDate))) continue
            if (status.isNotEmpty() && req.reservationStatus.toString() != status) continue
            if (name.isNotEmpty() && !adapter.accommodations[req.accommodationId]!!.Name.contains(name)) continue
            res.add(req)
        }
        adapter.updateItems(res)
    }

    private fun fetchReservations() {
        val call =  ClientUtils.reservationService.getReservations()
        call.enqueue(object : Callback<List<Reservation>> {
            override fun onResponse(call: Call<List<Reservation>>, response: Response<List<Reservation>>) {
                if (response.isSuccessful) {
                    val ress = response.body()
                    if (ress != null){
                        requests = ress
                        adapter.updateItems(requests!!)
                    } else {
                        Toast.makeText(requireContext(), "Got no reservations", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                System.out.println("Error getting reservation requests: " + t)
                Toast.makeText(requireContext(), "Failed to fetch reservation requests", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

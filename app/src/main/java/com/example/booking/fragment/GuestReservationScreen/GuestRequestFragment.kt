package com.example.booking.fragment.GuestReservationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.models.SlideModel
import com.example.booking.R
import com.example.booking.adapter.ReservationRequestAdapter
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.FragmentGuestRequestBinding
import com.example.booking.model.Accommodation.GetAccommodationDTO
import com.example.booking.model.Reservation.ReservationRequest
import kotlinx.coroutines.awaitAll
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class GuestRequestFragment : Fragment() {
    private lateinit var binding: FragmentGuestRequestBinding
    private var requests: List<ReservationRequest>? = null
    private lateinit var adapter: ReservationRequestAdapter

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

        adapter = ReservationRequestAdapter(requireContext(), emptyList())
        binding.resViewViewList.adapter = adapter

        val spinnerItmes = listOf("", "REQUESTED", "APPROVED", "DENIED", "ACTIVE", "COMPLETED", "DELETED", "CANCELLED")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItmes)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.resViewStatusSpinner.adapter = spinnerAdapter

        binding.resViewClearButton.setOnClickListener{ adapter.updateItems(requests!!) }
        binding.resViewFilterButton.setOnClickListener{ filter() }

        fetchRequests()
    }

    private fun filter() {
        var res = ArrayList<ReservationRequest>()
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

    private fun fetchRequests() {
        val call =  ClientUtils.reservationService.getReservationRequests()
        call.enqueue(object : Callback<List<ReservationRequest>> {
            override fun onResponse(call: Call<List<ReservationRequest>>, response: Response<List<ReservationRequest>>) {
                if (response.isSuccessful) {
                    val ress = response.body()
                    if (ress != null){
                        requests = ress
                        adapter.updateItems(requests!!)
                    } else {
                        Toast.makeText(requireContext(), "Got no reservation requests", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ReservationRequest>>, t: Throwable) {
                System.out.println("Error getting reservation requests: " + t)
                Toast.makeText(requireContext(), "Failed to fetch reservation requests", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
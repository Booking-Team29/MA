package com.example.booking.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.models.SlideModel
import com.example.booking.client.ClientUtils
import com.example.booking.client.ReservationService
import com.example.booking.databinding.ActivityMakeReservationBinding
import com.example.booking.model.Accommodation.GetAccommodationDTO
import com.example.booking.model.Reservation.ReservationRequestDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class MakeReservationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakeReservationBinding
    private var accommodation: GetAccommodationDTO? = null
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakeReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var accommodationId = intent.getLongExtra("ACCOMMODATION_ID", -1)
        getAccommodation(accommodationId.toString())

        binding.resSubmitButton.setOnClickListener {
            submit()
        }

        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, emptyList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.slotSpinner.adapter = adapter
    }
    private fun getAccommodation(accommodationId: String) {
        val call =  ClientUtils.accommodationService.getAccommodation(accommodationId.toString())
        call.enqueue(object : Callback<GetAccommodationDTO> {
            override fun onResponse(call: Call<GetAccommodationDTO>, response: Response<GetAccommodationDTO>) {
                if (response.isSuccessful) {
                    val acc = response.body()
                    accommodation = acc
                    if (acc != null){
                        var temp = ArrayList<String>()
                        for (slot in acc.slots) {
                            if (slot.available) temp.add("("+slot.ID + ") - " + slot.startDate + " - " + slot.endDate)
                        }

                        adapter = ArrayAdapter(this@MakeReservationActivity, android.R.layout.simple_spinner_item, temp)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.slotSpinner.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@MakeReservationActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAccommodationDTO>, t: Throwable) {
                System.out.println("Error getting accommodation: " + t)
                Toast.makeText(this@MakeReservationActivity, "Failed to fetch accommodation", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun submit() {
        var slot = binding.slotSpinner.selectedItem as String
        var startDate = LocalDate.parse(binding.resCheckin.text)
        var endDate = LocalDate.parse(binding.resCheckOut.text)
        var numberOfGuests = binding.resGuests.text.toString().toIntOrNull()

        if (numberOfGuests == null) {
            Toast.makeText(this@MakeReservationActivity, "Invalid number of guests", Toast.LENGTH_SHORT).show()
            return
        }
        if (startDate == null) {
            Toast.makeText(this@MakeReservationActivity, "Invalid check in date", Toast.LENGTH_SHORT).show()
            return
        }
        if (endDate == null) {
            Toast.makeText(this@MakeReservationActivity, "Invalid check out date", Toast.LENGTH_SHORT).show()
            return
        }

        var slotParts = slot.split(" - ")
        var slotStart = LocalDate.parse(slotParts[1])
        var slotEnd = LocalDate.parse(slotParts[2])

        if (numberOfGuests < accommodation!!.MinGuests || numberOfGuests > accommodation!!.MaxGuests) {
            Toast.makeText(this@MakeReservationActivity, "Accommodation cannot accommodate this number of guests", Toast.LENGTH_SHORT).show()
            return
        }

        if (startDate.isAfter(endDate)) {
            Toast.makeText(this@MakeReservationActivity, "Check in date has to be before check out date", Toast.LENGTH_SHORT).show()
            return
        }

        if (!(startDate>= slotStart && endDate <= slotEnd)) {
            Toast.makeText(this@MakeReservationActivity, "Reservation period has to fit in the slot", Toast.LENGTH_SHORT).show()
            return
        }

        if (startDate.isBefore(LocalDate.now())) {
            Toast.makeText(this@MakeReservationActivity, "Check in date cannot be in the past", Toast.LENGTH_SHORT).show()
            return
        }

        var dayDiff = ChronoUnit.DAYS.between(startDate, endDate)
        var price = accommodation!!.prices[0].Amount * dayDiff
        var slotId = slotParts[0].removePrefix("(").removeSuffix(")")

        var dto = ReservationRequestDTO(-1, startDate.toString(), endDate.toString(), numberOfGuests, price.toInt(), ClientUtils.userEmail, accommodation!!.ID, slotId.toLong())
        val call =  ClientUtils.reservationService.makeReservation(dto)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MakeReservationActivity, "Successfully made a reservation request!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@MakeReservationActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                System.out.println("Error creating reservation request: " + t)
                Toast.makeText(this@MakeReservationActivity, "Failed to create reservation request", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
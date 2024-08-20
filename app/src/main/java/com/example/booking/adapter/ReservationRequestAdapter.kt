package com.example.booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.transition.Visibility
import com.denzcoskun.imageslider.models.SlideModel
import com.example.booking.R
import com.example.booking.client.ClientUtils
import com.example.booking.model.Accommodation.GetAccommodationDTO
import com.example.booking.model.Reservation.ReservationRequest
import com.example.booking.model.Reservation.ReservationStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservationRequestAdapter(
    private val context: Context,
    private var items: List<ReservationRequest>,
    public var accommodations: HashMap<Long, GetAccommodationDTO> = HashMap<Long, GetAccommodationDTO>()
    ): BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_reservation, parent, false)
        val deleteButton: Button = view.findViewById(R.id.resViewDeleteButton)
        val checkOut: TextView = view.findViewById(R.id.resViewCheckOut)
        val checkIn: TextView = view.findViewById(R.id.resViewCheckIn)
        val accName: TextView = view.findViewById(R.id.resViewAcc)
        val status: TextView = view.findViewById(R.id.resViewStatus)

        deleteButton.visibility = View.VISIBLE
        val item = items[position]
        if (item.reservationStatus.toString() != "REQUESTED") deleteButton.visibility = View.INVISIBLE
        else deleteButton.setOnClickListener{ deleteReservation(item.id, deleteButton, status) }

        checkIn.text = "Check in: " + item.startDate
        checkOut.text = "Check out: " + item.endDate
        accName.text = "Accommodation: " + item.accommodationId.toString()
        status.text = "Status: " + item.reservationStatus.toString()
        fetchAccommodation(item.accommodationId, accName)
        return view
    }

    private fun fetchAccommodation(accommodationId: Long, accName: TextView) {
        val call =  ClientUtils.accommodationService.getAccommodation(accommodationId.toString())
        call.enqueue(object : Callback<GetAccommodationDTO> {
            override fun onResponse(call: Call<GetAccommodationDTO>, response: Response<GetAccommodationDTO>) {
                if (response.isSuccessful) {
                    val acc = response.body()
                    if (acc != null){
                        accName.text = "Accommodation: " + acc.Name
                        accommodations[acc.ID] = acc
                    }
                } else {
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAccommodationDTO>, t: Throwable) {
                System.out.println("Error getting accommodation name: " + t)
                Toast.makeText(context, "Failed to fetch accommodation name", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun updateItems(newItems: List<ReservationRequest>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun deleteReservation(reservationId: Long, button: Button, status: TextView) {
        val call =  ClientUtils.reservationService.deleteReservationRequest(reservationId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    for (req in items) if (req.id == reservationId) req.reservationStatus = ReservationStatus.DELETED
                    updateItems(items)
                    Toast.makeText(context, "Reservation request has been deleted", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(context, "Error deleting reservation", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                System.out.println("Error deleting reservation request: " + t)
                Toast.makeText(context, "Failed to delete reservation request", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
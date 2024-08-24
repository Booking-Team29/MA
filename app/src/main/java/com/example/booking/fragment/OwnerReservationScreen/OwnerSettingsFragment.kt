package com.example.booking.fragment.OwnerReservationScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.booking.R
import com.example.booking.adapter.OwnerReservationRequestAdapter
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.FragmentGuestRequestBinding
import com.example.booking.databinding.FragmentOwnerSettingsBinding
import com.example.booking.model.Accommodation.AccommodationChangeConfirmationMethodDTO
import com.example.booking.model.Accommodation.ConfirmationMethod
import com.example.booking.model.Accommodation.GetAccommodationDTO
import com.example.booking.model.Reservation.ReservationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerSettingsFragment : Fragment() {
    private lateinit var binding: FragmentOwnerSettingsBinding
    private var accommodations: List<GetAccommodationDTO>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnerSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ArrayList<String>())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.accommodationPickerSpinner.adapter = adapter
        binding.accommodationPickerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (selectedItem.isEmpty()) {
                    binding.currentMethodTextView.text = "Current confirmation method: "
                    return
                }
                var parts = selectedItem.split(" - ")
                var id = parts[0].removePrefix("(")
                id = id.removeSuffix(")")
                var idLong = id.toLong()
                for (acc in accommodations!!) {
                    if (acc.ID == idLong) binding.currentMethodTextView.text = "Current confirmation method: " + acc.confirmationMethod.toString();
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        var methodSpinnerItems = listOf("AUTOMATIC", "MANUAL")
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, methodSpinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.methodSpinner.adapter = adapter

        binding.updateMethodButton.setOnClickListener { updateMethod() }

        fetchAccommodations()
    }

    private fun fetchAccommodations() {
        val call =  ClientUtils.accommodationService.getOwnerAccommodation()
        call.enqueue(object : Callback<List<GetAccommodationDTO>> {
            override fun onResponse(call: Call<List<GetAccommodationDTO>>, response: Response<List<GetAccommodationDTO>>) {
                if (response.isSuccessful) {
                    val accs = response.body()
                    if (accs != null){
                        accommodations = accs

                        var spinnerItems = ArrayList<String>()
                        spinnerItems.add("")
                        for (acc in accs) spinnerItems.add("("+acc.ID+") - "+acc.Name)
                        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.accommodationPickerSpinner.adapter = adapter
                    } else {
                        Toast.makeText(requireContext(), "Got no accommodations", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<GetAccommodationDTO>>, t: Throwable) {
                System.out.println("Error getting owner accommodatiosn: " + t)
                Toast.makeText(requireContext(), "Failed to fetch accommodations", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateMethod() {
        var accommodation = binding.accommodationPickerSpinner.selectedItem.toString()
        if (accommodation.isEmpty()) return;

        var parts = accommodation.split(" - ")
        var id = parts[0].removeSuffix(")").removePrefix("(")
        var method = ConfirmationMethod.valueOf(binding.methodSpinner.selectedItem.toString())
        var dto = AccommodationChangeConfirmationMethodDTO(method)

        val call =  ClientUtils.accommodationService.updateAccommodationConfirmationMethod(id, dto)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Confirmation method successfully changed", Toast.LENGTH_SHORT).show()
                    for (acc in accommodations!!) if (acc.ID.toString() == id) acc.confirmationMethod = method
                    binding.currentMethodTextView.text = "Current confirmation method: " + method.toString()
                } else Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                System.out.println("Error setting confirmation method: " + t)
                Toast.makeText(requireContext(), "Failed to set confirmation method", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
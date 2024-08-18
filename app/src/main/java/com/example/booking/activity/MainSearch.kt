package com.example.booking.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.example.booking.adapter.AccommodationSearchResultAdapter
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.ActivityMainSearchBinding
import com.example.booking.fragment.SearchFilterFragment
import com.example.booking.model.Accommodation.AccommodationFilterDTO
import com.example.booking.model.Accommodation.AccommodationType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MainSearch : AppCompatActivity(), SearchFilterFragment.OnFilterAppliedListener {
    private lateinit var listView: ListView
    private lateinit var adapter: AccommodationSearchResultAdapter
    private lateinit var binding: ActivityMainSearchBinding
    private var items: List<AccommodationFilterDTO> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = binding.resutlList
        adapter = AccommodationSearchResultAdapter(this, emptyList())

        binding.goButton.setOnClickListener { fetchItems() }
        binding.filterButton.setOnClickListener {
            val dialog = SearchFilterFragment()
            dialog.setOnFilterAppliedListener(this)
            dialog.show(supportFragmentManager, "FilterDialogFragment")
        }
        listView.adapter = adapter
    }

    private fun fetchItems() {
        if (binding.placeInput.text.toString().isEmpty()) return
        var location = binding.placeInput.text.toString()

        if (binding.numberOfPeopleInput.text.toString().isEmpty()) return
        var numberOfPeople: Int = binding.numberOfPeopleInput.text.toString().toIntOrNull() ?: return
        var checkInDate: LocalDate
        var checkOutDate: LocalDate
        try{
            if (binding.checkInDate.text.toString().isEmpty()) return
            checkInDate = LocalDate.parse(binding.checkInDate.text.toString()) ?: return

            if (binding.checkOutDate.text.toString().isEmpty()) return
            checkOutDate = LocalDate.parse(binding.checkOutDate.text.toString()) ?: return
        } catch (e: Exception) {
            return
        }
        val call =  ClientUtils.accommodationService.searchAcommodations(location, numberOfPeople, checkInDate, checkOutDate)
        call.enqueue(object : Callback<List<AccommodationFilterDTO>> {
            override fun onResponse(call: Call<List<AccommodationFilterDTO>>, response: Response<List<AccommodationFilterDTO>>) {
                if (response.isSuccessful) {
                    val users = response.body() ?: emptyList()
                    items = users
                    adapter.updateItems(users)
                } else {
                    Toast.makeText(this@MainSearch, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<AccommodationFilterDTO>>, t: Throwable) {
                System.out.println(t)
                Toast.makeText(this@MainSearch, "Failed to fetch items", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onFilterApplied(
        hotel: Boolean,
        apartement: Boolean,
        studio: Boolean,

        wifi: Boolean,
        parking: Boolean,
        balcony: Boolean,

        priceFrom: Double?,
        priceTo: Double?
    ) {
        var filteredItems = ArrayList<AccommodationFilterDTO>()
        for (item in items) {
            if (item.prices.isEmpty()) continue
            val price = item.prices[0]
            if (priceFrom != null && priceTo != null && (price.Amount < priceFrom || price.Amount > priceTo)) continue

            if (wifi && item.Amenities?.contains("Wi-Fi") == false) continue
            if (parking && item.Amenities?.contains("Parking") == false ) continue
            if (balcony && item.Amenities?.contains("Balcony") == false) continue

            if (hotel && item.Type.compareTo(AccommodationType.HOTEL) == 0) filteredItems.add(item)
            else if (apartement && item.Type.compareTo(AccommodationType.APARTMENT) == 0) filteredItems.add(item)
            else if (studio && item.Type.compareTo(AccommodationType.STUDIO) == 0) filteredItems.add(item)
        }
        adapter.updateItems(filteredItems)
    }
}
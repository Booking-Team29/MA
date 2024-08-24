package com.example.booking.activity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.collections.ArrayList
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
import kotlin.math.sqrt

class MainSearch : AppCompatActivity(), SearchFilterFragment.OnFilterAppliedListener, SensorEventListener {
    private lateinit var listView: ListView
    private lateinit var adapter: AccommodationSearchResultAdapter
    private lateinit var binding: ActivityMainSearchBinding
    private var items: List<AccommodationFilterDTO> = emptyList()
    private var filteredItems: List<AccommodationFilterDTO> = emptyList()
    private var isSorted: Boolean = false

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastShakeTime: Long = 0
    private val SHAKE_THRESHOLD = 20

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

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            val acceleration = sqrt((x * x + y * y + z * z).toDouble())
            val currentTime = System.currentTimeMillis()

            if (currentTime - lastShakeTime > 1000) {
                if (acceleration > SHAKE_THRESHOLD) {
                    lastShakeTime = currentTime
                    onShakeDetected()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }

    private fun onShakeDetected() {
        if (isSorted) {
            if (filteredItems.size > 0) {
                filteredItems = filteredItems.reversed()
                adapter.updateItems(filteredItems)
            } else {
                items = items.reversed()
                adapter.updateItems(items)
            }
        } else {
            if (filteredItems.size > 0) {
                filteredItems = filteredItems.sortedBy { it.Name }
                adapter.updateItems(filteredItems)
                isSorted = true
            } else {
                items = items.sortedBy { it.Name }
                adapter.updateItems(items)
                isSorted = true
            }
        }
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
                    items = ArrayList(users)
                    adapter.updateItems(items)
                    filteredItems = ArrayList()
                    isSorted = false
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
        filteredItems = ArrayList<AccommodationFilterDTO>()
        for (item in items) {
            if (item.prices.isEmpty()) continue
            val price = item.prices[0]
            if (priceFrom != null && priceTo != null && (price.Amount < priceFrom || price.Amount > priceTo)) continue

            if (wifi && item.Amenities?.contains("Wi-Fi") == false) continue
            if (parking && item.Amenities?.contains("Parking") == false ) continue
            if (balcony && item.Amenities?.contains("Balcony") == false) continue

            if (hotel && item.Type.compareTo(AccommodationType.HOTEL) == 0) (filteredItems as ArrayList<AccommodationFilterDTO>).add(item)
            else if (apartement && item.Type.compareTo(AccommodationType.APARTMENT) == 0) (filteredItems as ArrayList<AccommodationFilterDTO>).add(item)
            else if (studio && item.Type.compareTo(AccommodationType.STUDIO) == 0) (filteredItems as ArrayList<AccommodationFilterDTO>).add(item)
        }
        adapter.updateItems(filteredItems)
        isSorted = false
    }
}
package com.example.booking.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.booking.R
import com.example.booking.adapter.AccommodationReviewAdapter
import com.example.booking.adapter.AccommodationSearchResultAdapter
import com.example.booking.client.ClientUtils
import com.example.booking.databinding.ActivityAccommodationBinding
import com.example.booking.model.Accommodation.AccommodationFilterDTO
import com.example.booking.model.Accommodation.GetAccommodationDTO
import com.example.booking.model.Accommodation.IsAccommodaitonFavoriteDTO
import com.example.booking.model.Review.RatingDTO
import com.example.booking.model.Review.ReviewDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccommodationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccommodationBinding
    private var accommodation: GetAccommodationDTO? = null
    private lateinit var adapter: AccommodationReviewAdapter
    private var accommodationId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccommodationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteButton.visibility = View.INVISIBLE
        if (ClientUtils.role != "GUEST") {
            binding.bookButton.visibility = View.INVISIBLE
        }

        adapter = AccommodationReviewAdapter(this, emptyList())
        binding.reviewList.layoutManager = LinearLayoutManager(this)
        binding.reviewList.adapter = adapter

        accommodationId = intent.getLongExtra("ACCOMMODATION_ID", 0)
        checkIfUserFavorite()
        getAccommodation()
        getRating()
        getReviews()

        binding.bookButton.setOnClickListener {
            if (accommodation != null) {
                val intent = Intent(this@AccommodationActivity, MakeReservationActivity::class.java)
                intent.putExtra("ACCOMMODATION_ID", accommodation!!.ID)
                ContextCompat.startActivity(this@AccommodationActivity, intent, null)
            }
        }
        binding.favoriteButton.setOnClickListener { favoriteHandler() }
    }

    private fun favoriteHandler() {
        if (binding.favoriteButton.text == "Favorite") favoriteAccommodation()
        else unfavoriteAccommodation()
    }

    private fun favoriteAccommodation() {
        val call =  ClientUtils.accommodationService.putAccommodationInFavorites(accommodationId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    binding.favoriteButton.text = "Unfavorite"
                    Toast.makeText(this@AccommodationActivity, "Accommodation marked as favorite", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                System.out.println("Error marking accommodation as favorite: " + t)
                Toast.makeText(this@AccommodationActivity, "Failed to mark accommodation as favorite", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun unfavoriteAccommodation() {
        val call =  ClientUtils.accommodationService.deleteAccommodationFavorite(accommodationId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    binding.favoriteButton.text = "Favorite"
                    Toast.makeText(this@AccommodationActivity, "Accommodation removed from favorites", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                System.out.println("Error removing accommodation from favorites: " + t)
                Toast.makeText(this@AccommodationActivity, "Failed to remove accommodation from favorites", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkIfUserFavorite() {
        val call =  ClientUtils.accommodationService.isFavoriteAccommodation(accommodationId)
        call.enqueue(object : Callback<IsAccommodaitonFavoriteDTO> {
            override fun onResponse(call: Call<IsAccommodaitonFavoriteDTO>, response: Response<IsAccommodaitonFavoriteDTO>) {
                if (response.isSuccessful) {
                    val favoriteDTO = response.body()
                    if (favoriteDTO != null ) {
                        binding.favoriteButton.visibility = View.VISIBLE
                        if (favoriteDTO.isFavorite) binding.favoriteButton.text = "Unfavorite"
                        else binding.favoriteButton.text = "Favorite"
                    }
                }
            }
            override fun onFailure(call: Call<IsAccommodaitonFavoriteDTO>, t: Throwable) {
                System.out.println("Error checking if accommodation is favorite: " + t)
                Toast.makeText(this@AccommodationActivity, "Failed to check if accommodation is favorite", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAccommodation() {
        val call =  ClientUtils.accommodationService.getAccommodation(accommodationId.toString())
        call.enqueue(object : Callback<GetAccommodationDTO> {
            override fun onResponse(call: Call<GetAccommodationDTO>, response: Response<GetAccommodationDTO>) {
                if (response.isSuccessful) {
                    val acc = response.body()
                    accommodation = acc

                    // populate acc information
                    if (acc != null){
                        binding.descriptionAccText.text = acc.Description
                        binding.placeAccText.text = acc.Location
                        binding.nameAccText.text = acc.Name
                        var temp = "Amenities: "
                        for (amen in acc.Amenities!!) temp += "$amen "
                        binding.amenitiesAccText.text = temp

                        val imageList = ArrayList<SlideModel>()
                        for (image in acc.Images ?: emptyList()) imageList.add(SlideModel(image))
                        binding.imageCarousel.setImageList(imageList)
                    }

                } else {
                    Toast.makeText(this@AccommodationActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAccommodationDTO>, t: Throwable) {
                System.out.println("Error getting accommodation: " + t)
                Toast.makeText(this@AccommodationActivity, "Failed to fetch accommodation", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getReviews() {
        val call =  ClientUtils.reviewService.getAccommodationReview(accommodationId.toString())
        call.enqueue(object : Callback<List<ReviewDTO>> {
            override fun onResponse(call: Call<List<ReviewDTO>>, response: Response<List<ReviewDTO>>) {
                if (response.isSuccessful) {
                    val reviews = response.body()
                    if (reviews != null) adapter.updateItems(reviews)
                } else {
                    Toast.makeText(this@AccommodationActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ReviewDTO>>, t: Throwable) {
                System.out.println("Error getting reviews: " + t)
                Toast.makeText(this@AccommodationActivity, "Failed to fetch reviews", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getRating() {
        val call =  ClientUtils.reviewService.getAccommodationRating(accommodationId.toString())
        call.enqueue(object : Callback<RatingDTO> {
            override fun onResponse(call: Call<RatingDTO>, response: Response<RatingDTO>) {
                if (response.isSuccessful) {
                    val rating = response.body()
                    if (rating != null){
                        binding.ratingAccText.text = String.format("%.2f", rating.Rating) + "/10"
                    }

                } else {
                    Toast.makeText(this@AccommodationActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RatingDTO>, t: Throwable) {
                System.out.println(t)
                Toast.makeText(this@AccommodationActivity, "Failed to fetch rating", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
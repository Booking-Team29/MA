package com.example.booking.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.booking.R
import com.example.booking.activity.AccommodationActivity
import com.example.booking.model.Accommodation.AccommodationFilterDTO

class AccommodationSearchResultAdapter(private val context: Context, private var items: List<AccommodationFilterDTO>): BaseAdapter() {
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
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_search_result, parent, false)
        val titleTextView: TextView = view.findViewById(R.id.nameText)
        val ratingTextView: TextView = view.findViewById(R.id.ratingText)
        val placeTextView: TextView = view.findViewById(R.id.placeText)
        val descriptionTextView: TextView = view.findViewById(R.id.descriptionText)
        val imageImageView: ImageView = view.findViewById(R.id.imageView)
        val priceText: TextView = view.findViewById(R.id.priceText)

        val item = items[position]
        titleTextView.text = item.Name
        placeTextView.text = item.Location
        descriptionTextView.text = item.Description
        ratingTextView.text = String.format("%.2f", item.Rating) + "/10"
        var price = 0.0
        if (item.prices.isNotEmpty()) price = item.prices[0].Amount
        if (price > 0) priceText.text = "Price per night: $" + String.format("%.2f", price)
        else priceText.text = ""

        // Load image using Glide
        Glide.with(context)
            .load(item.Images?.get(0) ?: "")
            .placeholder(R.drawable.dummy)
            .error(R.drawable.dummy)
            .into(imageImageView)

        val showButton: Button = view.findViewById(R.id.showButton)
        showButton.setOnClickListener {
            val intent = Intent(context, AccommodationActivity::class.java)
            intent.putExtra("ACCOMMODATION_ID", item.ID)
            startActivity(context, intent, null)
        }
        return view
    }

    fun updateItems(newItems: List<AccommodationFilterDTO>) {
        items = newItems
        notifyDataSetChanged()
    }
}
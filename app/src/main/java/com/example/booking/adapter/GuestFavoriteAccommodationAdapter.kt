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
import com.example.booking.model.Accommodation.Accommodation

class GuestFavoriteAccommodationAdapter(private val context: Context, private var items: List<Accommodation>): BaseAdapter() {
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
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_user_favorite_accommodation, parent, false)
        val image: ImageView = view.findViewById(R.id.favoriteAccommodationImage)
        var name: TextView = view.findViewById(R.id.accFavoriteName)
        var location: TextView = view.findViewById(R.id.accFavoriteLocation)
        var openButton: Button = view.findViewById(R.id.accFavoriteOpenButton)

        val item = items[position]
        name.text = item.Name
        location.text = item.Location

        Glide.with(context)
            .load(item.Images?.get(0) ?: "")
            .placeholder(R.drawable.dummy)
            .error(R.drawable.dummy)
            .into(image)

        openButton.setOnClickListener {
            val intent = Intent(context, AccommodationActivity::class.java)
            intent.putExtra("ACCOMMODATION_ID", item.ID)
            startActivity(context, intent, null)
        }
        return view
    }

    fun updateItems(newItems: List<Accommodation>) {
        items = newItems
        notifyDataSetChanged()
    }
}

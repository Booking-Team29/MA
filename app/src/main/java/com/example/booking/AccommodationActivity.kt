package com.example.booking

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.activity.ComponentActivity
import com.example.booking.databinding.ActivityAccommodationBinding

class AccommodationActivity : ComponentActivity() {
    private lateinit var binding: ActivityAccommodationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccommodationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.reviewList.adapter = MyAdapter(this)
    }
    private class MyAdapter(context: Context): BaseAdapter() {
        private val ctx: Context
        init {
            ctx = context
        }

        override fun getCount(): Int {
            return 5
        }

        override fun getItem(position: Int): Any {
            return "test"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(ctx)
            val rowMain = layoutInflater.inflate(com.example.booking.R.layout.fragment_review, parent, false)
            return rowMain
        }


    }
}
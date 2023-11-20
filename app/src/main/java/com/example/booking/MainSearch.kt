package com.example.booking

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.booking.databinding.ActivityMainSearchBinding
import com.example.booking.databinding.FragmentSearchResultBinding

class MainSearch : ComponentActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<SearchResult>
    private lateinit var binding: ActivityMainSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.resutlList.adapter = MyAdapter(this)
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
            val rowMain = layoutInflater.inflate(com.example.booking.R.layout.fragment_search_result, parent, false)
            val button: Button = rowMain.findViewById(com.example.booking.R.id.showButton)
            button.setOnClickListener {
                var intent = Intent(ctx, AccommodationActivity::class.java)
                startActivity(ctx, intent, null)
            }
            return rowMain
        }
    }
}
package com.example.booking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booking.R
import com.example.booking.model.Review.ReviewDTO

//class AccommodationReviewAdapter(private val context: Context, private var items: List<ReviewDTO>): BaseAdapter() {
//    override fun getCount(): Int {
//        return items.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return items[position]
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.fragment_review, parent, false)
//        var comment: TextView = view.findViewById(R.id.reviewComment)
//        var rating: TextView = view.findViewById(R.id.reviewRating)
//        var username: TextView = view.findViewById(R.id.reviewUsername)
//
//        val item = items[position]
//        comment.text = item.description
//        rating.text = String.format("%.0f/10", item.rating)
//        username.text = item.reviewerEmail
//
//        return view
//    }
//
//    fun updateItems(newItems: List<ReviewDTO>) {
//        items = newItems
//        notifyDataSetChanged()
//    }
//}

class AccommodationReviewAdapter(
    private val context: Context,
    private var items: List<ReviewDTO>
) : RecyclerView.Adapter<AccommodationReviewAdapter.ReviewViewHolder>() {

    // ViewHolder class to hold view references
    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val comment: TextView = view.findViewById(R.id.reviewComment)
        val rating: TextView = view.findViewById(R.id.reviewRating)
        val username: TextView = view.findViewById(R.id.reviewUsername)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_review, parent, false)
        return ReviewViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val item = items[position]
        holder.comment.text = item.description
        holder.rating.text = String.format("%.0f/10", item.rating)
        holder.username.text = item.reviewerEmail
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return items.size
    }

    // Function to update the data in the adapter
    fun updateItems(newItems: List<ReviewDTO>) {
        items = newItems
        notifyDataSetChanged()
    }
}
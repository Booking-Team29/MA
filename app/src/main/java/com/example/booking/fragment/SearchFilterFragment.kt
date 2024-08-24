package com.example.booking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.booking.R

class SearchFilterFragment : DialogFragment() {
    interface OnFilterAppliedListener {
        fun onFilterApplied(
            hotel: Boolean,
            apartement: Boolean,
            studio: Boolean,
            wifi: Boolean,
            parking: Boolean,
            balcony: Boolean,
            priceFrom: Double?,
            priceTo: Double?,
        )
    }

    private var listener: OnFilterAppliedListener? = null

    fun setOnFilterAppliedListener(listener: OnFilterAppliedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_filter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hotelCheckBox: CheckBox = view.findViewById(R.id.hotelCheckBox)
        val apartementCheckBox: CheckBox = view.findViewById(R.id.apartementCheckBox)
        val studioCheckBox: CheckBox = view.findViewById(R.id.studioCheckBox)
        val wifiCheckBox: CheckBox = view.findViewById(R.id.wifiCheckBox)
        val freeParkingCheckBox: CheckBox = view.findViewById(R.id.parkingCheckBox)
        val baclonyCheckBox: CheckBox = view.findViewById(R.id.balconyCheckBox)
        val applyButton: Button = view.findViewById(R.id.applyButton)
        val closeButton: Button = view.findViewById(R.id.closeButton)
        val fromPrice: EditText = view.findViewById(R.id.priceFromInput)
        val toPrice: EditText = view.findViewById(R.id.toPriceInput)

        closeButton.setOnClickListener { dismiss() }
        applyButton.setOnClickListener {
            val startingPrice: Double? = fromPrice.text.toString().toDoubleOrNull()
            val endPrice: Double? = toPrice.text.toString().toDoubleOrNull()
            listener?.onFilterApplied(
                hotelCheckBox.isChecked,
                apartementCheckBox.isChecked,
                studioCheckBox.isChecked,
                wifiCheckBox.isChecked,
                freeParkingCheckBox.isChecked,
                baclonyCheckBox.isChecked,
                startingPrice,
                endPrice
            )
            dismiss()

        }
    }
}
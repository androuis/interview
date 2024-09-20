package com.example.interview.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.interview.R
import com.example.interview.domain.FlightStatus

class FlightStatusAdapter() : RecyclerView.Adapter<FlightStatusAdapter.ViewHolder>() {

    private var flightStatuses: List<FlightStatus> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val flight: TextView = itemView.findViewById(R.id.flight)
        val status: TextView = itemView.findViewById(R.id.status)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_flight_status, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.flight.text = flightStatuses[position].destination
        viewHolder.status.text = flightStatuses[position].time.toString()
    }

    override fun getItemCount() = flightStatuses.size

    fun updateFlightStatusList(flightStatuses: List<FlightStatus>) {
        this.flightStatuses = flightStatuses
        notifyDataSetChanged()
    }

}
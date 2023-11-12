package com.camu.planeslufthansa.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.camu.planeslufthansa.R
import com.camu.planeslufthansa.data.remote.model.PlaneDto
import com.camu.planeslufthansa.databinding.PlaneElementBinding




class PlanesAdapter (
    private val planes: List<PlaneDto>,
    private val onPlaneClicked: (PlaneDto) -> Unit
): RecyclerView.Adapter<PlanesAdapter.ViewHolder>(){


    class ViewHolder(private val binding: PlaneElementBinding): RecyclerView.ViewHolder(binding.root){
        val ivThumbnail = binding.ivThumbnail

        fun bind(plane: PlaneDto){
            val context = binding.root.context
            binding.tvTitle.text = plane.title
            binding.tvManufacturing.text = plane.manufacturing
            binding.tvCapacity.text = context.getString(R.string.capacidad, plane.capacity)
            binding.tvFleet.text = context.getString(R.string.flota,plane.fleet)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = PlaneElementBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int = planes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plane = planes[position]

        holder.bind(plane)

        Glide.with(holder.itemView.context)
            .load(plane.thumbnail)
            .into(holder.ivThumbnail)


        holder.itemView.setOnClickListener{
            onPlaneClicked(plane)
        }

    }
}
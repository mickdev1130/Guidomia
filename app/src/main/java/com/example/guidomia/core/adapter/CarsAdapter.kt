package com.example.guidomia.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.R
import com.example.guidomia.core.data.CarItem
import com.example.guidomia.core.utils.AutoUpdatableAdapter
import com.example.guidomia.databinding.ItemCarBinding
import javax.inject.Inject
import kotlin.properties.Delegates


class CarsAdapter @Inject constructor() :
    RecyclerView.Adapter<CarsAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<CarItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    //internal var onItemClick: (List<CarItem>) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_car
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val car = collection[position]
            item = car

            val ratingAdapter = RatingAdapter()
            rvRating.adapter = ratingAdapter
            val prosAdapter = ProsAdapter()
            rvPros.adapter = prosAdapter
            val consAdapter = ConsAdapter()
            rvCons.adapter = consAdapter

            ratingAdapter.collection = car.rating

            if (car.prosList.isNotEmpty()) {
                prosAdapter.collection = listOf(car)
            }

            if (car.consList.isNotEmpty()) {
                consAdapter.collection = listOf(car)
            }

            holder.itemView.setOnClickListener {
                for (d in collection) {
                    d.isExpanded = false
                }
                car.isExpanded = true
                notifyDataSetChanged()
            }
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = collection.size

    class Holder(val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ItemCarBinding>(
                    inflater,
                    layout,
                    parent,
                    false
                )
                return Holder(
                    binding
                )
            }
        }
    }

}

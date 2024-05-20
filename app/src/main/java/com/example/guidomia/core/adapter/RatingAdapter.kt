package com.example.guidomia.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.R
import com.example.guidomia.core.utils.AutoUpdatableAdapter
import com.example.guidomia.databinding.ItemRatingBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class RatingAdapter @Inject constructor() :
    RecyclerView.Adapter<RatingAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: Int by Delegates.observable(0) { _, old, new ->
        autoNotify((0 until old).toList(), (0 until new).toList()) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_rating
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            item = collection
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = collection

    class Holder(val binding: ItemRatingBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ItemRatingBinding>(
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
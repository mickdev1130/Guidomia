package com.example.guidomia.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.R
import com.example.guidomia.core.utils.AutoUpdatableAdapter
import com.example.guidomia.databinding.ItemDataBinding
import javax.inject.Inject
import kotlin.properties.Delegates


class DescriptionAdapter @Inject constructor() :
    RecyclerView.Adapter<DescriptionAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<String> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_data
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            item = collection[position]
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = collection.size

    class Holder(val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ItemDataBinding>(
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

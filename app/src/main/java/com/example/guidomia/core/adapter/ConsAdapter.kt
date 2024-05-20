package com.example.guidomia.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.R
import com.example.guidomia.core.data.CarItem
import com.example.guidomia.core.utils.AutoUpdatableAdapter
import com.example.guidomia.databinding.ItemConsBinding
import javax.inject.Inject
import kotlin.properties.Delegates

class ConsAdapter @Inject constructor() :
    RecyclerView.Adapter<ConsAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<CarItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_cons
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val desc = collection[position]
            val descAdapter = DescriptionAdapter()
            descAdapter.apply {
                collection = desc.consList.filter { it.isNotEmpty() }
                rvData.adapter = this
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int = collection.size

    class Holder(val binding: ItemConsBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ItemConsBinding>(
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

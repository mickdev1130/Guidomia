package com.example.guidomia.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.guidomia.core.utils.AutoUpdatableAdapter
import javax.inject.Inject
import kotlin.properties.Delegates
import com.example.guidomia.R
import com.example.guidomia.core.data.CarItem
import com.example.guidomia.databinding.ItemProsBinding


class ProsAdapter @Inject constructor() :
    RecyclerView.Adapter<ProsAdapter.Holder>(),
    AutoUpdatableAdapter {

    internal var collection: List<CarItem> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder.from(
            parent,
            R.layout.item_pros
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.binding.apply {
            val desc = collection[position]
            val descAdapter = DescriptionAdapter()
            descAdapter.apply {
                collection = desc.prosList.filter { it.isNotEmpty() }
                rvData.adapter = this
                executePendingBindings()
            }
        }
    }

    override fun getItemCount(): Int = collection.size

    class Holder(val binding: ItemProsBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup, layout: Int): Holder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = DataBindingUtil.inflate<ItemProsBinding>(
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

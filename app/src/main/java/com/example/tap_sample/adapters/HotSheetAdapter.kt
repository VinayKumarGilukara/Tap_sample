package com.example.tap_sample.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tap_sample.database.HotSheet
import com.example.tap_sample.databinding.RowLayoutBinding


class HotSheetAdapter(private val onItemClickListener: OnItemClickListener? = null) :
    RecyclerView.Adapter<HotSheetAdapter.MyViewHolder>() {

    private var originalData = emptyList<HotSheet>()
    private var filteredData = emptyList<HotSheet>()


    class MyViewHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = filteredData[position]

        val formattedName = currentItem.name?.replace(",", "") ?: ""

        holder.binding.firstNameTextView.text = formattedName

        holder.itemView.setOnClickListener {
           onItemClickListener?.onItemClick(currentItem)

        }
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    fun setData(newData: List<HotSheet>) {
        originalData = newData.sortedBy { it.name }
        filteredData = originalData
        notifyDataSetChanged()
    }

    fun filterData(query: String) {
        filteredData = if (query.isBlank()) {
            originalData
        } else {
            originalData.filter {
                it.name!!.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(hotSheet: HotSheet)
    }
}
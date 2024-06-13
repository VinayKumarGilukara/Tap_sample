package com.example.tap_sample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.tap_sample.database.Inspector
import com.example.tap_sample.database.LineRoute
import com.example.tap_sample.databinding.RowLayoutBinding

class SearchAdapter(private val onItemClickListener: OnItemClickListener? = null) :
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    private var oldData = emptyList<LineRoute>()
    private var filterData = emptyList<LineRoute>()

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
        val currentItem = filterData[position]

        holder.binding.firstNameTextView.text = currentItem.description

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return filterData.size
    }

    fun setData(newData: List<LineRoute>) {
        oldData = newData.distinctBy { it.description }
        notifyDataSetChanged()
    }

    fun filterData(query: String) {
        filterData = if (query.isBlank()) {
            oldData
        } else {
            oldData.filter {
                it.description?.contains(query, ignoreCase = true) == true
            }
        }
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(lineRoute: LineRoute)
    }
}





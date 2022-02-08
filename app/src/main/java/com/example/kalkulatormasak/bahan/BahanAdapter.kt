package com.example.kalkulatormasak.bahan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kalkulatormasak.databinding.ItemBahanBinding
import com.example.kalkulatormasak.model.Bahan

class BahanAdapter : RecyclerView.Adapter<BahanAdapter.ViewHolder>() {

    private val list = ArrayList<Bahan>()

    fun setData(list: List<Bahan>) {
        val diffCallback = BahanDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBahanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    class ViewHolder(private val binding: ItemBahanBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Bahan) {
            binding.tvBahan.text = item.name
            binding.tvUnit.text = "Satuan: ${item.unit}"
        }
    }
}
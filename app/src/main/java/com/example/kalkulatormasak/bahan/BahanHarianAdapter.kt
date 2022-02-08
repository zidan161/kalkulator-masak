package com.example.kalkulatormasak.bahan

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kalkulatormasak.databinding.ItemBahanHarianBinding
import com.example.kalkulatormasak.model.Bahan

class BahanHarianAdapter(private val isMenu: Boolean, private val listener: (Long, Float) -> Unit,
                         private val edit: (Bahan) -> Unit) : RecyclerView.Adapter<BahanHarianAdapter.ViewHolder>() {

    private val list = ArrayList<Bahan>()

    fun setData(list: List<Bahan>) {
        val diffCallback = BahanDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBahanHarianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItem(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    inner class ViewHolder(private val binding: ItemBahanHarianBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: Bahan, pos: Int) {
            binding.tvName.text = item.name
            binding.tvQty.text = if (item.qty == 0f) "Habis"
                                 else "${item.qty} ${item.unit}"

            binding.btnRemove.setOnClickListener {
                list.remove(item)
                notifyItemRemoved(pos)
                listener(item.bahanId, 0f)
            }

            binding.btnEdit.setOnClickListener { edit(item) }
        }
    }
}
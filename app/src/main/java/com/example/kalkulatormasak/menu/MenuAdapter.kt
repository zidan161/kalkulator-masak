package com.example.kalkulatormasak.menu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kalkulatormasak.checkGudang
import com.example.kalkulatormasak.databinding.ItemMenuBinding
import com.example.kalkulatormasak.model.MenuWithBahan
import com.google.android.material.chip.Chip

class MenuAdapter(private val ctx: Context, private val listener: (MenuWithBahan, Int, Int) -> Unit): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    private val list = ArrayList<MenuWithBahan>()

    fun setData(list: List<MenuWithBahan>) {
        val diffCallback = MenuDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ctx, listener, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    class ViewHolder(
        private val ctx: Context,
        private val listener: (MenuWithBahan, Int, Int) -> Unit,
        private val binding: ItemMenuBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: MenuWithBahan) {
            binding.tvName.text = item.menu.name
            binding.chipGroup.apply {
                for (i in item.listBahan) {
                    val chip = Chip(ctx)
                    chip.text = "${i.realBahan.name} ${i.bahan.qty} ${i.realBahan.unit}"
                    chip.isCheckable = false
                    this.addView(chip)
                }
            }
            binding.tlQty.helperText = "Jumlah Porsi yang dapat dimasak: ${checkGudang(item.listBahan)}"
            binding.btnMasak.setOnClickListener {
                val qty = binding.edtQty.text.toString()

                if (qty.isEmpty()) {
                    binding.edtQty.error = "Harus diisi!"
                    return@setOnClickListener
                }
                listener(item, REQUEST_ADD, qty.toInt())
            }
            binding.btnEdit.setOnClickListener { listener(item, REQUEST_EDIT, 0) }
        }

        companion object {
            const val REQUEST_ADD = 101
            const val REQUEST_EDIT = 100
        }
    }
}
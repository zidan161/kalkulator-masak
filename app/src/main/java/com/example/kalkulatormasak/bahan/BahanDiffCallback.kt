package com.example.kalkulatormasak.bahan

import androidx.recyclerview.widget.DiffUtil
import com.example.kalkulatormasak.model.Bahan

class BahanDiffCallback(private val oldData: List<Bahan>, private val newData: List<Bahan>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].bahanId == newData[newItemPosition].bahanId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldData[oldItemPosition]
        val new = newData[newItemPosition]

        return old.name == new.name && old.unit == new.unit && old.qty == new.qty
    }
}
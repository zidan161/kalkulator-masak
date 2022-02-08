package com.example.kalkulatormasak

import androidx.recyclerview.widget.DiffUtil
import com.example.kalkulatormasak.model.Masak
import com.example.kalkulatormasak.model.Report
import com.example.kalkulatormasak.model.ReportWithMasak

class MasakDiffCallback(private val oldData: List<Masak>, private val newData: List<Masak>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].id == newData[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldData[oldItemPosition]
        val new = newData[newItemPosition]

        return old.count == new.count && old.menuName == new.menuName
    }
}
package com.example.kalkulatormasak

import androidx.recyclerview.widget.DiffUtil
import com.example.kalkulatormasak.model.ReportWithMasak

class ReportDiffCallback(private val oldData: List<ReportWithMasak>, private val newData: List<ReportWithMasak>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].report.id == newData[newItemPosition].report.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldData[oldItemPosition]
        val new = newData[newItemPosition]

        return old.masak == new.masak
    }
}
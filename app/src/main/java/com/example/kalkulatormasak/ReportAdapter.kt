package com.example.kalkulatormasak

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kalkulatormasak.databinding.ItemReportBinding
import com.example.kalkulatormasak.model.ReportWithMasak

class ReportAdapter(private val listener: (ReportWithMasak) -> Unit) : RecyclerView.Adapter<ReportAdapter.ViewHolder>() {

    private val list = ArrayList<ReportWithMasak>()

    fun setData(list: List<ReportWithMasak>) {
        val diffCallback = ReportDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.list.clear()
        this.list.addAll(list)
        this.list.reverse()
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    class ViewHolder(private val binding: ItemReportBinding, private val listener: (ReportWithMasak) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: ReportWithMasak) {
            binding.tvDate.text = if (item.report.date == getDate()) "Hari ini" else item.report.date
            binding.tvDate.setOnClickListener { listener(item) }
        }
    }
}
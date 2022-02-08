package com.example.kalkulatormasak

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.kalkulatormasak.databinding.ActivityTableBinding
import com.example.kalkulatormasak.model.MenuWithBahan
import com.example.kalkulatormasak.model.ReportWithMasak
import com.example.kalkulatormasak.print.PdfActivity
import com.example.kalkulatormasak.print.PrinterHelper

class TableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTableBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ReportWithMasak>("data")

        val factory = ViewModelFactory.getInstance(application)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        val listBahan = arrayListOf<String>()

        if (data != null) {

            binding.tvDate.text = "Laporan Tanggal: ${data.report.date}"
            binding.tvAdmin.text = "Admin: ${data.report.admin}"

            if (data.masak.isEmpty()) {
                binding.imgReport.visibility = View.VISIBLE
                binding.tvInfo.visibility = View.VISIBLE
            }

            binding.btnPrint.setOnClickListener {
                PrinterHelper().printText(data)
            }

            binding.btnPdf.setOnClickListener {
                val intent = Intent(this, PdfActivity::class.java)
                intent.putExtra("data", data)
                intent.putStringArrayListExtra("listBahan", listBahan)
                startActivity(intent)
            }

            data.masak.forEachIndexed { i, item ->

                val row = TableRow(this)

                val bahan = TextView(this)
                viewModel.getMenuWithBahanById(item.menuId!!).observe(this) {
                    var textI = ""
                    it.listBahan.forEachIndexed { i, data ->
                        textI += "-${data.bahan.qty * item.count} ${data.realBahan.unit} ${data.realBahan.name}"
                        if (i != it.listBahan.lastIndex) textI += ", "
                    }
                    listBahan.add(textI)
                    bahan.text = textI
                }

                val name = TextView(this)
                val portion = TextView(this)

                name.text = item.menuName
                portion.text = item.count.toString()

                name.textSize = 18f
                portion.textSize = 18f

                row.apply {
                    addView(name)
                    addView(portion)
                    addView(bahan)
                    setPadding(14, 14, 14, 14)
                    if (i % 2 == 0) {
                        setBackgroundColor(Color.rgb(245, 245, 239))
                    }
                }

                binding.tableLayout.addView(row)
            }
        }
    }
}
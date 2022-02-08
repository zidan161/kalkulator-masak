package com.example.kalkulatormasak.print

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import com.example.kalkulatormasak.getDate
import com.example.kalkulatormasak.model.MenuWithBahan
import com.example.kalkulatormasak.model.ReportWithMasak
import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.PDFBody
import com.tejpratapsingh.pdfcreator.views.PDFFooterView
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import com.tejpratapsingh.pdfcreator.views.basic.PDFVerticalView
import java.io.File
import com.tejpratapsingh.pdfcreator.views.PDFTableView
import com.tejpratapsingh.pdfcreator.views.PDFTableView.PDFTableRowView
import java.lang.Exception
import java.util.ArrayList

class PdfActivity : PDFCreatorActivity() {

    private lateinit var data: ReportWithMasak
    private lateinit var listBahan: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getParcelableExtra("data")!!
        listBahan = intent.getStringArrayListExtra("listBahan")!!

        createPDF("lprn_${getDate()}", object : PDFUtil.PDFUtilListener {
            override fun pdfGenerationSuccess(savedPDFFile: File?) {
                Toast.makeText(this@PdfActivity, "PDF Created", Toast.LENGTH_SHORT).show()
            }

            override fun pdfGenerationFailure(exception: Exception?) {
                Toast.makeText(this@PdfActivity, exception?.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getHeaderView(forPage: Int): PDFHeaderView {
        val headerView = PDFHeaderView(applicationContext)

        val verticalView = PDFVerticalView(applicationContext)
        val header = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H1).setText("Laporan Harian")

        val date = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3).setText("tanggal: ${getDate()}")
        date.setPadding(0, 20, 0, 0)

        verticalView.view.gravity = Gravity.CENTER_HORIZONTAL

        verticalView.addView(header)
        verticalView.addView(date)

        headerView.addView(verticalView)

        return headerView
    }

    override fun getBodyViews(): PDFBody {
        val bodyView = PDFBody()

        val headerRow = PDFTableRowView(applicationContext)
        headerRow.setPadding(0, 40, 0, 0)

        val headers = arrayOf("Masakan", "Porsi", "Bahan")
        for (i in headers) {
            val pdfTextView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P)
            pdfTextView.setText(i)
            headerRow.addToRow(pdfTextView)
        }

        val firstRow = getFirstRow()

        val tableView = PDFTableView(applicationContext, headerRow, firstRow)

        data.masak.drop(1).forEachIndexed { i, data ->
            val dataRow = PDFTableRowView(applicationContext)
            val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(data.menuName)
            val masak = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(data.count.toString())
            val bahan = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(listBahan[i+1])

            dataRow.addToRow(name)
            dataRow.addToRow(masak)
            dataRow.addToRow(bahan)

            tableView.addRow(dataRow)
        }
        bodyView.addView(tableView)

        return bodyView
    }

    override fun getFooterView(forPage: Int): PDFFooterView {
        val footer = PDFFooterView(this)

        val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText("Kalkulator Masak 2022")
        name.view.gravity = Gravity.HORIZONTAL_GRAVITY_MASK

        footer.addView(name)
        return footer
    }

    override fun onNextClicked(savedPDFFile: File?) {
        finish()
    }

    private fun getFirstRow(): PDFTableRowView {
        val firstRow = PDFTableRowView(applicationContext)
        firstRow.setPadding(0, 20, 0, 0)

        val firstData = data.masak[0]
        val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(firstData.menuName)
        val masak = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(firstData.count.toString())
        val bahan = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(listBahan[0])

        firstRow.addToRow(name)
        firstRow.addToRow(masak)
        firstRow.addToRow(bahan)

        return firstRow
    }

    override fun getExternalFilesDir(type: String?): File? {
        return super.getExternalFilesDir(type)
    }
}
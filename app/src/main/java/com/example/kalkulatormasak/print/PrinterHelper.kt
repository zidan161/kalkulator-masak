package com.example.kalkulatormasak.print

import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.example.kalkulatormasak.model.ReportWithMasak

class PrinterHelper {

    fun printText(data: ReportWithMasak) {

        var textMenus = ""

        for (i in data.masak) {
            textMenus += """
                [L]<b>${i.menuName}</b>[R]${i.count}
                [L]
                        """.trimIndent()
        }

        val printer = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)
        printer
            .printFormattedText(
                """
        [C]<u><font size='big'>Laporan Harian</font></u>
        [L]
        [C]================================
        [L]
        $textMenus
        [C]--------------------------------
        [L]
        [C]Terima Kasih :)
        """.trimIndent())
    }
}
package top.kikt.pdf

import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import top.kikt.pdf.table.TableRow

@Suppress("MemberVisibilityCanBePrivate")
/**
 * Created by CaiJingLong on 2022-12-05.
 *
 * Build a table for [Document].
 */
class PdfTableBuilder(val pdf: Pdf) {

    /**
     *  The table.
     */
    var columnCount = 1

    private var rows = ArrayList<TableRow>()

    /**
     * Make [TableRow] with [rowBuilder], and add it to table.
     */
    fun addRow(rowBuilder: Pdf.() -> TableRow) {
        val row = pdf.rowBuilder()
        rows.add(row)
    }

    fun build(): PdfPTable {
        val table = PdfPTable(columnCount)

        rows.forEach {
            val cells = it.build()
            cells.forEachIndexed { index, pdfPCell ->
                pdfPCell.colspan
                if (index < columnCount) {
                    table.addCell(pdfPCell)
                }
            }
            if (cells.size < columnCount) {
                for (i in 0 until columnCount - cells.size) {
                    table.addCell(Paragraph(""))
                }
            }
        }

        return table
    }

}
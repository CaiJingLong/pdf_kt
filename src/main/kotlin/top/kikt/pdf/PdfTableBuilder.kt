package top.kikt.pdf

import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPTable
import top.kikt.pdf.table.TableRow

@Suppress("MemberVisibilityCanBePrivate")
class PdfTableBuilder {

    /**
     *  The table.
     */
    var columnCount = 1

    private var rows = ArrayList<TableRow>()

    fun addRow(row: TableRow) {
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
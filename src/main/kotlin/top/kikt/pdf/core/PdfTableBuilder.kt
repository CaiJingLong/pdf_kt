package top.kikt.pdf.core

import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfPTable
import top.kikt.pdf.ILogger
import top.kikt.pdf.core.table.TableRow

@Suppress("MemberVisibilityCanBePrivate")
/**
 * Created by CaiJingLong on 2022-12-05.
 *
 * Build a table for [Document].
 */
class PdfTableBuilder(val pdf: Pdf, var columnCount: Int) : ILogger {

    private var rows = ArrayList<TableRow>()

    /**
     * The table element of [Document].
     */
    val table by lazy { PdfPTable(columnCount).apply(pdf.tableDefaultConfig) }

    /**
     * Make [TableRow] with [rowBuilder], and add it to table.
     */
    fun addRow(rowBuilder: TableRow.(Pdf) -> Unit = {}) {
        val row = TableRow(pdf, this)
        row.rowBuilder(pdf)
        rows.add(row)
    }

    /**
     * Build the table.
     */
    fun build(builder: (PdfPTable) -> Unit = {}): PdfPTable {
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

        builder(table)

        return table
    }

    fun row(config: TableRow.(Pdf) -> Unit) {
        addRow(config)
    }
}
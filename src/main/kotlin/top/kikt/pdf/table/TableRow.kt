package top.kikt.pdf.table

import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import top.kikt.pdf.Pdf

/**
 * Created by CaiJingLong on 2022-12-05.
 * Cells greater than the number of table columns will be ignored.
 */
class TableRow(val pdf: Pdf) {

    private var cells = ArrayList<PdfPCell>()

    /**
     * Add a [cell] to the row.
     * Use [config] to config the cell.
     */
    fun addCell(cell: PdfPCell, config: PdfPCell.() -> Unit = {}) {
        cell.config()
        cells.add(cell)
    }

    /**
     * Add a [text] to the row.
     * The text will be wrapped in [Paragraph].
     * Use [config] to config the cell or [Paragraph].
     */
    fun addText(text: String, textSize: Float = pdf.defaultFontSize, config: PdfPCell.(Paragraph) -> Unit = {}) {
        val paragraph = Paragraph(text)
        paragraph.font = pdf.getFont(textSize)
        val cell = PdfPCell(paragraph)
        cell.config(paragraph)
        addCell(cell)
    }

    fun addImage(image: Image, action: PdfPCell.() -> Unit = {}) {
        val pCell = PdfPCell(image)
        action(pCell)
        cells.add(pCell)
    }

    fun build(): List<PdfPCell> {
        return cells
    }

}
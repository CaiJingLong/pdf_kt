package top.kikt.pdf.table

import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell

class TableRow {

    private var cells = ArrayList<PdfPCell>()

    fun addCell(cell: PdfPCell) {
        cells.add(cell)
    }

    fun addText(text: String, action: Paragraph.() -> Unit = {}) {
        val paragraph = Paragraph(text)
        action(paragraph)
        cells.add(PdfPCell(paragraph))
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
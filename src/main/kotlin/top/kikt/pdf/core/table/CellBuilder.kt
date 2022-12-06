package top.kikt.pdf.core.table

import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import java.io.InputStream

@Suppress("unused")
class CellBuilder(private val tableRow: TableRow) {

    private val pdf = tableRow.pdf

    private val cell = PdfPCell()

    init {
        tableRow.pdfTableBuilder.everyCellConfig(cell)
    }

    fun text(
        text: String,
        textSize: Float = tableRow.pdf.defaultFontSize,
        config: PdfPCell.(Paragraph) -> Unit = {},
    ) {
        val font = pdf.getFont(textSize)
        val paragraph = Paragraph(text, font)
        cell.config(paragraph)
        cell.addElement(paragraph)
    }

    fun paragraph(
        config: PdfPCell.(Paragraph) -> Unit
    ) {
        val paragraph = Paragraph()
        cell.config(paragraph)
        cell.addElement(paragraph)
    }

    fun image(
        image: Image,
        config: PdfPCell.(Image) -> Unit = {},
    ) {
        cell.config(image)
        cell.addElement(image)
    }

    fun image(
        filePath: String,
        config: PdfPCell.(Image) -> Unit = {},
    ) {
        val image = Image.getInstance(filePath)
        this.image(image, config)
    }

    fun image(
        inputStream: InputStream,
        config: PdfPCell.(Image) -> Unit = {},
    ) {
        inputStream.use {
            val image = Image.getInstance(it.readBytes())
            this.image(image, config)
        }
    }

    fun config(config: CellBuilder.(PdfPCell) -> Unit) {
        config(this, cell)
    }

    fun build(): PdfPCell {
        return cell
    }

}
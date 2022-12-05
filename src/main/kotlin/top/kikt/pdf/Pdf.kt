package top.kikt.pdf

import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.Closeable

/**
 * Wrapper IText, and provide some useful method.
 */
class Pdf(pageSize: Rectangle = PageSize.A4, val baseFont: BaseFont = BaseFont.createFont()) : Closeable {

    private val margin = 0f

    private val document = Document(pageSize, margin, margin, margin, margin)
    private val outputStream = ByteArrayOutputStream()
    private val writer = PdfWriter.getInstance(document, outputStream)

    /**
     * Open the document.
     */
    fun open(config: Document.() -> Unit = {}) {
        updateDocument(config)
        document.open()
    }

    /**
     * close the document.
     */
    override fun close() {
        document.close()
        writer.close()
    }

    /**
     * output the pdf data to [ByteArray].
     */
    fun toByteArray(): ByteArray {
        return outputStream.toByteArray()
    }

    /**
     * New page.
     */
    fun newPage() {
        document.newPage()
    }

    /**
     * Config the document.
     */
    fun updateDocument(action: Document.() -> Unit) {
        document.action()
    }

    /**
     * Set the document margins, I think must be called before [open].
     */
    fun setMargins(left: Float? = null, right: Float? = null, top: Float? = null, bottom: Float? = null) {
        document.setMargins(
            left ?: document.leftMargin(),
            right ?: document.rightMargin(),
            top ?: document.topMargin(),
            bottom ?: document.bottomMargin(),
        )
    }

    /**
     * Add [Element] to [document]
     */
    fun add(element: Element) {
        document.add(element)
    }

    /**
     * Add text of [Trunk] to [document]
     */
    fun addText(text: String, textSize: Float = 12f, action: Chunk.() -> Unit = {}) {
        val chunk = Chunk(text)
        chunk.font = Font(baseFont, textSize)
        chunk.action()
        add(chunk)
    }

    /**
     * Add text of [Paragraph] to [document]
     */
    fun addParagraph(textSize: Float = 12f, action: Paragraph.() -> Unit = {}) {
        val paragraph = Paragraph()
        paragraph.font = Font(baseFont, textSize)
        paragraph.action()
        document.add(paragraph)
    }

    /**
     * Add [PdfPTable] to [document].
     *
     * The [PdfPTable] will be created by [builder].
     */
    fun addTable(builder: Document.() -> PdfPTable) {
        document.add(document.builder())
    }
}
package top.kikt.pdf

import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import java.io.ByteArrayOutputStream
import java.io.Closeable

/**
 * Wrapper IText, and provide some useful method.
 */
class Pdf(
    val pageSize: Rectangle = PageSize.A4,
    var baseFont: BaseFont = BaseFont.createFont(),
    var baseColor: BaseColor = BaseColor.BLACK,
) : Closeable {

    /**
     * The default font size.
     */
    var defaultFontSize: Float = 12f

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
     * Make [Font] with [textSize] by [baseFont].
     */
    fun getFont(textSize: Float): Font = Font(baseFont, textSize)

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
    fun add(element: Element, action: Pdf.() -> Unit = {}) {
        action()
        document.add(element)
    }

    /**
     * Add text of [Chunk] to [document]
     */
    fun addText(text: String, textSize: Float = defaultFontSize, action: Chunk.() -> Unit = {}) {
        val chunk = Chunk(text)
        chunk.font = getFont(textSize)
        chunk.action()
        add(chunk)
    }

    /**
     * Add text of [Paragraph] to [document]
     */
    fun addParagraph(textSize: Float = defaultFontSize, action: Paragraph.() -> Unit = {}) {
        val paragraph = Paragraph()
        paragraph.font = getFont(textSize)
        paragraph.action()
        document.add(paragraph)
    }

    /**
     * Add [Image] to [document]
     */
    fun <T : Image> addImage(image: T, action: T.() -> Unit = {}) {
        image.action()
        document.add(image)
    }

    /**
     * Add [PdfPTable] to [document] with [filePath].
     */
    fun addImage(filePath: String, action: Image.() -> Unit = {}) {
        val image = Image.getInstance(filePath)
        addImage(image, action)
    }

    /**
     * Add [PdfPTable] to [document] with [bytes].
     */
    fun addImage(bytes: ByteArray, action: Image.() -> Unit = {}) {
        val image = Image.getInstance(bytes)
        addImage(image, action)
    }

    /**
     * Get width of [pageSize].
     */
    fun getWidth(): Float {
        return pageSize.width
    }

    /**
     * Get height of [pageSize].
     */
    fun getHeight(): Float {
        return pageSize.height
    }

    /**
     * Add LineSeparator to [document].
     */
    fun addLine(height: Float = 1.5f, percentage: Float = 100f, action: LineSeparator.() -> Unit = {}) {
        val lineSeparator = LineSeparator()

        lineSeparator.lineColor = baseColor
        lineSeparator.lineWidth = height
        lineSeparator.percentage = percentage

        lineSeparator.action()
        document.add(lineSeparator)
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
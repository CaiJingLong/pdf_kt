package top.kikt.pdf.core

import com.itextpdf.text.*
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.itextpdf.text.pdf.draw.LineSeparator
import top.kikt.pdf.exts.setBold
import top.kikt.pdf.exts.setItalic
import top.kikt.pdf.exts.setStrikeThrough
import top.kikt.pdf.exts.setUnderline
import java.io.*

/**
 * Wrapper IText, and provide some useful method.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class Pdf(
    /**
     * The page size of pdf.
     */
    val pageSize: Rectangle = PageSize.A4,

    /**
     * The default font size.
     */
    var defaultFontSize: Float = 12f,

    /**
     * The default font.
     */
    var baseFont: BaseFont = BaseFont.createFont(),

    /**
     * The default color.
     */
    var baseColor: BaseColor = BaseColor.BLACK,

    /**
     * Table defaultConfig
     */
    var tableDefaultConfig: PdfPTable.() -> Unit = {},

    /**
     * Cell defaultConfig
     */
    var cellDefaultConfig: PdfPCell.() -> Unit = {},
) : Closeable {

    private val document = Document(pageSize)
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
        if (document.isOpen) {
            close()
        }
        return outputStream.toByteArray()
    }

    /**
     * Make [Font] with [textSize] by [baseFont].
     */

    fun getFont(
        textSize: Float,
        color: BaseColor = baseColor,
        isBold: Boolean = false,
        isItalic: Boolean = false,
        isUnderline: Boolean = false,
        isStrikeThrough: Boolean = false
    ): Font {
        val font = Font(baseFont, textSize)
        font.color = color
        if (isBold) {
            font.setBold()
        }
        if (isItalic) {
            font.setItalic()
        }
        if (isUnderline) {
            font.setUnderline()
        }
        if (isStrikeThrough) {
            font.setStrikeThrough()
        }
        return font
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
    fun add(element: Element, action: Pdf.() -> Unit = {}) {
        action()
        document.add(element)
    }

    /**
     * Add text of [Chunk] to [document]
     */
    fun addTextChunk(
        text: String,
        textSize: Float = defaultFontSize,
        color: BaseColor = baseColor,
        isBold: Boolean = false,
        isItalic: Boolean = false,
        isUnderline: Boolean = false,
        isStrikeThrough: Boolean = false,
        action: Chunk.(Font) -> Unit = {}
    ) {
        val chunk = Chunk(text)
        val font = getFont(textSize, color, isBold, isItalic, isUnderline, isStrikeThrough)
        chunk.font = font
        chunk.action(font)
        add(chunk)
    }

    /**
     * Add text of [Paragraph] to [document]
     */
    fun addParagraph(
        textSize: Float = defaultFontSize,
        color: BaseColor = baseColor,
        isBold: Boolean = false,
        isItalic: Boolean = false,
        isUnderline: Boolean = false,
        isStrikeThrough: Boolean = false,
        action: Paragraph.(Font) -> Unit = {},
    ) {
        val paragraph = Paragraph()
        val font = getFont(textSize, color, isBold, isItalic, isUnderline, isStrikeThrough)
        paragraph.font = font
        paragraph.action(font)
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
    fun addTableWithDocument(builder: Document.() -> PdfPTable) {
        document.add(document.builder())
    }

    /**
     * Add [PdfPTable] to [document].
     *
     * The [PdfPTable] will be created by [builder].
     *
     * The builder provide [PdfTableBuilder] to help user to create [PdfPTable].
     */
    fun addTable(
        columnCount: Int,
        builder: PdfTableBuilder.(PdfPTable) -> Unit
    ) {
        val pdfTable = PdfTableBuilder(this, columnCount)
            .apply {
                this.columnCount = columnCount
                builder(table)
            }
            .build()
        add(pdfTable)
    }

    fun saveTo(file: File) {
        file.parentFile.mkdirs()
        val stream = file.outputStream()
        stream.use {
            saveTo(it)
        }
    }

    fun saveTo(path: String) {
        saveTo(File(path))
    }

    fun saveTo(outputStream: OutputStream) {
        outputStream.write(toByteArray())
    }


    fun table(
        columnCount: Int,
        config: PdfTableBuilder.() -> Unit,
    ) {
        val table = PdfTableBuilder(this, columnCount)
        table.config()
        add(table.build())
    }

    fun chunk(
        text: String,
        fontSize: Float = defaultFontSize,
        color: BaseColor = baseColor,
        builder: Chunk.(Font) -> Unit = {},
    ) {
        val font = getFont(fontSize).apply {
            this.color = color
        }
        val chunk = Chunk(text, font)
        chunk.builder(font)
        add(chunk)
    }

    fun text(
        text: String,
        fontSize: Float = defaultFontSize,
        color: BaseColor = baseColor,
        builder: Paragraph.(Font) -> Unit = {},
    ) {
        val font = getFont(fontSize).apply {
            this.color = color
        }
        val paragraph = Paragraph(text, font)
        paragraph.builder(font)
        add(paragraph)
    }

    fun paragraph(
        text: String,
        fontSize: Float = defaultFontSize,
        color: BaseColor = baseColor,
        builder: Paragraph.(Font) -> Unit = {},
    ) = text(text, fontSize, color, builder)

    fun line(
        width: Float = 100f,
        height: Float = 1f,
        color: BaseColor = baseColor,
    ) {
        val line = LineSeparator(1f, width, color, Element.ALIGN_CENTER, -height)
        add(line)
    }

    fun image(
        image: Image,
        builder: Image.() -> Unit = {},
    ) {
        image.builder()
        add(image)
    }

    fun image(
        bytes: ByteArray,
        builder: Image.() -> Unit = {},
    ) {
        image(Image.getInstance(bytes), builder)
    }

    fun image(
        path: String,
        builder: Image.() -> Unit = {},
    ) {
        image(Image.getInstance(path), builder)
    }

    fun image(
        inputStream: InputStream,
        builder: Image.() -> Unit = {},
    ) {
        image(Image.getInstance(inputStream.readBytes()), builder)
    }
}
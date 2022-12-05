package top.kikt.pdf

import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.PageSize.A4
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter
import org.junit.jupiter.api.Test
import top.kikt.pdf.core.Pdf
import java.io.File

class CreateDocumentTest {

    private val createDir = File("sample/create")

    init {
        createDir.mkdirs()
    }

    @Test
    fun createDocument() {
        val document = Document(A4)
        val outputStream = File(createDir, "output.pdf").outputStream()
        outputStream.use {
            val writer = PdfWriter.getInstance(document, it)

            document.open()
            document.newPage()

            val text = "hello world"
            val textElement = com.itextpdf.text.Paragraph(text)

            document.add(textElement)
            document.close()


            writer.close()
        }
    }

    @Test
    fun createPdfTest() {
        val pdf = Pdf()

        val baseFont = BaseFont.createFont()
        val font = Font(baseFont, 20f)

        pdf.open {
            val margin = this.topMargin()
            setMargins(margin, margin, margin, margin)
        }
        pdf.addParagraph {
            add(Chunk("hello world", font))
        }
        pdf.addTextChunk("hello world") {
            setFont(font)
        }
        pdf.addTextChunk("hello world") {
            setFont(font)
        }
        pdf.addParagraph {
            add(Chunk("hello world", font))
        }

        pdf.addTable(4) {
            it.widthPercentage = 100f
            for (i in 0..10) {
                addRow {
                    addText("index: $i")
                    addText("我是内容1")
                    addText("我是内容2")
                    addText("我是内容3")
                    addText("我是内容4")
                }
            }
        }

        pdf.close()

        val sample2 = File(createDir, "sample2.pdf")
        if (sample2.exists()) {
            sample2.delete()
        }
        sample2.writeBytes(pdf.toByteArray())
    }
}
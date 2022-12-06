package top.kikt.pdf

import com.itextpdf.text.Element
import com.itextpdf.text.Paragraph
import org.junit.jupiter.api.Test
import top.kikt.pdf.dsl.pdf
import top.kikt.pdf.dsl.usePdf

class PdfDslTest {

    @Test
    fun createPdfTest() {
        pdf {
            open()
            addParagraph {
                add("The first page")
            }

            addTextChunk("Bold text", 20f, isBold = true)
            addTextChunk("Italic text", 20f, isItalic = true)
            addTextChunk("Underline text", 20f, isUnderline = true)
            addTextChunk("StrikeThrough text", 20f, isStrikeThrough = true)

            addTextChunk("Bold and italic text", 20f, isBold = true, isItalic = true)
            addTextChunk("Bold and underline text", 20f, isBold = true, isUnderline = true)
            addTextChunk("Italic and underline text", 20f, isItalic = true, isUnderline = true)

            newPage()

            addParagraph {
                add("The second page")
            }

            addParagraph(isBold = true) {
                add("Bold text")
            }

            addParagraph(isItalic = true) {
                add("Italic text")
            }

            addParagraph(isUnderline = true) {
                add("Underline text")
            }

            addParagraph(isStrikeThrough = true) {
                add("StrikeThrough text")
            }

            addParagraph(isBold = true, isItalic = true) {
                add("Bold and italic text")
            }

            addParagraph(isBold = true, isUnderline = true) {
                add("Bold and underline text")
            }

            addParagraph(isItalic = true, isUnderline = true) {
                add("Italic and underline text")
            }

            addParagraph(isBold = true, isItalic = true, isUnderline = true) {
                add("Bold, italic and underline text")
            }

            addParagraph(isBold = true, isItalic = true, isUnderline = true, isStrikeThrough = true) {
                add("Bold, italic, underline and strikeThrough text")
            }

            close()
        }.saveTo("sample/dsl/text.pdf")
    }

    @Test
    fun createTable() {
        usePdf {
            newPage()
            table(3) {
                row {
                    cell {
                        text("1")
                        paragraph {
                            it.add(Paragraph("p1"))
                            it.add(Paragraph("p2"))
                            it.add(Paragraph("p3"))
                        }
                    }
                    cell {
                        text("2")
                    }
                    cell {
                        text("3")
                    }
                }
                row {
                    cell { text("10") }
                    cell {
                        image("sample/src.png") {
                            setPadding(10f)
                            it.scalePercent(20f)
                        }
                    }
                }
            }
        }.saveTo("sample/dsl/table.pdf")
    }

    @Test
    fun createPdfParagraph() {
        usePdf {
            text("Align center") {
                alignment = Element.ALIGN_CENTER
            }

            space(height = 10)
            line()

            text("Align right") {
                alignment = Element.ALIGN_RIGHT
            }

            line()

            text("Align left") {
                alignment = Element.ALIGN_LEFT
            }

            line()

            text("Align justify") {
                alignment = Element.ALIGN_JUSTIFIED
            }

            line()

            val longText = "Long text ".repeat(15)

            text("Indentation left: $longText") {
                indentationLeft = 20f
            }

            line()
            text("Indentation right: $longText") {
                indentationRight = 20f
            }

        }.saveTo("sample/dsl/paragraph.pdf")
    }
}
package top.kikt.pdf.core

import com.itextpdf.text.pdf.PdfDocument
import com.itextpdf.text.pdf.PdfWriter
import java.io.OutputStream

internal class PdfDocumentImpl() : PdfDocument() {

    public override fun newLine() {
        super.newLine()
    }

}


internal class PdfWriterImpl(document: PdfDocument, os: OutputStream) : PdfWriter(document, os) {

}
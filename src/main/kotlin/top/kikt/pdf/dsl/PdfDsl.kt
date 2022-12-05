package top.kikt.pdf.dsl

import com.itextpdf.text.BaseColor
import com.itextpdf.text.PageSize
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import top.kikt.pdf.core.Pdf

fun pdf(
    pageSize: Rectangle = PageSize.A4,
    defaultFontSize: Float = 12f,
    baseFont: BaseFont = BaseFont.createFont(),
    baseColor: BaseColor = BaseColor.BLACK,
    tableDefaultConfig: PdfPTable.() -> Unit = {},
    cellDefaultConfig: PdfPCell.() -> Unit = {},
    builder: Pdf.() -> Unit,
): Pdf {
    val pdf = Pdf(pageSize, defaultFontSize, baseFont, baseColor, tableDefaultConfig, cellDefaultConfig)
    pdf.builder()
    return pdf
}

fun usePdf(
    pageSize: Rectangle = PageSize.A4,
    defaultFontSize: Float = 12f,
    baseFont: BaseFont = BaseFont.createFont(),
    baseColor: BaseColor = BaseColor.BLACK,
    tableDefaultConfig: PdfPTable.() -> Unit = {},
    cellDefaultConfig: PdfPCell.() -> Unit = {},
    builder: Pdf.() -> Unit,
): Pdf {
    val pdf = Pdf(pageSize, defaultFontSize, baseFont, baseColor, tableDefaultConfig, cellDefaultConfig)
    pdf.open()
    pdf.builder()
    pdf.close()
    return pdf
}
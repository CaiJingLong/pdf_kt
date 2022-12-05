package top.kikt.pdf.dsl

import com.itextpdf.text.BaseColor
import com.itextpdf.text.PageSize
import com.itextpdf.text.Rectangle
import com.itextpdf.text.pdf.BaseFont
import top.kikt.pdf.core.Pdf

fun pdf(
    pageSize: Rectangle = PageSize.A4,
    baseFont: BaseFont = BaseFont.createFont(),
    baseColor: BaseColor = BaseColor.BLACK,
    builder: Pdf.() -> Unit,
): Pdf {
    val pdf = Pdf(pageSize, baseFont, baseColor)
    pdf.builder()
    return pdf
}

package top.kikt.pdf.exts

import com.itextpdf.text.BaseColor
import com.itextpdf.text.Rectangle

/**
 * The left, right, top and bottom border enum.
 */
enum class BorderSide {
    Left, Right, Top, Bottom;

    fun setBorder(rectangle: Rectangle, color: BaseColor? = null, width: Float? = null) {
        when (this) {
            Left -> {
                rectangle.borderColorLeft = color ?: rectangle.borderColorLeft
                rectangle.borderWidthLeft = width ?: rectangle.borderWidthLeft
            }

            Right -> {
                rectangle.borderColorRight = color ?: rectangle.borderColorRight
                rectangle.borderWidthRight = width ?: rectangle.borderWidthRight
            }

            Top -> {
                rectangle.borderColorTop = color ?: rectangle.borderColorTop
                rectangle.borderWidthTop = width ?: rectangle.borderWidthTop
            }

            Bottom -> {
                rectangle.borderColorBottom = color ?: rectangle.borderColorBottom
                rectangle.borderWidthBottom = width ?: rectangle.borderWidthBottom
            }
        }
    }
}

/**
 * Set the border width of [width] and the border color of [color].
 */
fun Rectangle.setBorderAll(
    width: Float? = null,
    color: BaseColor? = null,
    sides: List<BorderSide> = listOf(
        BorderSide.Left,
        BorderSide.Right,
        BorderSide.Top,
        BorderSide.Bottom,
    ),
) {
    for (side in sides) {
        side.setBorder(this, color, width)
    }
}

/**
 * Set the border left width of [width] and the border left color of [color].
 */
fun Rectangle.setBorderLeft(width: Float? = null, color: BaseColor? = null) {
    width?.let {
        this.borderWidthLeft = it
    }
    color?.let {
        this.borderColorLeft = it
    }
}

/**
 * Set the border right width of [width] and the border right color of [color].
 */
fun Rectangle.setBorderRight(width: Float? = null, color: BaseColor? = null) {
    width?.let {
        this.borderWidthRight = it
    }
    color?.let {
        this.borderColorRight = it
    }
}

/**
 * Set the border top width of [width] and the border top color of [color].
 */
fun Rectangle.setBorderTop(width: Float? = null, color: BaseColor? = null) {
    width?.let {
        this.borderWidthTop = it
    }
    color?.let {
        this.borderColorTop = it
    }
}

/**
 * Set the border bottom width of [width] and the border bottom color of [color].
 */
fun Rectangle.setBorderBottom(width: Float? = null, color: BaseColor? = null) {
    width?.let {
        this.borderWidthBottom = it
    }
    color?.let {
        this.borderColorBottom = it
    }
}

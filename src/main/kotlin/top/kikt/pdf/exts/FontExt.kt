package top.kikt.pdf.exts

import com.itextpdf.text.Font

/**
 * Set font style to [Font.BOLD].
 */
fun Font.setBold() {
    if (this.style == Font.UNDEFINED) {
        this.style = Font.BOLD
    } else {
        this.style = this.style or Font.BOLD
    }
}

/**
 * Set font style to [Font.ITALIC].
 */
fun Font.setItalic() {
    if (this.style == Font.UNDEFINED) {
        this.style = Font.ITALIC
    } else {
        this.style = this.style or Font.ITALIC
    }
}

/**
 * Set font style to [Font.UNDERLINE].
 */
fun Font.setUnderline() {
    if (this.style == Font.UNDEFINED) {
        this.style = Font.UNDERLINE
    } else {
        this.style = this.style or Font.UNDERLINE
    }
}

/**
 * Set font style to [Font.STRIKETHRU].
 */
fun Font.setStrikeThrough() {
    if (this.style == Font.UNDEFINED) {
        this.style = Font.STRIKETHRU
    } else {
        this.style = this.style or Font.STRIKETHRU
    }
}

/**
 * Set font style to [Font.UNDEFINED].
 */
fun Font.setNormal() {
    this.style = Font.UNDEFINED
}
# pdf_kt

Wrapper itextpdf 5.x for Kotlin, support dsl to generate pdf.

## A PDF create library for Kotlin

### Usage

#### Add text

```kotlin
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

```

#### Add table

```kotlin
 pdf {
    open()
    addTable(3) { // column count
        addRow {
            addText("1")
            addText("2")
            addText("3")
        }
        addRow {
            addText("4")
            addText("5")
            addText("6")
        }
        addRow {
            addText("7")
            addText("8")
            addText("9")
        }
    }
    close()
}.saveTo("sample/dsl/table.pdf")
```
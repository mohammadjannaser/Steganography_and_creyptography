package stage4


import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

const val END_MARKER = "\u0000\u0000\u0003"

/**
 * Mohammad Jan Naser
 * CEO Softelino.com
 * nasersd
 */

fun main() {
    while (true) {
        println("Task (hide, show, exit):")

        when (val input = readLine()!!) {
            "exit" -> {
                println("Bye!")
                return
            }
            "hide" -> hide()
            "show" -> show()
            else -> println("Wrong task: $input")
        }
    }
}

fun hide() {
    println("Input image file:")
    val inputFile = readLine()!!

    println("Output image file:")
    val outputFile = readLine()!!

    println("Message to hide:")
    val message = readLine()!!

    println("Password:")
    val password = readLine()!!

    try {
        val image = ImageIO.read(File(inputFile))

        val encryptedMessage = message.xor(password)
        binaryStringOf(encryptedMessage).forEachIndexed { index, c ->
            val x = index % image.width
            val y = index / image.width
            val bit = c.digitToInt()  // turn Char '0' or '1' into Int 0 or 1

            val color = Color(image.getRGB(x, y))
            val newColor = Color(color.red, color.green, getNewBlueValue(color.blue, bit))

            image.setRGB(x, y, newColor.rgb)
        }

        ImageIO.write(image, "png", File(outputFile))
        println("Message saved in $outputFile image.")
    } catch (ex: IndexOutOfBoundsException) {
        println("The input image is not large enough to hold this message.")
    } catch (ex: IOException) {
        println(ex.message)
    }
}

fun show() {
    println("Input image file:")
    val inputFile = readLine()!!

    println("Password:")
    val password = readLine()!!

    try {
        println("Message:")
        val image = ImageIO.read(File(inputFile))

        extractBinaryString(image)
            .chunked(8)
            .map { byte -> byte.toInt(2).toChar() } // convert each byte ('00110101') to Char
            .joinToString("")
            .split(END_MARKER)
            .first()
            .xor(password)
            .let { println(it) }
    } catch (ex: IOException) {
        println(ex.message)
    }
}

// xor each byte of this String with corresponding byte of password
fun String.xor(password: String): String {
    return this.withIndex().map { (index, char) ->
        val correspondingPasswordChar = password[index % password.length]

        char.code.xor(correspondingPasswordChar.code)
    }.joinToString("") {
        it.toChar().toString()
    }
}

// extract the least significant bits of blue values from all pixels
// will return one long binary String "0010101100100..."
fun extractBinaryString(image: BufferedImage): String {
    val binaryString = StringBuffer()

    for (y in 0 until image.height) {
        for (x in 0 until image.width) {
            val color = Color(image.getRGB(x, y))
            val lastBit = color.blue.toString(2).last()
            binaryString.append(lastBit)
        }
    }
    return binaryString.toString()
}

// convert message into one long String of 0 and 1
// representing the message in binary (each byte left padded with '0' to 8 digits)
// message is terminated with 0, 0, 3
fun binaryStringOf(message: String): String {
    return (message + END_MARKER)
        .encodeToByteArray()
        .joinToString("") { byte -> binaryStringOf(byte) }
}

fun getNewBlueValue(blue: Int, i: Int): Int {
    return setLeastSignificantBit(blue, i == 1)
}

// convert byte to binary string, 8 long, padded with '0' in front
// e.g. 8 -> 00001000
fun binaryStringOf(b: Byte): String {
    return b.toString(2).padStart(8, '0')
}

// in the given byte set the least significant bit (0 or 1) to value according to newLSB
// http://www.java2s.com/example/java/java.util/returns-a-value-with-the-least-significant-bit-set-to-the-value.html
fun setLeastSignificantBit(byte: Int, newLSB: Boolean): Int {
    val leastIsOne = byte % 2 == 1
    return if (leastIsOne xor newLSB)
        byte xor 1
    else
        byte
}
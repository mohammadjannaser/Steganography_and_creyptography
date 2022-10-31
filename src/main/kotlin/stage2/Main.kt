package stage2

import java.awt.Color
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

fun main() {


    while (true){
        println("Task (hide, show, exit):")

        when(readln()) {
            "hide" -> hide()
            "exit" -> break
        }
    }

    println("Bye!")

}

fun hide() {

    println("Input image file:")
    val imageFile = readln()
    println("Output image file:")
    val outFileName = readln()

    println("Input Image: $imageFile")
    println("Output Image: $outFileName")

    try {
        val image = ImageIO.read(File(imageFile))

        for (x in 0 until image.width) {
            for (y in 0 until image.height) {
                val color = Color(image.getRGB(x,y))
                image.setRGB(x,y,updateColor(color).rgb)
            }
        }

        ImageIO.write(image,"png",File(outFileName))
        println("Image $outFileName is saved.")
    } catch (ex: IOException){
        println(ex.message)
    }
}

fun updateColor(color: Color) : Color {
    return Color(color.red or 1, color.green or 1, color.blue or 1)
}


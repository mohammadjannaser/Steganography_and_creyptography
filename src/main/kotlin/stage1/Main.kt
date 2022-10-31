
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {

    while (true) {
        println("Task (hide, show, exit):")

        when(val userInput = readln()) {
            "exit" -> {
                println("Bye!")
                break
            }
            "hide" -> { println("Hiding message in image.") }
            "show" -> println("Obtaining message from image.")
            else -> println("Wrong task: $userInput")
        }
    }
}


fun drawStrings(): BufferedImage {
    // Add your code here
    val bufferedImage = BufferedImage(200,200,BufferedImage.TYPE_INT_RGB)

    val graphics1 = bufferedImage.createGraphics()
    graphics1.color = Color.RED
    graphics1.drawString("Hello, images!",50,50)

    graphics1.color = Color.GREEN
    graphics1.drawString("Hello, images!",51,51)

    graphics1.color = Color.BLUE
    graphics1.drawString("Hello, images!",52,52)


    return bufferedImage
}


fun drawLines(): BufferedImage {
    // Add your code here
    val height = 200
    val width = 200

    val bufferedImage = BufferedImage(width,height,BufferedImage.TYPE_INT_RGB)

    val graphics = bufferedImage.createGraphics()
    graphics.color = Color.RED
    graphics.drawLine(0,0,200,200)

    val graphics2 = bufferedImage.createGraphics()
    graphics2.color = Color.GREEN
    graphics2.drawLine(200,0,0,200)

    val imageFile = File("myFirstImage.png")

    return bufferedImage
}

fun drawSquare(): BufferedImage {

    val height = 500
    val width = 500
    val bufferedImage = BufferedImage(width,height,BufferedImage.TYPE_INT_RGB)

    val graphics = bufferedImage.createGraphics()
    graphics.color = Color.RED
    graphics.drawRect(100,100,300,300)
    val imageFile = File("myFirstImage.png")

    saveImage(bufferedImage,imageFile)
    return bufferedImage
}



fun saveImage(image: BufferedImage, imageFile: File) {
    ImageIO.write(image, "png", imageFile)
}
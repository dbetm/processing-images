package examples;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
/**
 *
 * @author david
 */
public class DetectWebcamExample {
    public static void main(String[] args) throws IOException {
        // get default webcam and open it
        Webcam webcam = Webcam.getDefault();
        
        webcam.open();

        // get image
        BufferedImage image = webcam.getImage();

        // save image to PNG file
        ImageIO.write(image, "PNG", new File("assets/test.png"));
    }
}

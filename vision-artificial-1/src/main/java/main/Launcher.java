package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import preprocessing.Thresholding;
import sampling.Capture;
import sampling.LiveCamera;
import tools.Histogram;
import tools.Utilities;

/**
 *
 * @author david
 */
public class Launcher {
    public static void main(String args[]) {
        LiveCamera cam = new LiveCamera();
        cam.iniciarMuestreo();
        
        // Creamos el objeto de captura
        Capture capture = new Capture(cam);
        // Comenzamos la captura
        capture.start();
        // Instanciamos la salida
        Output out = new Output();
        out.setVisible(true);
        // Para el tratamiento de imagen
        BufferedImage bi;
        Image img = null;
        Histogram hist;
        int umbral;
        
        for(;;) {
            try {
                // Obtener muestra
                bi = capture.getBufferedImage();
                // Preprocesar
                Utilities.toGrayScales(bi);
                hist = new Histogram(bi);
                umbral = Thresholding.Isodata(hist.getHistogramG());
                Thresholding.simple(umbral, bi);
                // Procesar
                // Mostrar salida
                out.updateImage(bi);
                Thread.sleep(200);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        
        /*
        try {
            ImageIO.write(capture.getBufferedImage(), "PNG", new File("assets/test2.png"));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        */
    }
}

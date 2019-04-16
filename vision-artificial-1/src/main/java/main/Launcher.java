package main;

import java.awt.Image;
import java.awt.image.BufferedImage;
import preprocessing.Thresholding;
import processing.CenterMass;
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
        int ancho = cam.getWidth();
        int alto = cam.getHeight();
        int umbralAnterior = 63;
        
        while(true) {
            try {
                // Obtener muestra
                bi = capture.getBufferedImage();
                // Preprocesar
                Utilities.toGrayScales(bi, ancho, alto);
                hist = new Histogram(bi);
                umbral = Thresholding.Isodata(hist.getHistogramR());
                if(umbral == 0) umbral = umbralAnterior;
                System.out.print("");
                Thresholding.simple(umbral, bi, ancho, alto);
                // Procesar
                CenterMass.draw(bi, ancho, alto, "apple");
                //CenterMass.draw(bi, ancho, alto, "square");
                //CenterMass.draw(bi, ancho, alto, "heart");
                //CenterMass.draw(bi, ancho, alto, "target");
                // Mostrar salida
                out.updateImage(bi);
                // Imagen a 10 FPS
                Thread.sleep(100);
                if(umbral > 0) umbralAnterior = umbral;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

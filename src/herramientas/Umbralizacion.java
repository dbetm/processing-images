package herramientas;

import gui.JFrameImagen;
import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import muestreo.Histograma;

/**
 *
 * @author david
 */
public class Umbralizacion {
    public static Image umbralizacionSimple(int umbral, Image original) {
        BufferedImage bi = ImageManager.toBufferedImage(original);
        int ancho = original.getWidth(null);
        int alto = original.getHeight(null);
        // Recorremos el Buffer
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Color c = new Color(bi.getRGB(i, j));
                // Calculamos la reducción por promedio
                int prom = (c.getRed() + c.getGreen() + c.getRed()) / 3;
                if(prom > umbral) {
                    c = new Color(0, 0, 0);
                }
                else {
                    c = new Color(255, 255, 255);
                }
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(i, j, c.getRGB());
            }
        }
        
        return ImageManager.toImage(bi);
    }
    
    public static void main(String args[]) {
        // Abrimos la imagen
        Image original = ImageManager.openImage();
        // La original la mostramos en el frame
        JFrameImagen fi = new JFrameImagen(original);
        // El resultado lo mostramos en otro frame
        Image grises = EscalaGrises.generarImagenGrises(original);
        // Obtenemos los histogramas
        Histograma h = new Histograma(grises);
        h.graficarHistogramasRGB();
        // El resultado lo mostramos en otro frame
        Image resultado = Umbralizacion.umbralizacionSimple(75, grises);
        // Mostrar el resultado en otro frame
        JFrameImagen fo = new JFrameImagen(resultado);
    }
}

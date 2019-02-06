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
public class EscalaGrises {
    
    public static Image generarImagenGrises(Image original) {
        BufferedImage bi = ImageManager.toBufferedImage(original);
        int ancho = original.getWidth(null);
        int alto = original.getHeight(null);
        
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                // Extraer el tono del píxel en RGB
                Color c = new Color(bi.getRGB(i, j));
                // Calculamos la reducción por promedio
                int gris = (c.getRed() + c.getGreen() + c.getRed()) / 3;
                // Generamos el nuevo tono (en gris) con el valor rgb
                c = new Color(gris, gris, gris);
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
        Image resultado = EscalaGrises.generarImagenGrises(original);
        // Mostrar el resultado en otro frame
        JFrameImagen fo = new JFrameImagen(resultado);
        // Obtenemos los histogramas
        Histograma h = new Histograma(original);
        h.graficarHistogramasRGB();
        
        Histograma hres = new Histograma(resultado);
        hres.graficarHistogramasRGB();
    }
}

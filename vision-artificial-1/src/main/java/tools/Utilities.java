package tools;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public abstract class Utilities {
    // Pasar de BufferedImage a Image
    public static Image toImage (BufferedImage bi) {
        return bi.getScaledInstance(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
    }
    
    // To grayScales
    public static BufferedImage toGrayScales(BufferedImage bi) {
        int ancho = bi.getWidth(null);
        int alto = bi.getHeight(null);
        
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
        return bi;
    }
}

package herramientas;

import gui.JFrameImagen;
import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public class Negativo {
    
    public static Image pasarNegativo(Image original) {
        
        BufferedImage bi = ImageManager.toBufferedImage(original);
        int ancho = original.getWidth(null);
        int alto = original.getHeight(null);
        
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                // Extraer el tono del píxel en RGB
                Color c = new Color(bi.getRGB(i, j));
                // Generamos el nuevo tono, negativo del actual
                c = new Color(255 - c.getRed(), 255 - c.getGreen(), 255- c.getBlue());
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
        Image resultado = Negativo.pasarNegativo(original);
        // Mostrar el resultado en otro frame
        JFrameImagen fo = new JFrameImagen(resultado);
    }
}

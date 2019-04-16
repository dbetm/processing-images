package processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author david
 */
public class CenterMass {
    public static void draw(BufferedImage bi, int ancho, int alto, String tipo) {
        // contar el número de píxeles oscuros
        int negros = 0;
        // suma acumulada en la coordenada x
        int sumaX = 0;
        // suma acumulada en la coordenada y
        int sumaY = 0;
        
        // procesar
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Color c = new Color(bi.getRGB(i, j));
                if(c.getRed() == 0) {
                    negros++;
                    sumaX += i;
                    sumaY += j;
                }
            }
        }
        
        // obtener coordenada promedio
        if(negros == 0) negros = 1;
        int promX = sumaX / negros;
        int promY = sumaY / negros;
        try {
            if(tipo.equals("square")) {
                // dibujar centro color verde 10x10, cuidano no salirse de los límites
                putSimpleSquare(bi, ancho, alto, promX, promY);
            }
            else if(tipo.equals("apple")) {
                // dibujar una manzana
                putRedApple(bi, ancho, alto, promX, promY);
            }
            else if(tipo.equals("heart")) {
                // dibujar un corazón
                putHeart(bi, ancho, alto, promX, promY);
            }
            else if(tipo.equals("target")) {
                // dibujar un corazón
                putTarget(bi, ancho, alto, promX, promY);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Draw a (10x10) green square 
    private static void putSimpleSquare(BufferedImage bi, int ancho, int alto, 
        int promX, int promY) {
        Color c = new Color(75, 187, 14);
        for (int i = promX-5; i <= promX+5; i++) {
            for (int j = promY-5; j < promY+5; j++) {
                if(i < 0 || j < 0 || i >= ancho || j >= alto) continue;
                bi.setRGB(i, j, c.getRGB());
            }
        }
    }
    
    private static void putRedApple(BufferedImage bi, int ancho, int alto, 
        int promX, int promY) throws IOException {
        Color c = new Color(75, 187, 14);
        // open the image
        File file = new File("assets/apple.png");
        BufferedImage img = ImageIO.read(file);
        
        for (int i = promX-12, x = 0; i < promX+12; i++, x++) {
            for (int j = promY-12, y = 0; j < promY+12; j++, y++) {
                if(i < 0 || j < 0 || i >= ancho || j >= alto) continue;
                int pixel = img.getRGB(x, y);
                if(!isTransparent(pixel)) bi.setRGB(i, j, pixel);
            }
        }
    }
    
    private static void putHeart(BufferedImage bi, int ancho, int alto, 
        int promX, int promY) throws IOException {
        Color c = new Color(75, 187, 14);
        // open the image
        File file = new File("assets/heart.png");
        BufferedImage img = ImageIO.read(file);
        
        for (int i = promX-12, x = 0; i < promX+12; i++, x++) {
            for (int j = promY-12, y = 0; j < promY+12; j++, y++) {
                if(i < 0 || j < 0 || i >= ancho || j >= alto) continue;
                int pixel = img.getRGB(x, y);
                if(!isTransparent(pixel)) bi.setRGB(i, j, pixel);
            }
        }
    }
    
    private static void putTarget(BufferedImage bi, int ancho, int alto, 
        int promX, int promY) throws IOException {
        Color c = new Color(75, 187, 14);
        // open the image
        File file = new File("assets/target.png");
        BufferedImage img = ImageIO.read(file);
        
        for (int i = promX-12, x = 0; i < promX+12; i++, x++) {
            for (int j = promY-12, y = 0; j < promY+12; j++, y++) {
                if(i < 0 || j < 0 || i >= ancho || j >= alto) continue;
                int pixel = img.getRGB(x, y);
                if(!isTransparent(pixel)) bi.setRGB(i, j, pixel);
            }
        }
    }
    
    public static boolean isTransparent(int pixel) {
        boolean ans = false;
        if( (pixel>>24) == 0x00 ) {
            ans = true;
        }
        return ans;
    }
}

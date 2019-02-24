package herramientas;

import gui.JFrameImagen;
import io.ImageManager;
import static io.ImageManager.saveImage;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import muestreo.Histograma;

/**
 *
 * @author david
 */
public class Traslacion {
    
    public static Image cambiarIluminacion(Image img, int c) {
        BufferedImage bi = ImageManager.toBufferedImage(img);
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        if(c == 0) return ImageManager.toImage(bi);
        
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Color color = new Color(bi.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                if(c > 0) {
                   red = (red + c > 255)? 255 : red + c;
                   green = (green + c > 255)? 255 : green + c;  
                   blue = (blue + c > 255)? 255 : blue + c;  
                }
                else {
                   red = (red + c <= 0)? 0 : red + c;
                   green = (green + c <= 0)? 0 : green + c;  
                   blue = (blue + c <= 0)? 0 : blue + c;
                }
                color = new Color(red, green, blue);
                bi.setRGB(i, j, color.getRGB());
            }
        }
        return ImageManager.toImage(bi);
    }
    
    public static Image cambiarTemperatura(Image img, int c) {
        BufferedImage bi = ImageManager.toBufferedImage(img);
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        if(c == 0) return ImageManager.toImage(bi);
        
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Color color = new Color(bi.getRGB(i, j));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                
                if(c > 0) {
                   red = (red + c > 255)? 255 : red + c;  
                   blue = (blue - c <= 0)? 0 : blue - c;  
                }
                else {
                   red = (red + c <= 0)? 0 : red + c;  
                   blue = (blue - c > 255)? 255 : blue - c;
                }
                color = new Color(red, green, blue);
                bi.setRGB(i, j, color.getRGB());
            }
        }
        return ImageManager.toImage(bi);
    }
    
    public static void main(String args[]) throws IOException {
        Image original = ImageManager.openImage();
        //JFrameImagen fi = new JFrameImagen(original);
        //fi.setSize(600, 600);
        //fi.setTitle("Original");
        //Histograma h = new Histograma(original);
        //h.graficarHistogramasRGB();
        for (int i = -255; i <= 255; i += 2) {
            // Modificada
            Image res = Traslacion.cambiarTemperatura(original, i);
            //String path = "/home/david/Imágenes/0-image-processing/p4/temp_"+i;
            //saveImage(path, res, "png");
            String path = "/home/david/Imágenes/0-image-processing/p4/ilu_"+i;
            res = Traslacion.cambiarIluminacion(original, i);
            saveImage(path, res, "png");
            System.out.println(i);
        }
        /*
        // Modificada
        Image res = Traslacion.cambiarTemperatura(original, -90);
        JFrameImagen fo = new JFrameImagen(res);
        fo.setSize(600, 600);
        Histograma hisRes = new Histograma(res);
        hisRes.graficarHistogramasRGB();
        */
    }
}

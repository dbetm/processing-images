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
public class Expansion {
    /* j = [(i-a)/(b-a)]*(d-c) + c
       Llevar del intervalo F[a,b] al intervalo G[c,d], para cualquier i
       perteneciente al intervalor F, su equivalente 'j' en el intervalo G
       está dado por:
    */ 
    public static Image expansionLinealConRango(Image original, int c, int d) {
        BufferedImage bi = ImageManager.toBufferedImage(original);
        int ancho = original.getWidth(null);
        int alto = original.getHeight(null);
        // Recorremos el Buffer
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                Color color = new Color(bi.getRGB(x, y));
                // obtener el valor de luminosidad de cada canal
                double i1 = color.getRed();
                double i2 = color.getGreen();
                double i3 = color.getBlue();
                // Hacemos la transformación lineal sobre cada canal
                int j1 = (int) ((i1-0) / (255-0)*(d - c) + c);
                if(j1 > 255) j1 = 255;
                else if(j1 < 0) j1 = 0;
                int j2 = (int) ((i2-0) / (255-0)*(d - c) + c);
                if(j2 > 255) j2 = 255;
                else if(j2 < 0) j2 = 0;
                int j3 = (int) ((i3-0) / (255-0)*(d - c) + c);
                if(j3 > 255) j3 = 255;
                else if(j3 < 0) j3 = 0;
                color = new Color(j1, j2, j3);
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(x, y, color.getRGB());
            }
        }
        
        return ImageManager.toImage(bi);
    }
    
    public static Image expansionLineal(int min, int max, Image img) {
        BufferedImage bi = ImageManager.toBufferedImage(img);
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        
        // Recorremos el Buffer
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                Color color = new Color(bi.getRGB(x, y));
                // obtener el valor de luminosidad de cada canal
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                int rojo = (int) validarRangoRGB(((255/(max - min))*(red - min)));
                int verde = (int) validarRangoRGB((255/(max - min))*(green- min));
                int azul = (int) validarRangoRGB((255/(max - min))*(blue - min));
                
                color = new Color(rojo, verde, azul);
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(x, y, color.getRGB());
            }
        }
        
        return ImageManager.toImage(bi);
    }
    
    public static Image centrar(int min, int max, Image img) {
        BufferedImage bi = ImageManager.toBufferedImage(img);
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        int dif = max - min;
        int mitad = dif / 2;
        
        // Recorremos el Buffer
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                Color color = new Color(bi.getRGB(x, y));
                int rojo = color.getRed();
                int verde = color.getGreen();
                int azul = color.getBlue();
                // Procesamos el canal rojo
                if(rojo < mitad) rojo = centrarColor(rojo, mitad, min, 'l');
                if(rojo > mitad) rojo = centrarColor(rojo, mitad, min, 'r');
                else rojo = centrarColor(rojo, mitad, min, 'c');
                // Ahora el verde
                if(verde < mitad) verde = centrarColor(verde, mitad, min, 'l');
                if(verde > mitad) verde = centrarColor(verde, mitad, min, 'r');
                else verde = centrarColor(verde, mitad, min, 'c');
                // Y finalmente el azul
                if(azul < mitad) azul = centrarColor(azul, mitad, min, 'l');
                if(azul > mitad) azul = centrarColor(azul, mitad, min, 'r');
                else azul = centrarColor(azul, mitad, min, 'c');
                
                color = new Color(rojo, verde, azul);
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(x, y, color.getRGB());
            }
        }
        return ImageManager.toImage(bi);
    }
    
    private static int centrarColor(int color, int mitad, int min, char c) {
        if(c == 'l') color = 127 - mitad - color; 
        else if(c == 'r') color = 127 - mitad + color - min; 
        else color = 127;
        if(color < 0) color = 0;
        else if(color > 255) color = 255;
        return color;
    }
    
    public static int calcularMinimo(int histograma[]) {
        // Recorrer el histograma de izquierda a derecha
        for (int i = 0; i < histograma.length; i++) {
            if(histograma[i] != 0) return i;
        }
        
        return -1;
    }
    
    public static int calcularMaximo(int histograma[]) {
        // Recorrer el histograma de derecha a izquierda
        for (int i = histograma.length - 1; i >= 0; i--) {
            if(histograma[i] != 0) return i;
        }
        
        return -1;
    }
    
    public static double validarRangoRGB(double v) {
        if(v > 255) return 255;
        else if(v < 0) return 0;
        else return v;
    }
    
    public static Image expansionLogaritmica(Image img, int j) {
        BufferedImage bi = ImageManager.toBufferedImage(img);
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        
        // Recorremos el Buffer
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                Color color = new Color(bi.getRGB(x, y));
                // obtener el valor de luminosidad de cada canal
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                int rojo = (int) validarRangoRGB(255*Math.log(j+red) / Math.log(256));
                int verde = (int) validarRangoRGB(255*Math.log(j+green) / Math.log(256));
                int azul = (int) validarRangoRGB(255*Math.log(j+blue) / Math.log(256));
                
                color = new Color(rojo, verde, azul);
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(x, y, color.getRGB());
            }
        }
        
        return ImageManager.toImage(bi);
    }
    
    public static Image expansionExponencial(Image img, double z) {
        BufferedImage bi = ImageManager.toBufferedImage(img);
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        
        // Recorremos el Buffer
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                Color color = new Color(bi.getRGB(x, y));
                // obtener el valor de luminosidad de cada canal
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                int rojo = (int) validarRangoRGB(Math.pow(z+1, red) / z);
                int verde = (int) validarRangoRGB(Math.pow(z+1, green) / z);
                int azul = (int) validarRangoRGB(Math.pow(z+1, blue) / z);
                
                color = new Color(rojo, verde, azul);
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(x, y, color.getRGB());
            }
        }
        
        return ImageManager.toImage(bi);
    }
    
    public static Image expansionDavid(Image img, int j) {
        BufferedImage bi = ImageManager.toBufferedImage(img);
        int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        
        // Recorremos el Buffer
        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < alto; y++) {
                Color color = new Color(bi.getRGB(x, y));
                // obtener el valor de luminosidad de cada canal
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                int rojo = (int) validarRangoRGB(
                    ((255*Math.log(256)) / (Math.log(j+red))) - 255);
                int verde = (int) validarRangoRGB(
                    ((255*Math.log(256)) / (Math.log(j+green))) - 255);
                int azul = (int) validarRangoRGB(
                    ((255*Math.log(256)) / (Math.log(j+blue))) - 255);
                
                color = new Color(rojo, verde, azul);
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(x, y, color.getRGB());
            }
        }
        
        return ImageManager.toImage(bi);
    }
    
    public static void main(String args[]) {
        Image original = ImageManager.openImage();
        JFrameImagen fi = new JFrameImagen(original);
        // Mostramos los histogramas
        Histograma h = new Histograma(original);
        h.graficarHistogramasRGB();
        /*
        // Procesamos, expansión lineal con rango
        Image res = Expansion.expansionLinealConRango(original, 103, 152);
        // Mostramos el resultado
        JFrameImagen fo = new JFrameImagen(res);
        // Y el histograma
        // Mostramos los histogramas
        Histograma his = new Histograma(res);
        his.graficarHistogramasRGB();
        */
        
        
        // Procesamos expansion lineal automática
        int min = Math.min(Math.min(calcularMinimo(h.getHistogramaR()), 
            calcularMinimo(h.getHistogramaG())), calcularMinimo(h.getHistogramaB()));
        
        int max = Math.max(Math.max(calcularMaximo(h.getHistogramaR()), 
            calcularMaximo(h.getHistogramaG())), calcularMaximo(h.getHistogramaB()));
        
        // Centrar
        original = Expansion.centrar(min, max, original);
        h = new Histograma(original);
        h.graficarHistogramasRGB();
        
        Image res2 = Expansion.expansionLineal(min, max, original);
        // Mostramos el resultado
        JFrameImagen fo2 = new JFrameImagen(res2);
        
        // Mostramos los histogramas
        Histograma his2 = new Histograma(res2);
        his2.graficarHistogramasRGB();
        
        
        /*
        // Expansión logarítmica
        Image res = Expansion.expansionLogaritmica(original);
        // Mostramos el resultado
        JFrameImagen fos = new JFrameImagen(res);
        // Y el histograma
        // Mostramos los histogramas
        Histograma his = new Histograma(res);
        his.graficarHistogramasRGB();
        */
        
        /*
        // Expansión exponencial
        Image res = Expansion.expansionExponencial(original, 4);
        // Mostramos el resultado
        JFrameImagen fos = new JFrameImagen(res);
        // Y el histograma
        // Mostramos los histogramas
        Histograma his = new Histograma(res);
        his.graficarHistogramasRGB();
        */
        
        
        // Expansión propuesta por mi
        /*
        // Hace un negativo con un contraste más alto
        Image res = Expansion.expansionDavid(original, 1);
        // Mostramos el resultado
        JFrameImagen fos = new JFrameImagen(res);
        // Y el histograma
        // Mostramos los histogramas
        Histograma his = new Histograma(res);
        his.graficarHistogramasRGB();
        */
    }
}

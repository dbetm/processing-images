package herramientas;

import gui.JFrameImagen;
import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
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
                if(prom < umbral) {
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
    
    // Algoritmo ISODATA
    public static int calcularUmbralISODATA(int histograma[]) {
        // Calculamos un umbral inicial aleatorio
        int nuevoUmbral = new Random().nextInt(256);
        
        // Iteramos hasta que el cambio del umbral sea 0
        int umbral;
        do {
            // Actualización
            umbral = nuevoUmbral;
            // Calculamos el nuevo umbral
            nuevoUmbral = reajustarUmbral(umbral, histograma);
            System.out.println(nuevoUmbral);
        } while(umbral != nuevoUmbral);
        
        return umbral;
    }
    
    private static int reajustarUmbral(int umbral, int histograma[]) {
        // Acumuladores
        int productoFrec = 0, frecuencias = 0;
        // Calculamos el lado izquierdo
        productoFrec += histograma[0];
        frecuencias += histograma[0];
        for (int i = 1; i < umbral; i++) {
            productoFrec += histograma[i] * i;
            frecuencias += histograma[i];
        }
        if(frecuencias == 0) return 0;
        int u1 = productoFrec / frecuencias;
        
        // Calculamos el lado derecho
        productoFrec = 0;
        frecuencias = 0;
        
        for (int i = umbral; i < histograma.length; i++) {
            productoFrec += histograma[i] * i;
            frecuencias += histograma[i];
        }
        if(frecuencias == 0) return 0;
        int u2 = productoFrec / frecuencias;
        
        return (int)((u1 + u2) / 2);
    }
    
    public static int calcularUmbralOtsu(int[] hist, int m, int n) {
        int umbral = 125;
        int len = hist.length;
        double sumasAcumulativas[] = new double[len];
        double mediasAcumulativas[] = new double[len];
        double mediaGlobal;
        double pi = hist[0] / (m * n);
        sumasAcumulativas[0] = pi;
        mediasAcumulativas[0] = 0;
        
        for (int i = 1; i < len; i++) {
            pi = hist[i] / (m * n);
            sumasAcumulativas[i] = sumasAcumulativas[i-1] + pi;
            System.out.println("pi: " + pi);
            System.out.println("");
            mediasAcumulativas[i] = sumasAcumulativas[i] * i;
            // System.out.println("");
        }
        System.out.println("");
        mediaGlobal = mediasAcumulativas[len-1];
        
        // Ahora se busca la varianza máxima
        
        // Hipótesis
        double varianzaMax = (mediaGlobal*sumasAcumulativas[0] - mediasAcumulativas[0]);
        varianzaMax *= varianzaMax;
        varianzaMax /= (sumasAcumulativas[0]*(1 - sumasAcumulativas[0]));
        ArrayList<Integer> k = new ArrayList<>();
        k.add(0);
        
        for (int i = 1; i < len; i++) {
            double var = (mediaGlobal*sumasAcumulativas[i] - mediasAcumulativas[i]);
            var *= varianzaMax;
            var /= (sumasAcumulativas[i]*(1 - sumasAcumulativas[i]));
            if(var == varianzaMax) k.add(i);
            else if(var > varianzaMax) {
                k.clear();
                k.add(i);
                varianzaMax = var;
            }
        }
        umbral = obtenerPromedio(k);
        System.out.println(umbral);
        return umbral;
    }
    
    private static int obtenerPromedio(ArrayList<Integer> k) {
        int sum = 0;
        for (int i = 0; i < k.size(); i++) sum += k.get(i);
        return sum/k.size();
    }
    
    public static void main(String args[]) {
        Image original = ImageManager.openImage();
        JFrameImagen fi = new JFrameImagen(original);
        // Creamos la escala de grises
        Image grises = EscalaGrises.generarImagenGrises(original);
        // Obtenemos los histogramas
        Histograma h = new Histograma(grises);
        h.graficarHistogramasRGB();
        // Calcular el umbral
        int umbral = calcularUmbralISODATA(h.getHistogramaR());
        //int umbral = calcularUmbralOtsu(h.getHistogramaR(), grises.getHeight(null), 
        //    grises.getWidth(fi));
        // El resultado lo mostramos en otro frame
        Image resultado = Umbralizacion.umbralizacionSimple(umbral, original);
        // Mostrar el resultado en otro frame
        JFrameImagen fo = new JFrameImagen(resultado);
        
        /*
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
        */
    }
}

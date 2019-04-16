package preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author david
 */
public class Thresholding {
    
    public static void simple(int umbral, BufferedImage bi, int ancho, int alto) {
        // Recorremos el Buffer
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Color c = new Color(bi.getRGB(i, j));
                // Calculamos la reducción por promedio
                int prom = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
                if(prom < umbral) c = new Color(0, 0, 0);
                else c = new Color(255, 255, 255);
                // Seteamos el pixel con base al nuevo tono generado
                bi.setRGB(i, j, c.getRGB());
            }
        }
    }
    
//    // Algoritmo ISODATA
//    public static int calcularUmbralISODATA(int histogram[]) {
//        // Calculamos un umbral inicial aleatorio
//        int nuevoUmbral = new Random().nextInt(256);
//        
//        // Iteramos hasta que el cambio del umbral sea 0
//        int umbral;
//        do {
//            // Actualización
//            umbral = nuevoUmbral;
//            // Calculamos el nuevo umbral
//            nuevoUmbral = readjust(umbral, histogram);
//        } while(umbral != nuevoUmbral);
//        
//        return umbral;
//    }
    
    public static int Isodata(int histograma[]){
        // calculamos el umbral inicial
        Random ran = new Random();
        int uR = ran.nextInt(255);
        int uA;
        // hacemos el proceso iterativo de reajuste dl umbral inicial
        do  {
            uA = uR;    
            uR = readjust(uA,histograma);
        } while(uR != uA);
    
        return uR;
    }
    /*
    private static int readjust(int umbral, int histograma[]) {
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
    */
    private static int readjust(int u, int[] histograma) {
       int aFi=0, aPi=0;
       int aFd=0, aPd=0;
       aPi += histograma[0];
       for(int x = 1;x < u; x++) {
            aPi += histograma[x] * x;
            aFi += histograma[x];
       }
       for(int y = u;y < histograma.length; y++){
            aPd += histograma[y] * y;
            aFd += histograma[y];
       }
       if(aFi==0 || aFd==0) return 0;
       int uI = aPi / aFi;
       int uD = aPd / aFd;
       
       return (int)((uI + uD) / 2);
    }
}

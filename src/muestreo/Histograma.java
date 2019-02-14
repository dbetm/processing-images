package muestreo;
// Histograma de frecuencias

import gui.JFrameImagen;
import herramientas.Grafica;
import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public class Histograma {
    private Image imagen;
    private int histogramaR[];
    private int histogramaG[];
    private int histogramaB[];

    public Histograma(Image imagen) {
        this.imagen = imagen;
        this.histogramaR = new int[256];
        this.histogramaG = new int[256];
        this.histogramaB = new int[256];
        calcularHistogramas();
    }
    
    private void calcularHistogramas() {
        // Hacer recorrido de la imagen y contamos la frecuencia para cada tono
        BufferedImage bi = ImageManager.toBufferedImage(this.imagen);
        // Recorrer el buffered image
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                Color c = new Color(bi.getRGB(x, y));
                this.histogramaR[c.getRed()]++;
                this.histogramaG[c.getGreen()]++;
                this.histogramaB[c.getBlue()]++;
            }
        }
    }
    
    public void graficarHistogramasRGB() {
        Grafica grafica = new Grafica("Histogramas RGB", "Tono", "Frecuencia");
        grafica.agregarSerie("Rojo", this.histogramaR);
        grafica.agregarSerie("Azul", this.histogramaB);
        grafica.agregarSerie("Verde", this.histogramaG);
        grafica.crearGrafica();
    }
    
    public void graficarHistogramaRojo() {
        Grafica grafica = new Grafica("Histograma rojo", "Tono", "Frecuencia");
        grafica.agregarSerie("Rojo", this.histogramaR);
    }
    
    public void graficarHistogramaVerde() {
        Grafica grafica = new Grafica("Histograma verde", "Tono", "Frecuencia");
        grafica.agregarSerie("Verde", this.histogramaG);
    }
    
    public void graficarHistogramaAzul() {
        Grafica grafica = new Grafica("Histograma azul", "Tono", "Frecuencia");
        grafica.agregarSerie("Azul", this.histogramaB);
    }
    
    public static void main(String args[]) {
        Image img = ImageManager.openImage();
        JFrameImagen jfimage = new JFrameImagen(img);
        Histograma h = new Histograma(img);
        h.graficarHistogramasRGB();
    }

    public int[] getHistogramaR() {
        return histogramaR;
    }

    public int[] getHistogramaG() {
        return histogramaG;
    }

    public int[] getHistogramaB() {
        return histogramaB;
    }
}

package frecuencias;

import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public abstract class Filtro {
    private Complejo[][] matriz;
    
    public Filtro(int ancho, int alto) {
        this.matriz = new Complejo[ancho][alto];
    }
    
    public abstract void generar();
    
    public Image toImage() {
        this.generar(); 
        int ancho = matriz.length;
        int alto = matriz[0].length;
        // Declaramos el buffer
        BufferedImage bi = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        // Recorrer el buffered
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                int rgb = (int)this.matriz[i][j].getReal();
                //bi.setRGB(i, j, rgb);
                double valor = matriz[i][j].getReal() * 255;
                //System.out.println("Valor: " + valor);
                valor = HerramientasColor.validarRangoRGB((int) valor);
                bi.setRGB(i, j, new Color((int)valor,(int)valor,(int)valor).getRGB());
            }
        }
        return ImageManager.toImage(bi);
    }

    public Complejo[][] getMatriz() {
        return matriz;
    }
}

package practicas;

import gui.JFrameImagen;
import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public class P1PixelDarthVader {
    private int delta;
    private Color centro;
    
    public P1PixelDarthVader(int delta, Color centro) {
        this.delta = delta;
        this.centro = centro;
    }
    
    public Image pintar(Image original, Color nuevoColor) {
        int ancho = original.getWidth(null);
        int alto = original.getHeight(null);
        
        BufferedImage o = ImageManager.toBufferedImage(original);
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Color c = new Color(o.getRGB(i, j));
                if(esPosibleColorear(c)) {
                    o.setRGB(i, j, nuevoColor.getRGB());
                }
            }
        }
        return ImageManager.toImage(o);
    }
    
    private boolean esPosibleColorear(Color c) {
        // validar canal R
        int rojo = c.getRed();
        int rLow = this.centro.getRed() - this.delta;
        int rUp = this.centro.getRed() + this.delta;
        if(rojo < rLow || rojo > rUp) return false;
        
        // validar canal G
        int verde = c.getGreen();
        int vLow = this.centro.getGreen() - this.delta;
        int vUp = this.centro.getGreen() + this.delta;
        if(verde < vLow || verde > vUp) return false;
        
        // validar canal B
        int azul = c.getBlue();
        int bLow = this.centro.getBlue() - this.delta;
        int bUp = this.centro.getBlue() + this.delta;
        if(azul < bLow || azul > bUp) return false;
        
        return true;
    }
    
    public static void main(String args[]) {
        // Abrimos la imagen
        Image ejemplo = ImageManager.openImage();
        // La original la mostramos en el frame
        JFrameImagen fi = new JFrameImagen(ejemplo);
        // Instanciamos la clase para manipular la imagen
        // Primero definimos un delta
        int delta = 30;
        // Luego un centro RGB
        Color centro = new Color(83, 43, 36);
        P1PixelDarthVader p1 = new P1PixelDarthVader(delta, centro);
        // El resultado lo mostramos en otro frame
        Image resultado = p1.pintar(ejemplo, new Color(255, 255, 0));
        // Mostrar el resultado en otro frame
        JFrameImagen fo = new JFrameImagen(resultado);
    }
}

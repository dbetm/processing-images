package practicas;

import gui.JFrameImagen;
import io.ImageManager;
import static io.ImageManager.saveImage;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author david
 */
public class P2CambiarFondo {
    private int delta;
    private Color centro;

    public P2CambiarFondo(Color croma, int delta) {
        this.delta = delta;
        this.centro = croma; 
    }
    
    private Image procesar(Image fotomontaje, Image fondo) {
        int ancho = fotomontaje.getWidth(null);
        int alto = fotomontaje.getHeight(null);
        
        BufferedImage f = ImageManager.toBufferedImage(fondo);
        BufferedImage res = ImageManager.toBufferedImage(fotomontaje);
        
        // Construímos el fotomontaje, sustituyendo el fondo color croma con
        // el fondo deseado.
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                Color pix = new Color(res.getRGB(i, j));
                // Comprobamos si pix es color croma
                if(esPosibleColorear(pix)) {
                    // Tomamos el color del fondo y lo ponemos en el fotomontaje
                    res.setRGB(i, j, f.getRGB(i, j));
                }
            }
        }
        return ImageManager.toImage(res);
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
    
    public static void main(String args[]) throws IOException {
        // Abrimos la imagen controlada
        Image fotomontaje = ImageManager.openImage();
        // Abrimos el fondo 
        Image fondo = ImageManager.openImage();
        // Instanciamos la clase para manipular la imagen
        // Primero definimos un delta
        int delta = 115;
        // Luego un centro RGB
        Color centro = new Color(0, 15, 253);
        P2CambiarFondo cf1 = new P2CambiarFondo(centro, delta);
        // El resultado lo mostramos en otro frame
        Image resultado = cf1.procesar(fotomontaje, fondo);
        // Mostrar el resultado en otro frame
        JFrameImagen fo = new JFrameImagen(resultado);
        fo.setTitle("Resultado");
        // Guardar el resultado 
        String path = "src/salidas/p2/dinosaurio";
        if(saveImage(path, resultado, "png")) { 
            System.out.println("Imagen guardada correctamente");
        }
        else {
            System.out.println("Ocurrió un error al escribir la imagen");
        }
    }
    
}

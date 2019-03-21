package practicas;

import gui.JFrameImagen;
import io.ImageManager;
import java.awt.Image;

/**
 *
 * @author david
 */
public class P5KirschConv {
    // Máscaras para bordes
    
    public static void main(String args[]) {
        //  Kirsch
        double[][] kirsch1 = {{-3, -3, 5}, {-3, 0, 5}, {-3, -3, 5}};
        double[][] kirsch2 = {{-3, 5, 5}, {-3, 0, 5}, {-3, -3, -3}};
        double[][] kirsch3 = {{5, 5, 5}, {-3, 0, -3}, {-3, -3, -3}};
        double[][] kirsch4 = {{5, 5, -3}, {5, 0, -3}, {-3, -3, -3}};
        double[][] kirsch5 = {{5, -3, -3}, {5, 0, -3}, {5, -3, -3}};
        double[][] kirsch6 = {{-3, -3, -3}, {5, 0, -3}, {5, 5, -3}};
        double[][] kirsch7 = {{-3, -3, -3}, {-3, 0, -3}, {5, 5, 5}};
        double[][] kirsch8 = {{-3, -3, -3}, {-3, 0, 5}, {-3, 5, 5}};
        // Creamos el arreglo de kernels
        double[][][] arregloMascaras = {kirsch1, kirsch2, kirsch3, kirsch4, 
            kirsch5, kirsch6, kirsch7, kirsch8};
        // Abrimos la imagen
        Image original = ImageManager.openImage();
        // La mostramos
        JFrameImagen origin = new JFrameImagen(original);
        // Aplicamos la convolución
        Image res = herramientas.Convolucion.convolucionar(original, arregloMascaras, 1.0);
        // Mostramos el resultado
        JFrameImagen fRepujado = new JFrameImagen(res);
        fRepujado.setTitle("Detección de bordes con Kirch");
    }
    
}

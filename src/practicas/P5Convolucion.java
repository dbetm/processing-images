package practicas;

import gui.JFrameImagen;
import herramientas.Convolucion;
import io.ImageManager;
import static io.ImageManager.saveImage;
import java.awt.Image;
import java.io.IOException;

/**
 *
 * @author david
 */
public class P5Convolucion {
    // Máscaras para bordes
    
    public static void main(String args[]) throws IOException {
        String base = "/home/david/Dropbox/UPIIZ/6 - Opt Image analysis/Práctica convolución/test/car";
        // Abrimos la imagen
        Image original = ImageManager.openImage();
        // La mostramos
        //JFrameImagen origin = new JFrameImagen(original);
        
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
        double[][][] mascaras1 = {kirsch1, kirsch2, kirsch3, kirsch4, 
            kirsch5, kirsch6, kirsch7, kirsch8};
        // Aplicamos la convolución con Kirsch
        Image res1 = herramientas.Convolucion.convolucionar(original, mascaras1, 1.0);
        String path = base + "Kirsch";
        saveImage(path, res1, "png");
        // Mostramos el resultado
        //JFrameImagen frame1 = new JFrameImagen(res1);
        //frame1.setTitle("Kirsch");
        
        // Diferencia de Píxeles Gx
        double[][] diferenciaPixelesGx = {{0.0, 0.0, 0.0}, {0.0, 1.0, -1.0}, {0.0, 0.0, 0.0}};
        Image res2 = herramientas.Convolucion.convolucionar(original, diferenciaPixelesGx, 1.0);
        path = base + "diferenciaPixelesGx";
        saveImage(path, res2, "png");
        
        // Diferencia de Píxeles Gy
        double[][] diferenciaPixelesGy = {{0.0, -1.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 0.0}};
        Image res3 = herramientas.Convolucion.convolucionar(original, diferenciaPixelesGy, 1.0);
        path = base + "diferenciaPixelesGy";
        saveImage(path, res3, "png");
        
        // Diferencias de píxeles separados Gx
        double[][] diferenciaPixelesSeparadosGx = {{0.0, 0.0, 0.0}, {1.0, 0.0, -1.0}, 
            {0.0, 0.0, 0.0}};
        Image res4 = herramientas.Convolucion.convolucionar(original, diferenciaPixelesSeparadosGx, 1.0);
        path = base + "diferenciaPixelesSeparadosGx";
        saveImage(path, res4, "png");
        
        // Diferencias de píxeles separados Gy
        double[][] diferenciaPixelesSeparadosGy = {{0.0, -1.0, 0.0}, {0.0, 0.0, 0.0}, 
            {0.0, 1.0, 0.0}};
        Image res5 = herramientas.Convolucion.convolucionar(original, diferenciaPixelesSeparadosGy, 1.0);
        path = base + "diferenciaPixelesSeparadosGy";
        saveImage(path, res5, "png");
        
        // Máscara de operador prewitt Gx
        double[][] prewittGx = {{1.0, 0.0, -1.0}, {1.0, 0.0, -1.0}, {1.0, 0.0, -1.0}};
        Image res6 = herramientas.Convolucion.convolucionar(original, prewittGx, 1.0);
        path = base + "prewittGx";
        saveImage(path, res6, "png");
        
        // Máscara de operador prewittGy
        double[][] prewittGy = {{-1.0, -1.0, -1.0}, {0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}};
        Image res7 = herramientas.Convolucion.convolucionar(original, prewittGy, 1.0);
        path = base + "prewittGy";
        saveImage(path, res7, "png");
        
        // Máscara de operador SobelGx
        double[][] SobelGx = {{1.0, 0.0, -1.0}, {2.0, 0.0, -2.0}, {1.0, 0.0, -1.0}};
        Image res8 = herramientas.Convolucion.convolucionar(original, SobelGx, 1.0);
        path = base + "SobelGx";
        saveImage(path, res8, "png");
        
        // Máscara de operador SobelGy
        double[][] SobelGy = {{-1.0, -2.0, -1.0}, {0.0, 0.0, 0.0}, {1.0, 2.0, 1.0}};
        Image res9 = herramientas.Convolucion.convolucionar(original, SobelGy, 1.0);
        path = base + "SobelGy";
        saveImage(path, res9, "png");
        
        // Máscara de operador RobertsGx
        double[][] robertsGx = {{0.0, 0.0, -1.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 0.0}};
        Image res10 = herramientas.Convolucion.convolucionar(original, robertsGx, 1.0);
        path = base + "RobertsGx";
        saveImage(path, res10, "png");
        
        // Máscara de operador RobertsGy
        double[][] robertsGy = {{-1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 0.0}};
        Image res11 = herramientas.Convolucion.convolucionar(original, robertsGy, 1.0);
        path = base + "RobertsGy";
        saveImage(path, res11, "png");
        
        // creamos mascara de Laplace
        double[][] laplace = {{0.0, 1.0, 0.0}, {1.0, -4.0, 1.0}, {0.0, 1.0, 0.0}};
        Image res12 = Convolucion.convolucionar(original, laplace, 1);
        path = base + "laplace";
        saveImage(path, res12, "png");
        
        /*
        // Diferencias en píxeles
        double[][] diferenciaPixelesGx = {{0.0, 0.0, 0.0}, {0.0, 1.0, -1.0}, {0.0, 0.0, 0.0}};
        double[][] diferenciaPixelesGy = {{0.0, -1.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 0.0}};
        // Creamos el arreglo de éstas últimas máscaras
        double [][][] mascaras2 = {diferenciaPixelesGx, diferenciaPixelesGy};
        // Aplicamos la convolución con diferencia de píxeles
        Image res2 = herramientas.Convolucion.convolucionar(original, mascaras2, 1.0);
        // Mostramos el resultado
        JFrameImagen frame2 = new JFrameImagen(res2);
        frame2.setTitle("Diferencia de píxeles");
        
        // Diferencias de píxeles separados
        double[][] diferenciaPixelesSeparadosGx = {{0.0, 0.0, 0.0}, {1.0, 0.0, -1.0}, 
            {0.0, 0.0, 0.0}};
        double[][] diferenciaPixelesSeparadosGy = {{0.0, -1.0, 0.0}, {0.0, 0.0, 0.0}, 
            {0.0, 1.0, 0.0}};
        // Creamos el arreglo de éstas últimas máscaras
        double [][][] mascaras3 = {diferenciaPixelesSeparadosGx, diferenciaPixelesSeparadosGy};
        // Aplicamos la convolución con diferencia de píxeles
        Image res3 = herramientas.Convolucion.convolucionar(original, mascaras3, 1.0);
        // Mostramos el resultado
        JFrameImagen frame3 = new JFrameImagen(res3);
        frame3.setTitle("Diferencia de píxeles separados");
        
        // máscara de operador prewitt
        double[][] prewittGx = {{1.0, 0.0, -1.0}, {1.0, 0.0, -1.0}, {1.0, 0.0, -1.0}};
        double[][] prewittGy = {{-1.0, -1.0, -1.0}, {0.0, 0.0, 0.0}, {1.0, 1.0, 1.0}};
        // Creamos el arreglo de éstas últimas máscaras
        double [][][] mascaras4 = {prewittGx, prewittGy};
        // Aplicamos la convolución con diferencia de píxeles
        Image res4 = herramientas.Convolucion.convolucionar(original, mascaras4, 1.0);
        // Mostramos el resultado
        JFrameImagen frame4 = new JFrameImagen(res4);
        frame4.setTitle("Operador Prewitt");
        
        // Máscara de operador Sobel
        double[][] SobelGx = {{1.0, 0.0, -1.0}, {2.0, 0.0, -2.0}, {1.0, 0.0, -1.0}};
        double[][] SobelGy = {{-1.0, -2.0, -1.0}, {0.0, 0.0, 0.0}, {1.0, 2.0, 1.0}};
        // Creamos el arreglo de éstas últimas máscaras
        double [][][] mascaras5 = {SobelGx, SobelGy};
        // Aplicamos la convolución con diferencia de píxeles
        Image res5 = herramientas.Convolucion.convolucionar(original, mascaras5, 1.0);
        // Mostramos el resultado
        JFrameImagen frame5 = new JFrameImagen(res5);
        frame5.setTitle("Operador sobel");
        
        // Máscara de operador Roberts
        double[][] robertsGx = {{0.0, 0.0, -1.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 0.0}};
        double[][] robertsGy = {{-1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 0.0}};
        // Creamos el arreglo de éstas últimas máscaras
        double [][][] mascaras6 = {robertsGx, robertsGy};
        // Aplicamos la convolución con diferencia de píxeles
        Image res6 = herramientas.Convolucion.convolucionar(original, mascaras6, 1.0);
        // Mostramos el resultado
        JFrameImagen frame6 = new JFrameImagen(res6);
        frame6.setTitle("Operador Roberts");
        
        // creamos mascara de Laplace
        double[][] laplace = {{0.0, 1.0, 0.0}, {1.0, -4.0, 1.0}, {0.0, 1.0, 0.0}};
        Image res7 = Convolucion.convolucionar(original, laplace, 1);
        JFrameImagen frame7 = new JFrameImagen(res7);
        frame7.setTitle("Laplace");
        */
    }
    
}

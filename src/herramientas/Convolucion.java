package herramientas;

import gui.JFrameImagen;
import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public class Convolucion {
    
    public static Image convolucionar(Image original, double kernels[][][], 
        double divisor) {
        int ancho = original.getWidth(null);
        int alto = original.getHeight(null);
        int numKernels = kernels.length;
        int tamKernel = kernels[0].length;
        BufferedImage bi = ImageManager.toBufferedImage(original);
        BufferedImage nueva = new BufferedImage(original.getWidth(null), 
            original.getHeight(null), BufferedImage.TYPE_INT_RGB);
        
        // Proceso iterativo para generar una imagen nueva
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                // Extraemos la muestra para el píxel actual
                double muestra[][] = extraerMuestra(i, j, bi, tamKernel);
                if(muestra != null) {
                    Color colorRes = new Color(0, 0, 0);
                    Color aux;
                    // Probamos cada uno de los kernels
                    for (int k = 0; k < numKernels; k++) {
                        aux = aplicarKernel(kernels[k], muestra, divisor);
                        // Obtenemos el mayor
                        if(aux.getRGB() > colorRes.getRGB()) {
                            colorRes = aux;
                        }
                    }
                    // seteamos el color máximo obtenido de la convolución
                    nueva.setRGB(i, j, colorRes.getRGB());
                }
                else {
                    // si la muestra es nula, entonces seteamos el mismo color
                    // de la imagen original
                    nueva.setRGB(i, j, bi.getRGB(i, j));
                }
            }
        }
        
        return ImageManager.toImage(nueva);
    }
    
    public static double[][] extraerMuestra(int x, int y, BufferedImage bi, int n) {
        double matriz[][] = new double[n][n];
        int xx = 0;
        int yy = 0;
        try {
            // recorremos la imagen
            for(int i = x-(n-1)/2; i <= x+(n-1)/2; i++) {
                for(int j = y-(n-1)/2; j <= y+(n-1)/2; j++){
                    matriz[xx][yy] = bi.getRGB(i,j);
                    yy++;
                }
                yy=0;
                xx++;
            }  
            return matriz;
        }
        catch (Exception e) {
            // System.out.println("Indice no valido");
            return null;
        }
    }
    
    public static Color aplicarKernel(double[][] kernel, double[][] muestra, double divisor) {
        double acumuladorR = 0;
        double acumuladorG = 0;
        double acumuladorB = 0;
        Color aux;
        // recorremos el kernel y la muestra
        for(int x = 0; x < kernel[0].length; x++) {
            for(int y = 0; y < kernel[0].length; y++) {
                aux = new Color((int)muestra[x][y]);
                acumuladorR += (kernel[x][y] * aux.getRed());
                acumuladorG += (kernel[x][y] * aux.getGreen());
                acumuladorB += (kernel[x][y] * aux.getBlue());
            }
        }
        acumuladorR /= divisor;
        acumuladorG /= divisor;
        acumuladorB /= divisor;

        return new Color((int)validarRangoRGB(acumuladorR), 
            (int)validarRangoRGB(acumuladorG), (int)validarRangoRGB(acumuladorB));
    }
    
    public static Image convolucionar(Image imagenOriginal, double [][] kernel, double fD){
        BufferedImage bi = ImageManager.toBufferedImage(imagenOriginal);
        BufferedImage nueva = new BufferedImage(bi.getWidth(),bi.getHeight(),BufferedImage.TYPE_INT_RGB);
        int n = kernel.length;
        int t = (int) Math.floor(n/2);
        Color color;
        // recorrer la imagen para generar la nueva 
        for (int i = 0; i < bi.getWidth(); i++) {
            for(int j = 0; j < bi.getHeight(); j++) {
                // Acumuladores para cada canal
                double aR = 0, aG = 0, aB = 0;
                int contExcepciones = 0;
                for (int x = 0; x < n; x++) {
                    for (int y = 0; y < n; y++) {
                        try {
                            color = new Color(bi.getRGB(x-t+i, y-t+j));
                            aR += kernel[x][y] * color.getRed();
                            aG += kernel[x][y] * color.getGreen();
                            aB += kernel[x][y] * color.getBlue();
                        }
                        catch (Exception e) {
                            System.out.println("Hubo excepción");
                            contExcepciones++;
                        }
                    }
                }
                // Aplicamos el factor de división
                aR /= fD;
                aG /= fD;
                aB /= fD;
                
                // Con normalización
                
//                if(contExcepciones != 0) {
//                    aR /= contExcepciones;
//                    aG /= contExcepciones;
//                    aB /= contExcepciones;
//                }
                
                // Construímos el nuevo color
                color = new Color((int)validarRangoRGB(aR),(int)validarRangoRGB(aG),
                    (int)validarRangoRGB(aB));
                // seteamos en el buffer nuevo
                nueva.setRGB(i, j, color.getRGB());
            }
        }
        return ImageManager.toImage(nueva);
    }
     
    public static double validarRangoRGB(double v) {
        if(v > 255) return 255;
        else if(v < 0) return 0;
        else return v;
    }
    
    public static void main(String args[]) {
        Image original = ImageManager.openImage();
        // Aplicación de algunos filtros
        double repujado[][] = new double[][]{{-2,-1,0},{-1,1,1},{0,1,2}};
        Image repujadoRes = Convolucion.convolucionar(original, repujado, 1);
        JFrameImagen fRepujado = new JFrameImagen(repujadoRes);
        fRepujado.setTitle("Repujado");
        
        // Simple blox blur
        double blur[][] = new double[][]{{0.11,0.11,0.11},{0.11,0.11,0.11},{0.11,0.11,0.11}};
        Image blurRes = Convolucion.convolucionar(original, blur, 1);
        JFrameImagen fBlur = new JFrameImagen(blurRes);
        fBlur.setTitle("Blur");
        
        // Gaussian blur
        double gaussianBlur[][] = new double[][]{
            {0,0,0,5,0,0,0},
            {0,5,18,32,18,5,0},
            {0,18,64,100,64,18,0},
            {5,32,100,100,100,32,5},
            {0,18,64,100,64,18,0},
            {0,5,18,32,18,5,0},
            {0,0,0,5,0,0,0},
        };
        Image gaussianBlurRes = Convolucion.convolucionar(original, gaussianBlur, 1);
        JFrameImagen fgaussianBlur = new JFrameImagen(gaussianBlurRes);
        fgaussianBlur.setTitle("Gussian Blur");
        
        // Edge detection
        double edge[][] = new double[][]{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
        Image edgeRes = Convolucion.convolucionar(original, edge, 1);
        JFrameImagen fEdge = new JFrameImagen(edgeRes);
        fEdge.setTitle("Edge detection");
        
        // Laplacian operator normal
        double laplacian[][] = new double[][]{{0,1,0},{1,-4,1},{0,1,0}};
        Image laplacianRes = Convolucion.convolucionar(original, laplacian, 1);
        JFrameImagen fLaplacian = new JFrameImagen(laplacianRes);
        fLaplacian.setTitle("Laplacian");
    }
}

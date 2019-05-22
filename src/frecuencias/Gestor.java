package frecuencias;

import frecuencias.HerramientasColor.CanalColor;
import gui.JFrameImagen;
import io.ImageManager;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author david
 */
public class Gestor {
    private BufferedImage imagenOriginal;
    private Map<HerramientasColor.CanalColor, Complejo[][]> representacionEspacial;
    private Map<HerramientasColor.CanalColor, Complejo[][]> representacionFrecuencias;

    public Gestor(BufferedImage imagenOriginal) {
        this.imagenOriginal = imagenOriginal;
        this.representacionEspacial = new HashMap<>();
        this.representacionFrecuencias = new HashMap<>();
        // Obtener la información de los píxeles y crear los mapeos correspondientes
        for (HerramientasColor.CanalColor c : CanalColor.values()) {
            this.representacionEspacial.put(c, obtenerDatosPorCanal(c));
        }
    }

    private Complejo[][] obtenerDatosPorCanal(CanalColor c) {
        int ancho = this.imagenOriginal.getWidth(null);
        int alto = this.imagenOriginal.getHeight(null);
        Complejo[][] aux = new Complejo[ancho][alto];
        
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                aux[i][j] = 
                    new Complejo(HerramientasColor.obtenerValorPorCanal(
                        this.imagenOriginal.getRGB(i, j), c), 0);
            }
        }
        return aux;
    }
    
    public BufferedImage obtenerImagenFrecuencias(boolean reAjustarCuadrante) {
        /// obtenemos las dimensiones
        int anchoImagen = this.imagenOriginal.getWidth(null);
        int altoImagen = this.imagenOriginal.getHeight(null);
        BufferedImage aux = new BufferedImage(anchoImagen, altoImagen, 
            BufferedImage.TYPE_INT_ARGB);

        FFTCalculo fft = new FFTCalculo();
        // construir el mapeo de representacion en frecuencias utilizando FFT

        for (CanalColor canal : HerramientasColor.CanalColor.values()) {
            Complejo[][] datos = this.representacionEspacial.get(canal);
            Complejo[][] transformada = fft.calculateFT(datos, false);
            representacionFrecuencias.put(canal, transformada);
            // crear la imagen del espectro 
            for (int y = 0; y < aux.getHeight(); y++) {
                for (int x = 0; x < aux.getWidth(); x++) {
                    // modificamos la posicion de los cuadrantes 
                    int ejeX = reAjustarCuadrante ? (x + (anchoImagen / 2)) % anchoImagen : x;
                    int ejeY = reAjustarCuadrante ? (y + (altoImagen / 2)) % altoImagen : y;
                    // setear la info a la imagen 
                    // el que se ecuentre en la imagen 
                    int color1 = aux.getRGB(x, y);
                    int color2 = obtenerColorRealDeFrecuencia(ejeX, ejeY, transformada, canal);
                    int rgb = HerramientasColor.acumularColor(color1, color2);
                    aux.setRGB(x, y, rgb);
                }
            }
        }
        return aux;
    }
    
    public BufferedImage obtenerImagenEspacial() {
        /// obtenemos las dimensiones
        int anchoImagen = this.imagenOriginal.getWidth();
        int altoImagen = this.imagenOriginal.getHeight();
        BufferedImage aux = new BufferedImage(anchoImagen, altoImagen, BufferedImage.TYPE_INT_ARGB);

        FFTCalculo fft = new FFTCalculo();
        // construir el mapeo de representacion en frecuencias utilizando FFT

        for (CanalColor canal : HerramientasColor.CanalColor.values()) {
            Complejo[][] datos = this.representacionFrecuencias.get(canal);
            Complejo[][] transformadaInv = fft.calculateFT(datos, true);
            representacionEspacial.put(canal, transformadaInv);
            // crear la imagen del espectro 
            for (int y = 0; y < aux.getHeight(); y++) {
                for (int x = 0; x < aux.getWidth(); x++) {

                    int color = (int) Math.abs(transformadaInv[x][y].getReal());
                    color = HerramientasColor.validarRangoRGB(color);
                    color = HerramientasColor.obtenerRGBPorCanal(color, canal);

                    int rgb = HerramientasColor.acumularColor(aux.getRGB(x, y), color);
                    aux.setRGB(x, y, rgb);
                }
            }
        }
        return aux;

    }

    private int obtenerColorRealDeFrecuencia(int ejeX, int ejeY, Complejo[][] 
        transformada, CanalColor canal) {
        int color = (int) Math.abs(transformada[ejeX][ejeY].getReal());
        color = HerramientasColor.validarRangoRGB(color);
        color = HerramientasColor.obtenerRGBPorCanal(color, canal);
        return color;
    }
    
    private void aplicarFiltro(Complejo[][] matrizFiltro) {
        // Se obtienen las dimensiones de la matriz de complejos del filtro
        int ancho = matrizFiltro.length;
        int alto = matrizFiltro[0].length;
        
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                // Obtener el RGB de la representación en frecuencias.
                if(matrizFiltro[i][j].getReal() < 1) {
                    //System.out.println(matrizFiltro[i][j].getReal());
                    int rgb = obtenerPixelDominioFrecuencias(i, j, false);
                    Color aux = new Color(rgb);
                    int r = (int) (aux.getRed() * matrizFiltro[i][j].getReal());
                    int g = (int) (aux.getGreen() * matrizFiltro[i][j].getReal());
                    int b = (int) (aux.getBlue() * matrizFiltro[i][j].getReal());
                    r = HerramientasColor.validarRangoRGB(r);
                    g = HerramientasColor.validarRangoRGB(g);
                    b = HerramientasColor.validarRangoRGB(b);
                    aux = new Color(r, g, b);
                    setPixelDominioFrecuencias(i, j, false, aux.getRGB());
                }
            }
        }
    }
    
    private int obtenerPixelDominioFrecuencias(int i, int j, boolean encuadre) {
        // obtener las dimensiones de la imagen original
        int ancho = this.imagenOriginal.getWidth();
        int alto = this.imagenOriginal.getHeight();
        // Modificamos la posición de los cuadrantes
        int ejeX = encuadre ? (i + (ancho / 2)) % ancho : i;
        int ejeY = encuadre ? (j + (alto / 2)) % alto : j;
        // Acumulamos por cada canal
        int valorColor = 0;
        for (CanalColor canal: CanalColor.values()) {
            // se obtiene la matriz para el canal actual
            Complejo aux[][] = this.representacionFrecuencias.get(canal);
            valorColor += obtenerColorRealDeFrecuencia(ejeX, ejeY, aux, canal);
        }
        
        return valorColor;
    }

    private void setPixelDominioFrecuencias(int x, int y, boolean encuadre, int color) {
        // Se obtienen las dimensiones
        int anchoImagen = this.imagenOriginal.getWidth();
        int altoImagen = this.imagenOriginal.getHeight();
        // Modificamos la posición de los cuadrantes
        int ejeX = encuadre ? (x + (anchoImagen / 2)) % anchoImagen : x;
        int ejeY = encuadre ? (x + (altoImagen / 2)) % altoImagen : y;
        // Recorrer por canal de color
        for (CanalColor canal : CanalColor.values()) {
            Complejo[][] datos = this.representacionFrecuencias.get(canal);
            int nuevoRGB = HerramientasColor.obtenerValorPorCanal(color, canal);
            datos[ejeX][ejeY] = new Complejo(nuevoRGB, nuevoRGB);
        }
    }
    
    
    // Función de prueba para ver si se genera una matriz de complejos por cada canal
    // en la representación espacial
    public void prueba() {
        for (Map.Entry<HerramientasColor.CanalColor, Complejo[][]> entry : 
            this.representacionEspacial.entrySet()) {
            HerramientasColor.CanalColor key = entry.getKey();
            Complejo[][] matriz = entry.getValue();
            System.out.println("Key: " + key);
            FFTCalculo fft = new FFTCalculo();
            Complejo res[][] = fft.calculateFT(matriz, false);
            System.out.println("");
        }
    }
    
    public static void main(String args[]) {
        int ancho = 512;
        int alto = 512;
        Image original = ImageManager.openImage();
        original = original.getScaledInstance(ancho, alto, BufferedImage.TYPE_INT_RGB);
        
        BufferedImage bi = ImageManager.toBufferedImage(original);
        Gestor gestor = new Gestor(bi);
        
        JFrameImagen frame0 = new JFrameImagen(ImageManager.toImage(bi));
        frame0.setTitle("Original");
        
        Image res = ImageManager.toImage(gestor.obtenerImagenFrecuencias(false));
        JFrameImagen frame = new JFrameImagen(res);
        frame.setTitle("Frecuencias");
        
        // ### Aplicar el filtro ###
        // Creamos un filtro
        //FiltroIdealPasaBajas filtro = new FiltroIdealPasaBajas(ancho, alto, 200);
        FiltroIdealPasaAltas filtro = new FiltroIdealPasaAltas(ancho, alto, 340);
        //FiltroTrapezoidal filtro = new FiltroTrapezoidal(ancho, alto, 5, 50);
        filtro.generar();
        Complejo matrizFiltro[][] = filtro.getMatriz();
        gestor.aplicarFiltro(matrizFiltro);
        // Vemos el filtro
        Image img = filtro.toImage();
        JFrameImagen frameFiltro = new JFrameImagen(img);
        
        Image resEspacial = ImageManager.toImage(gestor.obtenerImagenEspacial());
        JFrameImagen frame1 = new JFrameImagen(resEspacial);
        frame1.setTitle("Espacial");
    }
}

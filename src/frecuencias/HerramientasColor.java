package frecuencias;

import java.awt.Color;

/**
 *
 * @author david
 */
public class HerramientasColor {
    public enum CanalColor{ROJO(2), VERDE(1), AZUL(0);
        int index;
        CanalColor(int index) {
            this.index = index;
        }
        
        int getIndex() {
            return this.index;
        }
    }
    
    public static int obtenerValorPorCanal(int rgb, CanalColor canalColor) {
        Color color = new Color(rgb);
        int valor = 0;
        switch(canalColor) {
            case ROJO:
                valor = color.getRed();
                break;
            case VERDE:
                valor = color.getGreen();
                break;
            case AZUL:
                valor = color.getBlue();
                break;
        }
        return valor;
    }
    
    public static int obtenerRGBPorCanal(int valor, CanalColor canalColor) {
        // creamos el aux para calcular el rgb dependiendo del canal
        int aux = 0;
        switch(canalColor) {
            case ROJO:
                aux = new Color(valor, 0, 0).getRGB();
                break;
            case VERDE:
                aux = new Color(0, valor, 0).getRGB();
                break;
            case AZUL:
                aux = new Color(0, 0, valor).getRGB();
                break;
        }
        
        return aux;
    }
    
    public static int acumularColor(int color1, int color2 ) {
        return color1 | color2;
    }
    
    public static int validarRangoRGB(int v) {
        if(v > 255) return 255;
        else if(v < 0) return 0;
        else return v;
    }
}

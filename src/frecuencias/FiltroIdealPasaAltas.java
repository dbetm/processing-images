package frecuencias;

import gui.JFrameImagen;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author david
 */
public class FiltroIdealPasaAltas extends Filtro {
    private double radio;

    public FiltroIdealPasaAltas(int ancho, int alto, double radio) {
        super(ancho, alto);
        this.radio = radio;
    }

    @Override
    public void generar() {
        Complejo aux[][] = getMatriz();
        // Recorremos la matriz y verificamos si el pto está trasladado
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < aux.length; j++) {
                int u = -1*(aux.length/2)+i;
                int v = (aux.length/2)-j;
                
                double p = Math.sqrt(Math.pow(u, 2) + Math.pow(v, 2));
                Color color;
                // Verificamos si p es menor al radio
                if(p > this.radio) {
                    // pintamos en blanco
                    color = new Color(255, 255, 255);
                    //aux[i][j] = new Complejo(color.getRGB(), 0);
                    aux[i][j] = new Complejo(1, 1);
                }
                else {
                    // pintamos en negro
                    color = new Color(0, 0, 0);
                    //aux[i][j] = new Complejo(color.getRGB(), 0);
                    aux[i][j] = new Complejo(0, 0);
                }
            }
        }
    }
    
    public static void main(String args[]) {
        FiltroIdealPasaAltas filtro = new FiltroIdealPasaAltas(500, 500, 45);
        Image img = filtro.toImage();
        JFrameImagen frame = new JFrameImagen(img);
    }
}

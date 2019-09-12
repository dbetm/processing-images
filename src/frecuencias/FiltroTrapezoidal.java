package frecuencias;

import gui.JFrameImagen;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author david
 */
public class FiltroTrapezoidal extends Filtro {
    // Atributos
    private double radio0; // k0
    private double radio1; // k1

    public FiltroTrapezoidal(int ancho, int alto, double radio0, double radio1) {
        super(ancho, alto);
        this.radio0 = radio0;
        this.radio1 = radio1;
    }
    
    
    @Override
    public void generar() {
        Complejo aux[][] = getMatriz();
        // Recorremos la matriz y verificamos si el pto está trasladado
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < aux[0].length; j++) {
                int u = -1*(aux.length/2)+i;
                int v = (aux.length/2)-j;
                double p = Math.sqrt(Math.pow(u, 2) + Math.pow(v, 2));
                double res;
                if(p < this.radio0) res = 0;
                else if(p >= this.radio0 && p <= this.radio1) {
                    res = (p - this.radio1) / (this.radio0 - this.radio1);
                }
                else res = 1;
                int res2 = (int)(res*255);
                // asignamos en el color que le asignamos
                Color color = new Color(res2, res2, res2);
                aux[i][j] = new Complejo(res, 0);
            }
        }
    }
    
    public static void main(String args[]) {
        FiltroTrapezoidal filtro = new FiltroTrapezoidal(500, 500, 50, 400);
        Image img = filtro.toImage();
        JFrameImagen frame = new JFrameImagen(img);
    }
    
}

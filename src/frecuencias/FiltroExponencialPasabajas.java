package frecuencias;

import gui.JFrameImagen;
import java.awt.Color;
import java.awt.Image;

/**
 *
 * @author david
 */
public class FiltroExponencialPasabajas extends Filtro {
    private double radio;
    private double n;
    
    public FiltroExponencialPasabajas(int ancho, int alto, double radio, double n) {
        super(ancho, alto);
        this.radio = radio;
        this.n = n;
    }

    @Override
    public void generar() {
        Complejo aux[][] = getMatriz();
        // Recorremos la matriz y verificamos si el pto está trasladado
        for (int i = 0; i < aux.length; i++) {
            for (int j = 0; j < aux.length; j++) {
                int u = -1*(aux.length/2)+i;
                int v = (aux.length/2)-j;
                System.out.println("i: " + i + " j:" + j);
                double p = Math.sqrt(Math.pow(u, 2) + Math.pow(v, 2));
                double res = Math.exp(-1*Math.pow(p / this.radio, this.n));
                int res2 = (int)(res*255);
                // asignamos en el color que le asignamos
                Color color = new Color(res2, res2, res2);
                aux[i][j] = new Complejo(color.getRGB(), 0);
            }
        }
    }
    
    public static void main(String args[]) {
        FiltroExponencialPasabajas filtro = new FiltroExponencialPasabajas(500, 500, 100, 1);
        Image img = filtro.toImage();
        JFrameImagen frame = new JFrameImagen(img);
    }
}

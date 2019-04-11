package tools;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public class Histogram {
    private BufferedImage bi;
    private int R[];
    private int G[];
    private int B[];

    public Histogram(BufferedImage img) {
        this.bi = img;
        this.R = new int[256];
        this.G = new int[256];
        this.B = new int[256];
        compute();
    }
    
    private void compute() {
        // Recorrer el buffered image
        for (int x = 0; x < this.bi.getWidth(); x++) {
            for (int y = 0; y < this.bi.getHeight(); y++) {
                Color c = new Color(this.bi.getRGB(x, y));
                this.R[c.getRed()]++;
                this.G[c.getGreen()]++;
                this.B[c.getBlue()]++;
            }
        }
    }

    public int[] getHistogramR() {
        return R;
    }

    public int[] getHistogramG() {
        return G;
    }

    public int[] getHistogramB() {
        return B;
    }
}

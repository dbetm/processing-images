package demoCECyT18;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public class Operaciones {
    
    public static Image umbralizacionSimple(int u, Image imagenOriginal){
        BufferedImage bi = Herramientas.toBufferedImage(imagenOriginal);
        for(int x=0; x < bi.getWidth(); x++) {
            for(int y=0;y < bi.getHeight(); y++) {
                Color color = new Color(bi.getRGB(x, y));
                int prom = (color.getRed()+color.getGreen()+color.getBlue()) / 3;
                if(prom < u) color = new Color(0, 0, 0);
                else color = new Color(255, 255, 255);
                bi.setRGB(x, y,color.getRGB());
           }
        }
        return Herramientas.toImage(bi);
    }
    
    public static Image generarImagenEnGrises(Image original){
        BufferedImage bi = Herramientas.toBufferedImage(original);
        for(int x=0; x<bi.getWidth();x++){
            for(int y=0;y<bi.getHeight();y++) {
                Color color = new Color(bi.getRGB(x, y));
                int gris = (color.getRed()+color.getGreen()+color.getBlue())/3;
                color = new Color(gris,gris,gris);
                bi.setRGB(x, y, color.getRGB());
            }
        }
        return Herramientas.toImage(bi);
    }
}

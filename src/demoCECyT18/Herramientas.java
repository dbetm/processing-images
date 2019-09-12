package demoCECyT18;

import gui.JFrameImagen;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author david
 */
public class Herramientas {
    public static void mostrarImagen(Image imagen) {
        JFrameImagen frame = new JFrameImagen(imagen);
    }
    
    public static BufferedImage toBufferedImage (Image imagen){
        if (imagen instanceof BufferedImage) {
          return (BufferedImage)imagen;
        }
        BufferedImage bi = new BufferedImage(imagen.getWidth(null), 
            imagen.getHeight(null), BufferedImage.TYPE_INT_RGB);

        Graphics2D nueva = bi.createGraphics();
        nueva.drawImage(imagen, 0, 0,null);
        nueva.dispose();
        return bi;
    }
        
    public static Image toImage (BufferedImage bi){
        return bi.getScaledInstance(bi.getWidth(),bi.getHeight(), BufferedImage.TYPE_INT_RGB);
    }
    
    public static Image openImage(){
        try {
            FileNameExtensionFilter filtro =
                new FileNameExtensionFilter("Imagenes","jpg","jpeg","png","bmp");
            JFileChooser selector = new JFileChooser();
            selector.addChoosableFileFilter(filtro);
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = selector.showOpenDialog(null);
            if (res == 1 ){  
                return null;  
            }
            File archivo = selector.getSelectedFile();
            BufferedImage bi = ImageIO.read(archivo);
            
            return toImage(bi);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}

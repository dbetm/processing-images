package io;

import imagenes.JFrameImagen;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author david
 */
public class ImageManager {
    
    public static Image openImage() {
        try {
            // definir los filtros para lectura
            FileNameExtensionFilter filtro =
                    new FileNameExtensionFilter("Imagenes","jpg","jpeg","png","bmp");
            // crear un selector de archivos
            JFileChooser selector = new JFileChooser();
            // agregar el filtro al selector
            selector.addChoosableFileFilter(filtro);
            // especificar que solo se puedan abrir archivos
            selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
            
            //ejecutar el selector de imágenes
            int res = selector.showOpenDialog(null);
            if (res == 1 ) return null;
            
            File archivo = selector.getSelectedFile();
            BufferedImage bi = ImageIO.read(archivo);
            return toImage(bi);
        } catch (IOException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Image toImage (BufferedImage bi) {
        return bi.getScaledInstance(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_RGB);
    }
    
    public static BufferedImage toBufferedImage(Image imagen) {
         // Si la imagen es un objeto de tipo BufferedImage
        if (imagen instanceof BufferedImage) {
          return (BufferedImage)imagen; // es posible por polimorfismo
        }
        // De otra manera es necesario crear el BufferedImage
        BufferedImage bi = new BufferedImage(imagen.getWidth(null), 
                imagen.getHeight(null), BufferedImage.TYPE_INT_RGB);
        
        // Cuidado, se trabaja por referencia
        Graphics2D nueva = bi.createGraphics();
        nueva.drawImage(imagen, 0, 0, null);
        nueva.dispose();
        return bi;
    }
    
    public static boolean saveImage(String path, Image imagen, String formato) throws IOException {
        File outputfile = new File(path + "." + formato);
        boolean ans = ImageIO.write(toBufferedImage(imagen), formato, outputfile);
        return ans;
    }
    
    public static void main(String args[]) throws IOException {
        Image ejemplo = ImageManager.openImage();
        JFrameImagen fo = new JFrameImagen(ejemplo);
        fo.setVisible(true);
        fo.setTitle("Ejemplo");
        String path = "src/salidas/ejemplo";
        if(saveImage(path, ejemplo, "png")) { 
            System.out.println("Imagen guardada correctamente");
        }
        else {
            System.out.println("Ocurrió un error al escribir la imagen");
        }
        
    }
}

package demoCECyT18;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author david
 */
public class JFrameImage extends javax.swing.JFrame {
    public JFrameImage(Image imagenOriginal) {
        JLabel  jLabelImagen = new JLabel();
        jLabelImagen.setIcon(new ImageIcon(imagenOriginal));
        this.add(jLabelImagen);
        this.setSize(imagenOriginal.getWidth(this), imagenOriginal.getHeight(this));
        setVisible(true);
    }
}

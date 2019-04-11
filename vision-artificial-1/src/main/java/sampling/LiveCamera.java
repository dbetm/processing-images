package sampling;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 *
 * @author david
 */
public class LiveCamera {
    private Webcam webcam;

    public LiveCamera() {
        this.webcam = Webcam.getDefault();
        // Configuramos la resolución
        this.webcam.setViewSize(WebcamResolution.VGA.getSize());
    }
    
    public void iniciarMuestreo() {
        // Creamos un panel de la imagen
        WebcamPanel panel = new WebcamPanel(this.webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(true);
        // Y ahora el JFrame contenedor
        JFrame window = new JFrame("Webcam panel");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
    
    public BufferedImage capturar() {
        return this.webcam.getImage();
    }
}

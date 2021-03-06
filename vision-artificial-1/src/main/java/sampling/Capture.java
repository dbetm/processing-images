package sampling;

import java.awt.image.BufferedImage;

/**
 *
 * @author david
 */
public class Capture extends Thread {
    private LiveCamera cam;
    private BufferedImage bi;

    public Capture(LiveCamera liveCam) {
        this.cam = liveCam;
        this.bi = this.cam.capturar();
    }

    @Override
    public void run() {
        while(true) {
            try {
                this.bi = this.cam.capturar();
                // Para que vaya 10 FPS
                Thread.sleep(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    // Para retornar la imagen
    public BufferedImage getBufferedImage() {
        return this.bi;
    }
}

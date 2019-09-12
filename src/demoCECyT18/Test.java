package demoCECyT18;

import java.awt.Image;

/**
 *
 * @author david
 */
public class Test {
    public static void main(String args[]) {
        Image imagen = Herramientas.openImage();
        // Mostrar la imagen original
        Herramientas.mostrarImagen(imagen);
        //imagen = Operaciones.umbralizacionSimple(60, imagen);
        imagen = Operaciones.generarImagenEnGrises(imagen);
        // Mostramos el resultado
        Herramientas.mostrarImagen(imagen);
    }
}

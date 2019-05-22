package frecuencias;

/**
 *
 * @author david
 */
public class FFTCalculo {
    
    public Complejo[][] calculateFT(Complejo[][] imagenOriginal, boolean inversa) {
        int anchoImagen = imagenOriginal.length;
        int altoImagen = imagenOriginal[0].length;
                               
        Complejo[][] imagenTransformada = new Complejo[anchoImagen][altoImagen];

        int anchoLog2 = calculaLog2(anchoImagen);
        int altoLog2 = calculaLog2(altoImagen);
                        
        // copiar a ceros para acumular
        for (int x = 0; x < anchoImagen; x++)
                for (int y = 0; y < altoImagen; y++)
                        imagenTransformada[x][y] = new Complejo(imagenOriginal[x][y]);

        //cambio de bits por renglon
        for (int y = 0; y < altoImagen; y++) {
            // para cada reglon
            int offset = 0;
            for (int i = 0; i < anchoImagen - 1; i++) {
                imagenTransformada[i][y] = new Complejo(imagenOriginal[offset][y]);
                int puntoMedio = anchoImagen / 2;
                while (puntoMedio <= offset) {
                        offset -= puntoMedio;
                        puntoMedio /= 2;
                }
                offset += puntoMedio;
            }
        }
        
        // cambio  de bits por columna 
        for (int x = 0; x < anchoImagen; x++) {
            // para cada columna
            int j = 0;
            for (int i = 0; i < altoImagen - 1; i++) {
                if (i < j) {
                    Complejo tempComplex = new Complejo(imagenTransformada[x][i]);
                    imagenTransformada[x][i] = new Complejo(imagenTransformada[x][j]);
                    imagenTransformada[x][j] = tempComplex;
                }
                int puntoMedio = altoImagen / 2;
                while (puntoMedio <= j) {
                    j -= puntoMedio;
                    puntoMedio /= 2;
                }
                j += puntoMedio;
            }
        }

        // Acumulación por columnas
        for (int x = 0; x < anchoImagen; x++) {
            double coseno = -1.0;
            double seno = 0.0;
            int l1 = 1, l2 = 1;
            
            for (int l = 0; l < anchoLog2; l++) {
                l1 = l2;
                l2 <<= 1;
                double u1 = 1.0;
                double u2 = 0.0;
                for (int j = 0; j < l1; j++) {
                    for (int i = j; i < anchoImagen; i += l2) {
                        int i1 = i + l1;
                        double t1 = u1 * imagenTransformada[x][i1].getReal() - u2
                            * imagenTransformada[x][i1].getImaginaria();
                        double t2 = u1 * imagenTransformada[x][i1].getImaginaria() + u2
                            * imagenTransformada[x][i1].getReal();
                        imagenTransformada[x][i1] = 
                            imagenTransformada[x][i].sumar(new Complejo(-t1, -t2));
                        imagenTransformada[x][i] = 
                            imagenTransformada[x][i].sumar(new Complejo(t1, t2));
                    }
                    double z = u1 * coseno - u2 * seno;
                    u2 = u1 * seno + u2 * coseno;
                    u1 = z;
                }
                seno = Math.sqrt((1.0 - coseno) / 2.0);
                if (!inversa)
                    seno = -seno;
                coseno = Math.sqrt((1.0 + coseno) / 2.0);
            }
        }

        //  Acumulación por renglon
        for (int y = 0; y < altoImagen; y++) {
            double coseno = -1.0;
            double seno = 0.0;
            int l1 = 1, l2 = 1;
            for (int l = 0; l < altoLog2; l++) {
                l1 = l2;
                l2 <<= 1;
                double u1 = 1.0;
                double u2 = 0.0;
                for (int j = 0; j < l1; j++) {
                    for (int i = j; i < anchoImagen; i += l2) {
                        int i1 = i + l1;
                        double t1 = u1 * imagenTransformada[i1][y].getReal() - u2
                            * imagenTransformada[i1][y].getImaginaria();
                        double t2 = u1 * imagenTransformada[i1][y].getImaginaria() + u2
                            * imagenTransformada[i1][y].getReal();
                        imagenTransformada[i1][y] = 
                            imagenTransformada[i][y].sumar(new Complejo(-t1, -t2));
                        imagenTransformada[i][y] = 
                            imagenTransformada[i][y].sumar(new Complejo(t1, t2));
                    }
                    double z = u1 * coseno - u2 * seno;
                    u2 = u1 * seno + u2 * coseno;
                    u1 = z;
                }
                seno = Math.sqrt((1.0 - coseno) / 2.0);
                if (!inversa) seno = -seno;
                coseno = Math.sqrt((1.0 + coseno) / 2.0);
            }
        }

        int dimension;
        if (inversa) dimension = anchoImagen;
        else dimension = altoImagen;
        for (int x = 0; x < anchoImagen; x++)
            for (int y = 0; y < altoImagen; y++) {
                imagenTransformada[x][y] 
                    = imagenTransformada[x][y].multiplicar(1 / (double) dimension);
            }

        return imagenTransformada;
    }

    private int calculaLog2(int dim) {
        int conteo = 0;
        while ((dim >>= 1) > 0)
                conteo++;
        return conteo;
    }
}

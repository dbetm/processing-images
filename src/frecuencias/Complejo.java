package frecuencias;

/**
 *
 * @author david
 */
public class Complejo {
    private double real;
    private double imaginaria;

    public Complejo(double real, double imaginaria) {
        this.real = real;
        this.imaginaria = imaginaria;
    }

    public Complejo(Complejo c) {
        this.real = c.getReal();
        this.imaginaria = c.getImaginaria();
    }
    
    public Complejo sumar(Complejo segundo) {
        //obtiene la referencia al objeto
        Complejo primero = this; 
        double real = primero.real + segundo.real;
        double imag = primero.imaginaria + segundo.imaginaria;
        return new Complejo(real, imag);
    }

    public Complejo multiplicar(double multiplicador) {
        return new Complejo(real * multiplicador, imaginaria * multiplicador);
    }

    // Métodos de acceso
    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginaria() {
        return imaginaria;
    }

    public void setImaginaria(double imaginaria) {
        this.imaginaria = imaginaria;
    }
}

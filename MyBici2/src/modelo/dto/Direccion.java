package modelo.dto;

/**
 * @
 */
public class Direccion {

    /**
     * Default constructor
     */
    public Direccion() {
    }

    /**
     * 
     */
    private Carrera carrera;

    /**
     * 
     */
    private Calle calle;

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Calle getCalle() {
        return calle;
    }

    public void setCalle(Calle calle) {
        this.calle = calle;
    }



}
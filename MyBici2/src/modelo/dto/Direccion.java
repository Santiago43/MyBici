package modelo.dto;

/**
 * Clase de dirección
 * @author Santiago Pérez 
 * @version 1.0
 * @since 2020-05-16
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
    private int idDireccion;
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

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }



}
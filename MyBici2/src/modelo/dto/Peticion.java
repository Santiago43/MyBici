package modelo.dto;

/**
 * Clase de peticiones de empleados
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-2020
 */
public class Peticion {
    private int idPeticionEmpleado;
    private String cedula;
    private String peticion;
    private boolean aprobado;

    public int getIdPeticionEmpleado() {
        return idPeticionEmpleado;
    }

    public void setIdPeticionEmpleado(int idPeticionEmpleado) {
        this.idPeticionEmpleado = idPeticionEmpleado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getPeticion() {
        return peticion;
    }

    public void setPeticion(String peticion) {
        this.peticion = peticion;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    
}

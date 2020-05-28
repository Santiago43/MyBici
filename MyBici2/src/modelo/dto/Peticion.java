package modelo.dto;

/**
 * Clase de peticiones de empleados
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-2020
 */
public class Peticion {
    private int idPeticionEmpleado;
    private String peticion;
    private boolean aprobado;
    private Empleado empleado;

    public int getIdPeticionEmpleado() {
        return idPeticionEmpleado;
    }

    public void setIdPeticionEmpleado(int idPeticionEmpleado) {
        this.idPeticionEmpleado = idPeticionEmpleado;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
}

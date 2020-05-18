package modelo.dto;

/**
 * Clase usuarios
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-11
 */
public class Usuario {
    /**
     * 
     */
    private String usuario;
    /**
     * 
     */
    private Empleado empleado;
    /**
     * 
     */
    private Rol rol;
    /**
     * 
     */
    private String contraseña;
    /**
     * 
     * @return 
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * 
     * @param usuario 
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    /**
     * 
     * @return 
     */
    public Rol getRol() {
        return rol;
    }
    /**
     * 
     * @param rol 
     */
    public void setRol(Rol rol) {
        this.rol = rol;
    }
    /**
     * 
     * @return 
     */
    public String getContraseña() {
        return contraseña;
    }
    /**
     * 
     * @param contraseña 
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
}

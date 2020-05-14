package modelo.dto;

/**
 * 
 */
public class EmpresaMantenimiento {

    /**
     * Default constructor
     */
    public EmpresaMantenimiento() {
    }

    /**
     * 
     */
    private int idEmpresaMantenimiento;

    /**
     * 
     */
    private Direccion direccion;

    /**
     * 
     */
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdEmpresaMantenimiento() {
        return idEmpresaMantenimiento;
    }

    public void setIdEmpresaMantenimiento(int idEmpresaMantenimiento) {
        this.idEmpresaMantenimiento = idEmpresaMantenimiento;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }


}
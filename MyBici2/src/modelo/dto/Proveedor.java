package modelo.dto;

/**
 * 
 */
public class Proveedor {

    /**
     * Default constructor
     */
    public Proveedor() {
    }

    /**
     * 
     */
    private int idProveedor;

    /**
     * 
     */
    private Direccion direccion;

    /**
     * 
     */
    private Telefono telefono;

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Telefono getTelefono() {
        return telefono;
    }

    public void setTelefono(Telefono telefono) {
        this.telefono = telefono;
    }



}
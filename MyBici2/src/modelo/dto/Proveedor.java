package modelo.dto;

import java.util.LinkedList;

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
    private String nombre;

    /**
     * 
     */
    private LinkedList<Telefono> telefono;

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


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<Telefono> getTelefono() {
        return telefono;
    }

    public void setTelefono(LinkedList<Telefono> telefono) {
        this.telefono = telefono;
    }



}
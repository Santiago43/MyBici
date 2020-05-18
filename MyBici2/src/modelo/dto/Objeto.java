package modelo.dto;


import java.util.*;

/**
 * 
 */
public class Objeto {

    /**
     * Default constructor
     */
    public Objeto() {
    }

    /**
     * 
     */
    private int idObjeto;

    /**
     * 
     */
    private String marca;

    /**
     * 
     */
    private int añosGarantia;

    /**
     * 
     */
    private LinkedList<Proveedor> proveedores;

    public int getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(int idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAñosGarantia() {
        return añosGarantia;
    }

    public void setAñosGarantia(int añosGarantia) {
        this.añosGarantia = añosGarantia;
    }

    public LinkedList<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(LinkedList<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    

}
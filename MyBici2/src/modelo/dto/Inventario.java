package modelo.dto;


import java.util.*;

/**
 * 
 */
public class Inventario {

    /**
     * Default constructor
     */
    public Inventario() {
    }

    /**
     * 
     */
    private Sede sede;
    
    private int idInventario;

    /**
     * 
     */
    private LinkedList<Mercancia> mercancia;

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public LinkedList<Mercancia> getMercancia() {
        return mercancia;
    }

    public void setMercancia(LinkedList<Mercancia> mercancia) {
        this.mercancia = mercancia;
    }

    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }



}
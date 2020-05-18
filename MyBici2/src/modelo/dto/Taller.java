package modelo.dto;


import java.util.*;

/**
 * 
 */
public class Taller {

    /**
     * Default constructor
     */
    public Taller() {
    }

    /**
     * 
     */
    private int idTaller;

    /**
     * 
     */
    private LinkedList<MantenimientoTaller> mantenimientos;

    /**
     * 
     */
    private double totalVentas;

    public int getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    public LinkedList<MantenimientoTaller> getMantenimientos() {
        return mantenimientos;
    }

    public void setMantenimientos(LinkedList<MantenimientoTaller> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }
    
    
    

}
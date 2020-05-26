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
    
    /**
     * 
     */
    private Sede sede;

    /**
     *
     * @return
     */
    public int getIdTaller() {
        return idTaller;
    }

    /**
     *
     * @param idTaller
     */
    public void setIdTaller(int idTaller) {
        this.idTaller = idTaller;
    }

    /**
     *
     * @return
     */
    public LinkedList<MantenimientoTaller> getMantenimientos() {
        return mantenimientos;
    }

    /**
     *
     * @param mantenimientos
     */
    public void setMantenimientos(LinkedList<MantenimientoTaller> mantenimientos) {
        this.mantenimientos = mantenimientos;
    }

    /**
     *
     * @return
     */
    public double getTotalVentas() {
        return totalVentas;
    }

    /**
     *
     * @param totalVentas
     */
    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    /**
     *
     * @return
     */
    public Sede getSede() {
        return sede;
    }

    /**
     *
     * @param sede
     */
    public void setSede(Sede sede) {
        this.sede = sede;
    }
    
    
    

}
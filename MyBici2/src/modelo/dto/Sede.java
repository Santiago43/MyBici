package modelo.dto;
import java.util.*;

/**
 * 
 */
public class Sede {

    /**
     * Default constructor
     */
    public Sede() {
    }

    /**
     * 
     */
    private int idSede;

    /**
     * 
     */
    private String nombreSede;

    /**
     * 
     */
    private Inventario inventario;

    /**
     * 
     */
    private Direccion direccion;

    /**
     * 
     */
    private LinkedList<Taller> talleres;

    /**
     * 
     */
    private LinkedList<EquipoOficina> equipoOficina;

    /**
     * 
     */
    private LinkedList<Empleado> emplados;

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public LinkedList<Taller> getTalleres() {
        return talleres;
    }

    public void setTalleres(LinkedList<Taller> talleres) {
        this.talleres = talleres;
    }

    public LinkedList<EquipoOficina> getEquipoOficina() {
        return equipoOficina;
    }

    public void setEquipoOficina(LinkedList<EquipoOficina> equipoOficina) {
        this.equipoOficina = equipoOficina;
    }

    public LinkedList<Empleado> getEmplados() {
        return emplados;
    }

    public void setEmplados(LinkedList<Empleado> emplados) {
        this.emplados = emplados;
    }

    
}
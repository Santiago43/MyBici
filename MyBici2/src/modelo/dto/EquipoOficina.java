package modelo.dto;

/**
 * 
 */
public class EquipoOficina extends Objeto {

    /**
     * Default constructor
     */
    public EquipoOficina() {
    }

    /**
     * 
     */
    private Sede sede;

    /**
     * 
     */
    private String descripcion;

    /**
     * 
     */
    private String puc;

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPuc() {
        return puc;
    }

    public void setPuc(String puc) {
        this.puc = puc;
    }


}
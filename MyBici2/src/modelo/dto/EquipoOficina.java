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

    private double valorAdquisicion;
    private double depreciacion;
    private String fechaAdquisicion;

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

    /**
     *
     * @return
     */
    public String getPuc() {
        return puc;
    }

    /**
     *
     * @param puc
     */
    public void setPuc(String puc) {
        this.puc = puc;
    }

    /**
     *
     * @return
     */
    public double getValorAdquisicion() {
        return valorAdquisicion;
    }

    /**
     *
     * @param valorAdquisicion
     */
    public void setValorAdquisicion(double valorAdquisicion) {
        this.valorAdquisicion = valorAdquisicion;
    }

    /**
     *
     * @return
     */
    public double getDepreciacion() {
        return depreciacion;
    }

    /**
     *
     * @param depreciacion
     */
    public void setDepreciacion(double depreciacion) {
        this.depreciacion = depreciacion;
    }

    /**
     *
     * @return
     */
    public String getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    /**
     *
     * @param fechaAdquisicion
     */
    public void setFechaAdquisicion(String fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

}

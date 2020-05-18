package modelo.dto;

/**
 * 
 */
public class MantenimientoTaller {

    /**
     * Default constructor
     */
    public MantenimientoTaller() {
    }

    /**
     * 
     */
    private int idMantenimiento;

    /**
     * 
     */
    private String fecha;

    /**
     * 
     */
    private String factura;

    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

}
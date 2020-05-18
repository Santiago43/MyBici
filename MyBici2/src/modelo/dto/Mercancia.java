package modelo.dto;

/**
 * 
 */
public class Mercancia extends Objeto {

    /**
     * Default constructor
     */
    public Mercancia() {
    }

    /**
     * 
     */
    private String nombre;

    /**
     * 
     */
    private double valorAdquisicion;

    /**
     * 
     */
    private double precioVenta;

    /**
     * 
     */
    private int cantidad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValorAdquisicion() {
        return valorAdquisicion;
    }

    public void setValorAdquisicion(double valorAdquisicion) {
        this.valorAdquisicion = valorAdquisicion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }



}
package modelo.dto;

/**
 * Clase mercancía
 * @author Andrés Camilo López - Carlos Plaza - Santiago Pérez
 * @version 1.0
 * @since 2020-05-23
 */
public class Mercancia extends Objeto {

    /**
     * Default constructor
     */
    public Mercancia() {
    }

    private String nombre;
    private double valorAdquisicion;
    private double precioVenta;
    private int cantidad;
    private int cantidadCompra;
    
    /**
     * 
     * @return 
     */

    public String getNombre() {
        return nombre;
    }
    /**
     * 
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public double getPrecioVenta() {
        return precioVenta;
    }
    /**
     * 
     * @param precioVenta 
     */
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
    /**
     * 
     * @return 
     */
    public int getCantidad() {
        return cantidad;
    }
    /**
     * 
     * @param cantidad 
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }



}
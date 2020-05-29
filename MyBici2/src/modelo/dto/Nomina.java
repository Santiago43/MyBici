
package modelo.dto;

/**
 *
 * @author F. PLAZA
 */
public class Nomina {
    
    public Nomina(){
        
    }
    private int idNomina;
    private Empleado cedula;
    private int horasExtra;
    private String fechaNomina;
    private boolean AuxTransportebool;
    private double AuxTransportedouble;
    private double descuento;
    private int diasAusencia;

    public int getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(int idNomina) {
        this.idNomina = idNomina;
    }

    public Empleado getCedula() {
        return cedula;
    }

    public void setCedula(Empleado cedula) {
        this.cedula = cedula;
    }

    public int getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(int horasExtra) {
        this.horasExtra = horasExtra;
    }

    public String getFechaNomina() {
        return fechaNomina;
    }

    public void setFechaNomina(String fechaNomina) {
        this.fechaNomina = fechaNomina;
    }

    public boolean isAuxTransportebool() {
        return AuxTransportebool;
    }

    public void setAuxTransportebool(boolean AuxTransportebool) {
        this.AuxTransportebool = AuxTransportebool;
    }

    public double getAuxTransportedouble() {
        return AuxTransportedouble;
    }

    public void setAuxTransportedouble(double AuxTransportedouble) {
        this.AuxTransportedouble = AuxTransportedouble;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getDiasAusencia() {
        return diasAusencia;
    }

    public void setDiasAusencia(int diasAusencia) {
        this.diasAusencia = diasAusencia;
    }
    
    
}

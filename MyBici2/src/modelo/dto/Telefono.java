package modelo.dto;

/**
 * 
 */
public class Telefono {

    /**
     * Default constructor
     */
    public Telefono() {
    }

    /**
     * 
     */
    private int idTelefono;

    /**
     * 
     */
    private String numeroTelefonico;

    /**
     * 
     */
    private String tipoTelefono;

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }

    public void setNumeroTelefonico(String numeroTelefonico) {
        this.numeroTelefonico = numeroTelefonico;
    }

    public String getTipoTelefono() {
        return tipoTelefono;
    }

    public void setTipoTelefono(String tipoTelefono) {
        this.tipoTelefono = tipoTelefono;
    }




}
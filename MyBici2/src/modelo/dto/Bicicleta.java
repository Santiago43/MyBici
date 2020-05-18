package modelo.dto;

/**
 * Clase de bicicletas
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-14
 */
public class Bicicleta {
    private String marcoSerial;
    private String grupoMecanico;
    private String color;
    private String marca;
    private String estado;

    public String getMarcoSerial() {
        return marcoSerial;
    }

    public void setMarcoSerial(String marcoSerial) {
        this.marcoSerial = marcoSerial;
    }

    public String getGrupoMecanico() {
        return grupoMecanico;
    }

    public void setGrupoMecanico(String grupoMecanico) {
        this.grupoMecanico = grupoMecanico;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}

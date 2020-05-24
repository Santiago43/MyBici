package modelo.dto;

/**
 * Clase de permisos
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-24
 */
public class Permiso {
    private int idPermiso;
    private String nombrePermiso;

    /**
     *
     * @return
     */
    public int getIdPermiso() {
        return idPermiso;
    }

    /**
     *
     * @param idPermiso
     */
    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    /**
     *
     * @return
     */
    public String getNombrePermiso() {
        return nombrePermiso;
    }

    /**
     *
     * @param nombrePermiso
     */
    public void setNombrePermiso(String nombrePermiso) {
        this.nombrePermiso = nombrePermiso;
    }
    
}

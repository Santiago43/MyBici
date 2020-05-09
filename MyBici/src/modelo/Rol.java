package modelo;

import java.util.LinkedList;

/**
 *
 * @author Santiago Pérez
 */
public class Rol {
    private String nombre;
    private LinkedList<String> permisos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LinkedList<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(LinkedList<String> permisos) {
        this.permisos = permisos;
    }
    
}

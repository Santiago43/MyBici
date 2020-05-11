package modelo.dto;

import java.util.LinkedList;

/**
 *
 * @author Santiago Pérez
 */
public class Rol {
    private int id;
    private String nombre;
    private LinkedList <String> permisos; 

    public String getNombre() {
        return nombre;
    }

    public LinkedList <String> getPermisos() {
        return permisos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPermisos(LinkedList <String> permisos) {
        this.permisos = permisos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}

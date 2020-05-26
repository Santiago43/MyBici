package modelo.dto;

import java.util.LinkedList;

/**
 * Clase rol
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-11
 */
public class Rol {
    private int id;
    private String nombre;
    private LinkedList <Permiso> permisos; 
    private String nombreCorto;

    public Rol() {
        this.permisos=new LinkedList();
    }

    
    public String getNombre() {
        return nombre;
    }

    public LinkedList <Permiso> getPermisos() {
        return permisos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPermisos(LinkedList <Permiso> permisos) {
        this.permisos = permisos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }
    
    
}

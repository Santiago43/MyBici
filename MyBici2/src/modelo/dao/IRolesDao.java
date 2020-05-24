package modelo.dao;

import modelo.dto.Permiso;
import modelo.dto.Rol;

/**
 *
 * @author Santiago Pérez
 */
public interface IRolesDao extends IDao<Rol>{
 
    /**
     *
     * @param nombre
     * @return
     */
    public Rol consultarPorNombre(String  nombre);

    /**
     *
     * @param permiso
     * @param rol
     * @return
     */
    boolean agregarPermiso(Permiso permiso, Rol rol);

    /**
     *
     * @param permiso
     * @param rol
     * @return
     */
    boolean removerPermiso(Permiso permiso, Rol rol);
    
}

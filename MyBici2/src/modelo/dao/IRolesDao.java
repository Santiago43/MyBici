package modelo.dao;

import modelo.dto.Rol;

/**
 *
 * @author Santiago Pérez
 */
public interface IRolesDao extends IDao<Rol>{
 
    public Rol  consultarPorNombre(String  nombre);
    boolean agregarPermiso(String permiso, Rol rol);
    boolean removerPermiso(String permiso, Rol rol);
    
}

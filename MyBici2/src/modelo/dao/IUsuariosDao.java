package modelo.dao;

import modelo.dto.Usuario;

/**
 * Interfaz usuariosDao
 * @author Santiago Pérez
 */
public interface IUsuariosDao extends IDao<Usuario>{
    boolean insertarPermiso(String permiso, Usuario usuario);
    boolean removerPermiso(String permiso, Usuario usuario);
}

package modelo.dao;

import modelo.dto.Permiso;
import modelo.dto.Usuario;

/**
 * Interfaz usuariosDao
 * @author Santiago Pérez
 */
public interface IUsuariosDao extends IDao<Usuario>{
    boolean insertarPermiso(Permiso permiso, Usuario usuario);
    boolean removerPermiso(Permiso permiso, Usuario usuario);
    Usuario consultarPorCedula(String cedula);
}

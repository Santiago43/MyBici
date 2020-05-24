package modelo.dao;

import modelo.dto.Direccion;

/**
 *
 * @author Santiago Pérez
 */
public interface IDireccionDao extends IDao<Direccion>{
    public int consultarIdUltimaDireccion();
}

package modelo.dao;

import java.util.LinkedList;
import modelo.dto.Peticion;

/**
 * Interface de peticiones
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-26
 */
public interface IPeticionDao extends IDao<Peticion>{

    /**
     *
     * @return
     */
    LinkedList<Peticion> listarPendientes();
}

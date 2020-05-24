package modelo.dao;

import java.util.LinkedList;

/**
 *
 * @author Santiago Pérez
 * @param <DTO>
 */
public interface IDao <DTO>{
    boolean crear(DTO proveedor);
    DTO consultar(String clave);
    boolean actualizar(DTO dto);
    boolean eliminar(String clave);
    LinkedList <DTO> listar();
}

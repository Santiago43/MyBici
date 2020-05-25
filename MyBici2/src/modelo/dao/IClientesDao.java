package modelo.dao;

import modelo.dto.Cliente;
import modelo.dto.Telefono;

/**
 * Interface de clientes
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-23
 */
public interface IClientesDao extends IDao<Cliente> {
    boolean agregarTelefono(Cliente cliente, Telefono telefono);
}

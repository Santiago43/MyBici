package modelo.dao;

import java.util.LinkedList;
import modelo.dto.Cliente;
import modelo.dto.Telefono;

/**
 * Clase de objeto de acceso a datos de los clientes
 * @author Santiago Pérez 
 * @version 1.0
 * @since 2020-05-23
 */
public class ClienteDao implements IClientesDao{

    /**
     *
     * @param cliente
     * @return
     */
    @Override
    public boolean crear(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public Cliente consultar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param cliente
     * @return
     */
    @Override
    public boolean actualizar(Cliente cliente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public boolean eliminar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    public LinkedList<Cliente> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param clave
     * @param telefono
     * @return
     */
    @Override
    public boolean agregarTelefono(String clave, Telefono telefono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

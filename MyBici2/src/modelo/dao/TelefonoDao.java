package modelo.dao;

import java.util.LinkedList;
import modelo.dto.Telefono;

/**
 * Objeto de acceso a datos de los telefonos. En realidad, solamente el tipo de telefono
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class TelefonoDao implements ITelefonoDao{
    
    @Override
    public boolean crear(Telefono proveedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Telefono consultar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(Telefono dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Telefono> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

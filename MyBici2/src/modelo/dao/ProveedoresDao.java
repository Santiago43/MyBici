package modelo.dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.Proveedor;

/**
 * Objeto de acceso a datos de los proveedores
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-23
 */
public class ProveedoresDao implements IProveedoresDao{

    /**
     *
     * @param proveedor
     * @return
     */
    @Override
    public boolean crear(Proveedor proveedor) {
        try {
            String sql = "insert into proveedor (Direccion_idDireccion,nombre) values (?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setInt(1, proveedor.getDireccion().getIdDireccion());
            pat.setString(2, proveedor.getNombre());
            boolean insert = pat.execute();
            pat.close();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(SedeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public Proveedor consultar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param proveedor
     * @return
     */
    @Override
    public boolean actualizar(Proveedor proveedor) {
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
    public LinkedList<Proveedor> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

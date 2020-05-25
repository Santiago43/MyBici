package modelo.dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.Proveedor;
import modelo.dto.Telefono;

/**
 * Objeto de acceso a datos de los proveedores
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-23
 */
public class ProveedoresDao implements IProveedoresDao {

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
        Proveedor proveedor = null;
        try {
            String sql = "select * from proveedor where idProveedor =" + clave;
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                proveedor = new Proveedor();
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(new DireccionDao().consultar(String.valueOf(rs.getInt("Direccion_idDireccion"))));
                //proveedor.setTelefono(obtenerTelefonos(proveedor.getIdProveedor()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedor;
    }

    /**
     *
     * @param proveedor
     * @return
     */
    @Override
    public boolean actualizar(Proveedor proveedor) {
        try {
            String sql = "update proveedor\n"
                    + "set Direccion_idDireccion = " + proveedor.getDireccion().getIdDireccion() + ", nombre='" + proveedor.getNombre() + "' \n"
                    + "where idProveedor = " + proveedor.getIdProveedor();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int update = pat.executeUpdate();
            return update > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public boolean eliminar(String clave) {
        try {
            String sql = "delete from proveedor where idProveedor =" + clave;
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            return pat.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public LinkedList<Proveedor> listar() {
        LinkedList <Proveedor> proveedores=null;
        try {
            String sql = "select * from proveedor ";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            proveedores = new LinkedList();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(new DireccionDao().consultar(String.valueOf(rs.getInt("Direccion_idDireccion"))));
                //proveedor.setTelefono(obtenerTelefonos(proveedor.getIdProveedor()));
                proveedores.add(proveedor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedores;
    }

    private LinkedList<Telefono> obtenerTelefonos(int idProveedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

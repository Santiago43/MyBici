package modelo.dao;

import conexion.Conexion;
import java.sql.CallableStatement;
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
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(new DireccionDao().consultar(String.valueOf(rs.getInt("Direccion_idDireccion"))));
                proveedor.setTelefono(obtenerTelefonos(conn, proveedor.getIdProveedor()));
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
        LinkedList<Proveedor> proveedores = null;
        try {
            String sql = "select * from proveedor ";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            proveedores = new LinkedList();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(new DireccionDao().consultar(String.valueOf(rs.getInt("Direccion_idDireccion"))));
                proveedor.setTelefono(obtenerTelefonos(conn, proveedor.getIdProveedor()));
                proveedores.add(proveedor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedores;
    }

    private LinkedList<Telefono> obtenerTelefonos(Connection conn, int idProveedor) throws SQLException {
        LinkedList<Telefono> telefonos = null;
        String sql = "select t.id_telefono,t.tipo,pt.numeroTelefono from telefono as t\n"
                + "inner join proveedor_has_telefono as pt on pt.Telefono_id_telefono = t.id_telefono\n"
                + "inner join proveedor as p on p.idProveedor=pt.Proveedor_idProveedor\n"
                + "where p.idProveedor =" + idProveedor;
        PreparedStatement pat = conn.prepareStatement(sql);
        ResultSet rs = pat.executeQuery();
        telefonos = new LinkedList();
        while (rs.next()) {
            Telefono telefono = new Telefono();
            telefono.setIdTelefono(rs.getInt("id_telefono"));
            telefono.setTipoTelefono(rs.getString("tipo"));
            telefono.setNumeroTelefonico(rs.getString("numeroTelefono"));
            telefonos.add(telefono);
        }
        return telefonos;
    }

    @Override
    public boolean agregarTelefono(Proveedor proveedor, Telefono telefono) {
        try {

            String sql = "call agregarTelefonoAProveedor(?,?,?);";
            Connection conn = Conexion.conectado();
            CallableStatement call = conn.prepareCall(sql);
            call.setString("_tipo", telefono.getTipoTelefono());
            call.setInt("idProveedor", proveedor.getIdProveedor());
            call.setString("numeroTelefono", telefono.getNumeroTelefonico());
            call.execute();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<Object[]> listarVentaMayor() {
        LinkedList<Object[]> proveedores = null;
        try {
            String sql = "select pro.*, sum(eo.valorAdquisicion) as total,count(pro.idProveedor) as cantidad from proveedor as pro\n"
                    + "inner join objeto_has_proveedor as op on pro.idProveedor = op.Proveedor_idProveedor\n"
                    + "inner join objeto as o on op.Objeto_idObjeto = o.idObjeto\n"
                    + "inner join equipooficina as eo on eo.Objeto_idObjeto = o.idObjeto\n"
                    + "order by total desc;";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            proveedores = new LinkedList();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(new DireccionDao().consultar(String.valueOf(rs.getInt("Direccion_idDireccion"))));
                Object data[] = {proveedor, rs.getDouble("total"), rs.getInt("cantidad")};
                proveedores.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedores;
    }

    @Override
    public LinkedList<Object[]> listarVentaMenor() {
        LinkedList<Object[]> proveedores = null;
        try {
            String sql = "select pro.*, sum(eo.valorAdquisicion) as total,count(pro.idProveedor) as cantidad from proveedor as pro\n"
                    + "inner join objeto_has_proveedor as op on pro.idProveedor = op.Proveedor_idProveedor\n"
                    + "inner join objeto as o on op.Objeto_idObjeto = o.idObjeto\n"
                    + "inner join equipooficina as eo on eo.Objeto_idObjeto = o.idObjeto\n"
                    + "order by total asc;";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            proveedores = new LinkedList();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(new DireccionDao().consultar(String.valueOf(rs.getInt("Direccion_idDireccion"))));
                Object data[] = {proveedor, rs.getDouble("total"),rs.getInt("cantidad")};
                proveedores.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedores;
    }

    @Override
    public LinkedList<Object[]> listarFrecuentes() {

        LinkedList<Object[]> proveedores = null;
        try {
            String sql = "select pro.*, sum(eo.valorAdquisicion) as total,count(pro.idProveedor) as cantidad from proveedor as pro\n"
                    + "inner join objeto_has_proveedor as op on pro.idProveedor = op.Proveedor_idProveedor\n"
                    + "inner join objeto as o on op.Objeto_idObjeto = o.idObjeto\n"
                    + "inner join equipooficina as eo on eo.Objeto_idObjeto = o.idObjeto\n"
                    + "order by cantidad desc;";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            proveedores = new LinkedList();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(rs.getInt("idProveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setDireccion(new DireccionDao().consultar(String.valueOf(rs.getInt("Direccion_idDireccion"))));
                Object data[] = {proveedor, rs.getDouble("total"),rs.getInt("cantidad")};
                proveedores.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return proveedores;
    }

    @Override
    public boolean removerTelefono(Telefono telefono) {
        try {
            String sql= "delete from proveedor_has_telefono where numeroTelefono='"+telefono.getNumeroTelefonico()+"'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<Telefono> listarTelefonos(Proveedor proveedor) {
        LinkedList <Telefono> telefonos=null;
        try {
            String sql = "select t.tipo , pt.numeroTelefono from telefono as t\n"
                    + "inner join proveedor_has_telefono as pt on pt.Telefono_id_telefono = t.id_telefono\n"
                    + "inner join proveedor as p on p.idProveedor = pt.Proveedor_idProveedor\n"
                    + "where idProveedor="+proveedor.getIdProveedor();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            telefonos = new LinkedList();
            while(rs.next()){
                Telefono telefono = new Telefono();
                telefono.setTipoTelefono(rs.getString("tipo"));
                telefono.setNumeroTelefonico(rs.getString("numeroTelefono"));
                telefonos.add(telefono);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProveedoresDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return telefonos;
    }

}

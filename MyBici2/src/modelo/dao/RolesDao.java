package modelo.dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.Permiso;
import modelo.dto.Rol;

/**
 * Clase de objeto de acceso a datos de los roles
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-24
 */
public class RolesDao implements IRolesDao {

    /**
     *
     * @param rol
     * @return
     */
    @Override
    public boolean crear(Rol rol) {
        try {
            String sql = "insert into rol (nombreRol) values (?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(1, rol.getNombre());
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public Rol consultar(String clave) {
        Rol rol = null;
        try {
            String sql = "select * from rol where idRol=" + clave;
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                rol = new Rol();
                rol.setId(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombre"));
                rol.setNombreCorto(rs.getString("nombreCorto"));
            }
            pat.close();
            rs.close();
            sql = "select p.* as nombrePermiso from permiso as p "
                    + "inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso "
                    + "inner join rol as r on r.idRol = rp.Rol_idRol "
                    + "where r.nombreRol = '" + rol.getNombre() + "';";
            PreparedStatement pat2 = conn.prepareStatement(sql);
            ResultSet rs2 = pat2.executeQuery();
            while (rs2.next()) {
                Permiso permiso = new Permiso();
                permiso.setIdPermiso(rs2.getInt("idPermiso"));
                permiso.setNombrePermiso(rs2.getString("nombrePermiso"));
                rol.getPermisos().add(permiso);
            }
            rs2.close();
            pat2.close();
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rol;
    }

    /**
     *
     * @param dto
     * @return
     */
    @Override
    public boolean actualizar(Rol dto) {
        try {
            String sql = "update from rol set nombreRol=" + dto.getNombre() + ""
                    + "where idRol=" + dto.getId();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int numero = pat.executeUpdate();
            if (numero > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "delete from rol where idRol=" + clave;
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
    public LinkedList<Rol> listar() {
        LinkedList<Rol> roles = new LinkedList();
        try {
            String sql = "select * from rol";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombreRol"));
                rol.setNombreCorto(rs.getString("nombreCorto"));
                sql = "select p.* from permiso as p \n"
                        + "inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso\n"
                        + "inner join rol as r on r.idRol = rp.Rol_idRol\n"
                        + "where r.nombreRol = '" + rol.getNombre() + "';";
                PreparedStatement pat2 = conn.prepareStatement(sql);
                ResultSet rs2 = pat2.executeQuery();
                while (rs2.next()) {
                    Permiso permiso = new Permiso();
                    permiso.setIdPermiso(rs2.getInt("idPermiso"));
                    permiso.setNombrePermiso(rs2.getString("nombrePermiso"));
                    rol.getPermisos().add(permiso);
                }
                roles.add(rol);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }

    /**
     *
     * @param nombre
     * @return
     */
    @Override
    public Rol consultarPorNombre(String nombre) {
        Rol rol = null;
        try {
            String sql = "select * from rol where nombreRol='" + nombre + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                rol = new Rol();
                rol.setId(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombreRol"));
                rol.setNombreCorto(rs.getString("nombreCorto"));
            }else{
                return rol;
            }
            sql = "select p.* from permiso as p "
                    + "inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso "
                    + "inner join rol as r on r.idRol = rp.Rol_idRol "
                    + "where r.nombreRol = '" + rol.getNombre() + "';";
            PreparedStatement pat2 = conn.prepareStatement(sql);
            ResultSet rs2 = pat2.executeQuery();
            while (rs2.next()) {
                Permiso permiso = new Permiso();
                permiso.setIdPermiso(rs2.getInt("idPermiso"));
                permiso.setNombrePermiso(rs2.getString("nombrePermiso"));
                rol.getPermisos().add(permiso);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rol;
    }

    /**
     *
     * @param permiso
     * @param rol
     * @return
     */
    @Override
    public boolean agregarPermiso(Permiso permiso, Rol rol) {
        try {
            String sql = "insert into rol_has_permiso (Rol_idRol,Permiso_idPermiso) values(?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setInt(1, rol.getId());
            pat.setInt(2, permiso.getIdPermiso());
            pat.execute();
            pat.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param permiso
     * @param rol
     * @return
     */
    @Override
    public boolean removerPermiso(Permiso permiso, Rol rol) {
        try {
            String sql = "delete from rol_has_permiso where Rol_idRol=" + rol.getId() + " and Permiso_idPermiso=" + permiso.getIdPermiso();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int delete = pat.executeUpdate();
            pat.close();
            return delete > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

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

/**
 * Clase de objeto de acceso a datos de los permisos
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-24
 */
public class PermisosDao implements IPermisosDao{

    /**
     *
     * @param permiso
     * @return
     */
    @Override
    public boolean crear(Permiso permiso) {
        try {
            String sql= "insert into permiso (nombrePermiso) values ('"+permiso.getNombrePermiso()+"');";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            boolean insert=pat.execute();
            pat.close();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(PermisosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public Permiso consultar(String clave) {
        Permiso permiso =null;
        try {            
            String sql = "select * from permiso where idPermiso ="+clave+";";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            permiso = new Permiso();
            if(rs.next()){
                permiso.setIdPermiso(rs.getInt("idPermiso"));
                permiso.setNombrePermiso(rs.getString("nombrePermiso"));
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(PermisosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permiso;
    }

    /**
     *
     * @param dto
     * @return
     * @deprecated
     */
    @Deprecated
    @Override
    public boolean actualizar(Permiso dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public boolean eliminar(String clave) {
        try {
            String sql = "delete from permiso where idPermiso =" + clave + "";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int delete = pat.executeUpdate();
            pat.close();
            if (delete > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public LinkedList<Permiso> listar() {
        LinkedList <Permiso> permisos= null;
        Permiso permiso =null;
        try {            
            String sql = "select * from permiso ";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();    
            permisos = new LinkedList();
            while(rs.next()){
                permiso = new Permiso();
                permiso.setIdPermiso(rs.getInt("idPermiso"));
                permiso.setNombrePermiso(rs.getString("nombrePermiso"));
                permisos.add(permiso);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(PermisosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return permisos;
    }

   
}

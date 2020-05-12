package modelo.dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.Rol;

/**
 *
 * @author Santiago Pérez
 */
public class RolesDao implements IRolesDao{

    @Override
    public boolean crear(Rol rol) {
        try {
            String sql = "insert into rol (nombreRol) values (?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(0, rol.getNombre());
            return pat.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Rol consultar(String clave) {
        Rol rol=null;
        try {
            String sql = "select * from rol where idRol="+clave;
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while(rs.next()){
                rol = new Rol();
                rol.setId(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombre"));
            }
            sql = "select p.nombrePermiso as nombrePermiso from permiso as p \n" +
                  "inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso\n" +  
                  "inner join rol as r on r.idRol = rp.Rol_idRol\n" +
                  "where r.nombreRol = \""+rol.getNombre()+"\";";
            PreparedStatement pat2 = conn.prepareStatement(sql);
            ResultSet rs2 = pat2.executeQuery();
            while(rs.next()){
                String permiso = rs.getString("nombrePermiso");
                rol.getPermisos().add(permiso);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rol;
    }

    @Override
    public boolean actualizar(Rol dto) {
        try {
            String sql = "update from rol set nombreRol="+dto.getNombre()+""
                    + "where idRol="+dto.getId();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int numero = pat.executeUpdate();
            if(numero>0){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String clave) {
        try {
            String sql = "delete from rol where idRol="+clave;
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            return pat.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<Rol> listar() {
        LinkedList<Rol> roles = new LinkedList();
        try {
            String sql = "select * from rol";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            while(rs.next()){
                Rol rol = new Rol();
                rol.setId(rs.getInt("idRol"));
                rol.setNombre(rs.getString("nombre"));
                roles.add(rol);
            }
            for (int i = 0; i < roles.size(); i++) {
                sql = "select p.nombrePermiso as nombrePermiso from permiso as p \n" +
                  "inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso\n" +  
                  "inner join rol as r on r.idRol = rp.Rol_idRol\n" +
                  "where r.nombreRol = \""+roles.get(i).getNombre()+"\";";
                PreparedStatement pat2 = conn.prepareStatement(sql);
                ResultSet rs2 = pat2.executeQuery();
                while(rs.next()){
                    String permiso = rs.getString("nombrePermiso");
                    roles.get(i).getPermisos().add(permiso);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }
    
}

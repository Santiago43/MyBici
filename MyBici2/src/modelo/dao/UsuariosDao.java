package modelo.dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.Empleado;
import modelo.dto.Rol;
import modelo.dto.Usuario;

/**
 * Clase usuarios dao
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-13
 */
public class UsuariosDao implements IUsuariosDao {

    @Override
    public boolean crear(Usuario usuario) {
        try {
            String sql = "insert into usuario (usuario,Rol_idRol,contraseña) values (?,?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(0, usuario.getUsuario());
            pat.setInt(1, usuario.getRol().getId());
            pat.setString(2, usuario.getContraseña());
            boolean insert = pat.execute();
            pat.close();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Usuario consultar(String clave) {
        Usuario usuario = null;
        try {
            String sql = "select * from usuario where usuario='" + clave + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            int idRol = 0;
            while (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                idRol = rs.getInt("Rol_idRol");
            }
            sql = "select * from rol where idRol=" + idRol;
            PreparedStatement pat2 = conn.prepareStatement(sql);
            ResultSet rs2 = pat2.executeQuery();
            if (rs2.next()) {
                Rol rol = new Rol();
                rol.setId(rs2.getInt("idRol"));
                rol.setNombre(rs2.getString("nombreRol"));
                usuario.setRol(rol);
            }
            rs2.close();
            rs.close();
            pat.close();
            pat2.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    @Override
    public boolean actualizar(Usuario usuario) {
        try {
            String sql = "update usuario"
                    + "set Empleado_Persona_cedula = "+usuario.getEmpleado().getCedula()+","
                    + " Rol_idRol="+usuario.getRol().getId()+","
                    + "contraseña='"+usuario.getContraseña()+"'"
                    + "where usuario='"+usuario.getUsuario()+"'; ";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int update = pat.executeUpdate();
            pat.close();
            return update>0;
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String clave) {
        try {
            String sql = "delete from usuario where usuario='"+clave+"'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int delete = pat.executeUpdate();
            pat.close();
            return delete>0;
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList listar() {
        LinkedList<Usuario> usuarios = null;
        try {
            String sql = "select * from usuario ";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            int idRol = 0;
            usuarios = new LinkedList();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                idRol = rs.getInt("Rol_idRol");
                String sql2 = "select * from rol where idRol=" + idRol;
                PreparedStatement pat2 = conn.prepareStatement(sql2);
                ResultSet rs2 = pat2.executeQuery();
                if (rs2.next()) {
                    Rol rol = new Rol();
                    rol.setId(rs2.getInt("idRol"));
                    rol.setNombre(rs2.getString("nombreRol"));
                    usuario.setRol(rol);
                }
                EmpleadosDao empleadosDao=new EmpleadosDao();
                Empleado empleado = empleadosDao.consultar(rs.getString("Empleado_Persona_cedula"));
                usuario.setEmpleado(empleado);
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    @Override
    public boolean insertarPermiso(String permiso, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removerPermiso(String permiso, Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

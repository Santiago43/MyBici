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
import modelo.dto.Permiso;
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

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public boolean crear(Usuario usuario) {
        try {
            String sql = "insert into usuario (usuario,Empleado_Persona_cedula,Rol_idRol,contraseña) values (?,?,?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(1, usuario.getUsuario());
            pat.setString(2, usuario.getEmpleado().getCedula());
            pat.setInt(3, usuario.getRol().getId());
            pat.setString(4, usuario.getContraseña());
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
     * @param clave
     * @return
     */
    @Override
    public Usuario consultar(String clave) {
        Usuario usuario = null;
        try {
            String sql = "select * from usuario where usuario='" + clave + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            int idRol = 0;
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                idRol = rs.getInt("Rol_idRol");
                Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
                usuario.setEmpleado(empleado);
            }else{
                return usuario;
            }
            pat.close();
            sql = "select * from rol where idRol=" + idRol;
            pat = conn.prepareStatement(sql);
            ResultSet rs2 = pat.executeQuery();
            if (rs2.next()) {
                Rol rol = new Rol();
                rol.setId(rs2.getInt("idRol"));
                rol.setNombre(rs2.getString("nombreRol"));
                usuario.setRol(rol);
            }
            usuario.setPermisos(traerPermisos(conn,usuario.getUsuario()));
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    /**
     *
     * @param usuario
     * @return
     */
    @Override
    public boolean actualizar(Usuario usuario) {
        try {
            String sql = "update usuario"
                    + "set Empleado_Persona_cedula = " + usuario.getEmpleado().getCedula() + ","
                    + " Rol_idRol=" + usuario.getRol().getId() + ","
                    + "contraseña='" + usuario.getContraseña() + "'"
                    + "where usuario='" + usuario.getUsuario() + "'; ";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.executeUpdate();
            pat.close();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "delete from usuario where usuario='" + clave + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.executeUpdate();
            pat.close();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
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
                EmpleadosDao empleadosDao = new EmpleadosDao();
                Empleado empleado = empleadosDao.consultar(rs.getString("Empleado_Persona_cedula"));
                usuario.setEmpleado(empleado);
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuarios;
    }

    /**
     *
     * @param permiso
     * @param usuario
     * @return
     */
    @Override
    public boolean insertarPermiso(Permiso permiso, Usuario usuario) {
        try {
            String sql = "insert into usuario_has_permiso (Usuario_idUsuario,Permiso_idPermiso) values(?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(1, usuario.getUsuario());
            pat.setInt(2, permiso.getIdPermiso());
            boolean insert = pat.execute();
            pat.close();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param permiso
     * @param usuario
     * @return
     */
    @Override
    public boolean removerPermiso(Permiso permiso, Usuario usuario) {
        try {
            String sql = "delete from usuario_has_permiso where Usuario_usuario= '" + usuario.getUsuario() + "' and Permiso_idPermiso=" + permiso.getIdPermiso();
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
    /**
     * 
     * @param conn
     * @param usuario
     * @return
     * @throws SQLException 
     */
    private LinkedList<Permiso> traerPermisos(Connection conn,String usuario) throws SQLException {
        LinkedList <Permiso> permisos=null;
        String sql = "select p.* from permiso as p "
                + "inner join usuario_has_permiso as up on up.Permiso_idPermiso = p.idPermiso "
                + "inner join usuario as u on u.usuario = up.Usuario_usuario "
                + "where u.usuario = '"+usuario+"';";
        PreparedStatement pat = conn.prepareStatement(sql);
        ResultSet rs = pat.executeQuery();
        permisos=new LinkedList();
        while(rs.next()){
            Permiso permiso = new Permiso();
            permiso.setIdPermiso(rs.getInt("idPermiso"));
            permiso.setNombrePermiso(rs.getString("nombrePermiso"));
            permisos.add(permiso);
        }
        return permisos;
    }

    @Override
    public Usuario consultarPorCedula(String cedula) {
        Usuario usuario = null;
        try {
            String sql = "select * from usuario where Empleado_Persona_cedula='" + cedula + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            int idRol = 0;
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContraseña(rs.getString("contraseña"));
                idRol = rs.getInt("Rol_idRol");
                Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
                usuario.setEmpleado(empleado);
            }else{
                return usuario;
            }
            pat.close();
            sql = "select * from rol where idRol=" + idRol;
            pat = conn.prepareStatement(sql);
            ResultSet rs2 = pat.executeQuery();
            if (rs2.next()) {
                Rol rol = new Rol();
                rol.setId(rs2.getInt("idRol"));
                rol.setNombre(rs2.getString("nombreRol"));
                usuario.setRol(rol);
            }
            usuario.setPermisos(traerPermisos(conn,usuario.getUsuario()));
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

}

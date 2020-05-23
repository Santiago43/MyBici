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
 * @author Santiago Pérez
 */
public class UsuariosDao implements IUsuariosDao{
    
    @Override
    public boolean crear(Usuario usuario) {
         try {
            String sql = "insert into usuario (usuario,Rol_idRol,contraseña) values (?,?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(0, usuario.getUsuario());
            pat.setInt(1, usuario.getRol().getId());
            pat.setString(2, usuario.getContraseña());
            return pat.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Usuario consultar(String clave) {
        Usuario usuario = null;
        Empleado empleado = null;
        EmpleadosDao dataEmpleado = new EmpleadosDao();
        try {       
            String sql = "select * from usuario where usuario=\""+clave+"\"";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            int idRol=0;
            while(rs.next()){
                usuario = new Usuario();
                empleado = new Empleado();
                usuario.setUsuario(rs.getString("usuario"));
                empleado.setCedula(rs.getInt("Empleado_Persona_cedula"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setEmpleado(empleado);
                idRol=rs.getInt("Rol_idRol");
                dataEmpleado.consultar(Integer.toString(usuario.getEmpleado().getCedula()));
            }
            sql = "select * from rol where idRol="+idRol;
            PreparedStatement pat2 = conn.prepareStatement(sql);
            ResultSet rs2 = pat2.executeQuery();
            while(rs2.next()){
                Rol rol = new Rol();
                rol.setId(rs2.getInt("idRol"));
                rol.setNombre(rs2.getString("nombreRol"));
                usuario.setRol(rol);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    @Override
    public boolean actualizar(Usuario dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

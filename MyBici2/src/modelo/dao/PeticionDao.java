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
import modelo.dto.Peticion;

/**
 * Objeto de acceso a datos de las peticiones
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class PeticionDao implements IPeticionDao {

    @Override
    public boolean crear(Peticion peticion) {
        try {
            String sql = "insert into peticionempleado(Empleado_Persona_cedula,peticion,aprobado) \n"
                    + "values(?,?,?);";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(1, peticion.getEmpleado().getCedula());
            pat.setString(2, peticion.getPeticion());
            pat.setBoolean(3, false);
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PeticionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Peticion consultar(String clave) {
        Peticion peticion = null;
        try {
            String sql = "select * from peticionempleado where idPeticionEmpleado =" + clave;

            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                peticion = new Peticion();
                Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
                peticion.setEmpleado(empleado);
                peticion.setIdPeticionEmpleado(rs.getInt("idPeticionEmpleado"));
                peticion.setPeticion(rs.getString("peticion"));
                peticion.setAprobado(rs.getBoolean("aprobado"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeticionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return peticion;
    }

    @Override
    public boolean actualizar(Peticion peticion) {
        try {
            String sql = "update peticionempleado\n"
                    + "set Empleado_Persona_cedula = '"+peticion.getEmpleado().getCedula()+"',aprobado = '"+peticion.isAprobado()+"',peticion='"+peticion.getPeticion()+"' "
                    + "where idPeticionEmpleado="+peticion.getIdPeticionEmpleado();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PeticionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String clave) {
        try {
            String sql = "delete from peticionempleado where idPeticionEmpleado =" + clave + "";
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

    @Override
    public LinkedList<Peticion> listar() {
        LinkedList <Peticion> peticiones =null;
        try {
            String sql = "select * from peticionempleado";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            peticiones = new LinkedList();
            if (rs.next()) {
                Peticion peticion = new Peticion();
                Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
                peticion.setEmpleado(empleado);
                peticion.setIdPeticionEmpleado(rs.getInt("idPeticionEmpleado"));
                peticion.setPeticion(rs.getString("peticion"));
                peticion.setAprobado(rs.getBoolean("aprobado"));
                peticiones.add(peticion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PeticionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return peticiones;
    }

}

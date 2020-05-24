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
import modelo.dto.Direccion;
import modelo.dto.Empleado;
import modelo.dto.Persona;

/**
 * Clase de objeto de acceso a datos de los empleados
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-14
 */
public class EmpleadosDao implements IEmpleadosDao {

    @Override
    public boolean crear(Empleado empleado) {
        try {
            String sql = "call insertarEmpleado(?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = Conexion.conectado();
            CallableStatement call = conn.prepareCall(sql);
            call.setInt("cedula", empleado.getCedula());
            call.setInt("idDireccion", empleado.getDireccion().getIdDireccion());
            call.setString("primerNombre", empleado.getPrimerNombre());
            call.setString("segundoNombre", empleado.getSegundoNombre());
            call.setString("primerApellido", empleado.getPrimerApellido());
            call.setString("segundoApellido", empleado.getSegundoApellido());
            call.setString("fechaNacimiento", empleado.getFechaNacimiento());
            call.setString("nacionalidad", empleado.getNacionalidad());
            call.setString("genero", String.valueOf(empleado.getGenero()));
            call.setInt("idSede", empleado.getIdSede());
            call.setString("profesion", empleado.getProfesion());
            call.setString("cargo", empleado.getCargo());
            call.setDouble("salario", empleado.getSalario());
            call.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Empleado consultar(String clave) {
        Connection conn = Conexion.conectado();
        String sql = "select * from empleado where Persona_cedula=" + clave;
        Empleado empleado = null;
        Persona persona;
        Direccion direccion;
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            empleado = new Empleado();
            if (rs.next()) {
                empleado.setProfesion(rs.getString("profesion"));
                empleado.setCargo(rs.getString("cargo"));
                empleado.setSalario(rs.getDouble("salario"));
                empleado.setIdSede(rs.getInt("Sede_idSede"));
                sql = "select * from persona where cedula=" + clave;
                pat = conn.prepareStatement(sql);
                rs = pat.executeQuery();
                if (rs.next()) {
                    persona = new Persona();
                    direccion = new Direccion();
                    persona.setPrimerNombre(rs.getString("primerNombre"));
                    persona.setSegundoNombre(rs.getString("segundoNombre"));
                    persona.setPrimerApellido(rs.getString("primerApellido"));
                    persona.setSegundoApellido(rs.getString("segundoApellido"));
                    persona.setFechaNacimiento(rs.getDate("fechaNacimiento").toString());
                    persona.setNacionalidad(rs.getString("nacionalidad"));
                    persona.setGenero(rs.getString("genero"));
                    direccion.setIdDireccion(rs.getInt("Direccion_idDireccion"));
                    persona.setDireccion(direccion);
                }

            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleado;
    }

    @Override
    public boolean actualizar(Empleado dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Empleado> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

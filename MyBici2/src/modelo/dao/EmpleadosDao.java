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
import modelo.dto.Calle;
import modelo.dto.Carrera;
import modelo.dto.Direccion;
import modelo.dto.Empleado;
import modelo.dto.Telefono;

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
            boolean insert = call.execute();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Empleado consultar(String clave) {
        Connection conn = Conexion.conectado();
        Empleado empleado = null;
        String sql = "select p.cedula, p.primerNombre, p.segundoNombre,p.primerApellido, p.segundoApellido, p.Direccion_idDireccion, p.fechaNacimiento, p.nacionalidad, p.genero, e.profesion, e.cargo, e.salario, e.Sede_idSede  from persona as p\n"
                + "inner join empleado as e on p.cedula=e.Persona_cedula\n"
                + "where e.Persona_cedula =" + clave;
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                empleado = new Empleado();
                empleado.setCedula(rs.getInt("cedula"));
                empleado.setPrimerNombre(rs.getString("primerNombre"));
                empleado.setSegundoNombre(rs.getString("segundoNombre"));
                empleado.setPrimerApellido(rs.getString("primerApellido"));
                empleado.setSegundoApellido(rs.getString("segundoApellido"));
                empleado.setFechaNacimiento(rs.getDate("fechaNacimiento").toString());
                empleado.setProfesion(rs.getString("profesion"));
                empleado.setCargo(rs.getString("cargo"));
                empleado.setSalario(rs.getDouble("salario"));
                empleado.setIdSede(rs.getInt("Sede_idSede"));
                String idDireccion = rs.getString("Direccion_idDireccion");
                String sql2 = "select cal.idCalle,cal.numeroCalle,cal.letraCalle,cal.bis as bisCalle,cal.sur,car.idCarrera,car.numeroCarrera,car.letraCarrera,car.bis as bisCarrera,car.este from direccion as d"
                        + "inner join calle as cal on cal.idCalle = d.Calle_idCalle"
                        + "inner join carrera as car on car.idCarrera = d.Carrera_idCarrera"
                        + "where d.idDireccion =" + idDireccion;
                PreparedStatement pat2 = conn.prepareStatement(sql2);
                ResultSet rs2 = pat2.executeQuery();
                Direccion direccion = new Direccion();
                Calle calle = new Calle();
                Carrera carrera = new Carrera();
                direccion.setIdDireccion(Integer.parseInt(idDireccion));
                if (rs2.next()) {
                    calle.setIdCalle(rs.getInt("idCalle"));
                    calle.setNumeroCalle(rs2.getInt("numeroCalle"));
                    calle.setLetraCalle(rs2.getString("letraCalle").charAt(0));
                    calle.setBis(rs2.getBoolean("bisCalle"));
                    calle.setSur(rs2.getBoolean("sur"));
                    carrera.setIdCarrera(rs.getInt("idCarrera"));
                    carrera.setNumeroCarrera(rs.getInt("numeroCarrera"));
                    carrera.setLetraCarrera(rs.getString("letraCarrera").charAt(0));
                    carrera.setBis(rs2.getBoolean("bisCarrera"));
                    carrera.setEste(rs.getBoolean("este"));
                }
                direccion.setCalle(calle);
                direccion.setCarrera(carrera);
                empleado.setDireccion(direccion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleado;
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        try {
            String sql = "update persona"
                    + "set primerNombre='"+empleado.getPrimerNombre()+"',"
                    + "segundoNombre='"+empleado.getSegundoNombre()+"',"
                    + "primerApellido='"+empleado.getPrimerApellido()+"',"
                    + "segundoApellido="+empleado.getSegundoApellido()+"',"
                    + "fechaNacimiento="+empleado.getFechaNacimiento()+"',"
                    + "nacionalidad="+empleado.getNacionalidad()+"',"
                    + "genero='"+empleado.getGenero()+"',"
                    + "Direccion_idDireccion="+empleado.getDireccion().getIdDireccion()
                    + "where persona.cedula ="+empleado.getCedula()+";";
            String sql2 = "update empleado"
                    + "set cargo='"+empleado.getCargo()+"',"
                    + "profesion='"+empleado.getProfesion()+"',"
                    + "salario="+empleado.getSalario()
                    + "Sede_idSede="+empleado.getIdSede()
                    + "where empleado.Persona_cedula='"+empleado.getCedula()+"'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int update1=pat.executeUpdate();
            PreparedStatement pat2 = conn.prepareStatement(sql2);
            int update2=pat2.executeUpdate();
            pat.close();
            pat2.close();
            if(update1>0){
                if(update2>0){
                    return true;
                }
            }   
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String clave) {
        try {
            String sql = "delete from persona where cedula ='"+clave+"'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int delete = pat.executeUpdate();
            pat.close();
            if(delete>0){
                return true;
            }  
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<Empleado> listar() {
        LinkedList<Empleado> empleados = null;
        Connection conn = Conexion.conectado();
        String sql = "select p.cedula, p.primerNombre, p.segundoNombre,p.primerApellido, p.segundoApellido, p.Direccion_idDireccion, p.fechaNacimiento, p.nacionalidad, p.genero, e.profesion, e.cargo, e.salario, e.Sede_idSede  from persona as p"
                + "inner join empleado as e on p.cedula=e.Persona_cedula";
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            empleados= new LinkedList();
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setCedula(rs.getInt("p.cedula"));
                empleado.setPrimerNombre(rs.getString("p.primerNombre"));
                empleado.setSegundoNombre(rs.getString("p.segundoNombre"));
                empleado.setPrimerApellido(rs.getString("p.primerApellido"));
                empleado.setSegundoApellido(rs.getString("p.segundoApellido"));
                empleado.setFechaNacimiento(rs.getDate("p.fechaNacimiento").toString());
                empleado.setProfesion(rs.getString("e.profesion"));
                empleado.setCargo(rs.getString("e.cargo"));
                empleado.setSalario(rs.getDouble("e.salario"));
                empleado.setIdSede(rs.getInt("e.Sede_idSede"));
                String idDireccion = rs.getString("p.Direccion_idDireccion");
                String sql2 = "select cal.idCalle,cal.numeroCalle,cal.letraCalle,cal.bis as bisCalle,cal.sur,car.idCarrera,car.numeroCarrera,car.letraCarrera,car.bis as bisCarrera,car.este from direccion as d\n"
                        + "inner join calle as cal on cal.idCalle = d.Calle_idCalle\n"
                        + "inner join carrera as car on car.idCarrera = d.Carrera_idCarrera\n"
                        + "where d.idDireccion =" + idDireccion;
                PreparedStatement pat2 = conn.prepareStatement(sql2);
                ResultSet rs2 = pat2.executeQuery();
                Direccion direccion = new Direccion();
                Calle calle = new Calle();
                Carrera carrera = new Carrera();
                direccion.setIdDireccion(Integer.parseInt(idDireccion));
                if (rs2.next()) {
                    calle.setIdCalle(rs.getInt("idCalle"));
                    calle.setNumeroCalle(rs2.getInt("numeroCalle"));
                    calle.setLetraCalle(rs2.getString("letraCalle").charAt(0));
                    calle.setBis(rs2.getBoolean("bisCalle"));
                    calle.setSur(rs2.getBoolean("sur"));
                    carrera.setIdCarrera(rs.getInt("idCarrera"));
                    carrera.setNumeroCarrera(rs.getInt("numeroCarrera"));
                    carrera.setLetraCarrera(rs.getString("letraCarrera").charAt(0));
                    carrera.setBis(rs2.getBoolean("bisCarrera"));
                    carrera.setEste(rs.getBoolean("este"));
                }
                direccion.setCalle(calle);
                direccion.setCarrera(carrera);
                empleado.setDireccion(direccion);
                empleados.add(empleado);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return empleados;
    }

    /**
     *
     * @param clave
     * @param telefono
     * @return
     */
    @Override
    public boolean agregarTelefono(String clave, Telefono telefono) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

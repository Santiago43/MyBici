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
import modelo.dto.Cliente;
import modelo.dto.Direccion;
import modelo.dto.Telefono;

/**
 * Clase de objeto de acceso a datos de los clientes
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-23
 */
public class ClienteDao implements IClientesDao {

    /**
     *
     * @param cliente
     * @return
     */
    @Override
    public boolean crear(Cliente cliente) {
        try {
            String sql = "call insertarcliente(?,?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = Conexion.conectado();
            CallableStatement call = conn.prepareCall(sql);
            call.setInt("cedula", cliente.getCedula());
            call.setInt("idDireccion", cliente.getDireccion().getIdDireccion());
            call.setString("primerNombre", cliente.getPrimerNombre());
            call.setString("segundoNombre", cliente.getSegundoNombre());
            call.setString("primerApellido", cliente.getPrimerApellido());
            call.setString("segundoApellido", cliente.getSegundoApellido());
            call.setString("fechaNacimiento", cliente.getFechaNacimiento());
            call.setString("nacionalidad", cliente.getNacionalidad());
            call.setString("genero", String.valueOf(cliente.getGenero()));
            boolean insert = call.execute();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public Cliente consultar(String clave) {
        Connection conn = Conexion.conectado();
        Cliente cliente = null;
        String sql = "select p.cedula, p.primerNombre, p.segundoNombre,p.primerApellido, p.segundoApellido, p.Direccion_idDireccion, p.fechaNacimiento, p.nacionalidad, p.genero, e.profesion, e.cargo, e.salario, e.Sede_idSede  from persona as p\n"
                + "inner join cliente as e on p.cedula=e.Persona_cedula\n"
                + "where e.Persona_cedula =" + clave;
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCedula(rs.getInt("cedula"));
                cliente.setPrimerNombre(rs.getString("primerNombre"));
                cliente.setSegundoNombre(rs.getString("segundoNombre"));
                cliente.setPrimerApellido(rs.getString("primerApellido"));
                cliente.setSegundoApellido(rs.getString("segundoApellido"));
                cliente.setFechaNacimiento(rs.getDate("fechaNacimiento").toString());
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
                cliente.setDireccion(direccion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cliente;
    }

    /**
     *
     * @param cliente
     * @return
     */
    @Override
    public boolean actualizar(Cliente cliente) {
        try {
            String sql = "update persona"
                    + "set primerNombre='" + cliente.getPrimerNombre() + "',"
                    + "segundoNombre='" + cliente.getSegundoNombre() + "',"
                    + "primerApellido='" + cliente.getPrimerApellido() + "',"
                    + "segundoApellido=" + cliente.getSegundoApellido() + "',"
                    + "fechaNacimiento=" + cliente.getFechaNacimiento() + "',"
                    + "nacionalidad=" + cliente.getNacionalidad() + "',"
                    + "genero='" + cliente.getGenero() + "',"
                    + "Direccion_idDireccion=" + cliente.getDireccion().getIdDireccion()
                    + "where persona.cedula =" + cliente.getCedula() + ";";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int update1 = pat.executeUpdate();
            pat.close();
            if (update1 > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "delete from persona where cedula ='" + clave + "'";
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
    public LinkedList<Cliente> listar() {
        LinkedList<Cliente> clientes = null;
        Connection conn = Conexion.conectado();
        String sql = "select p.cedula, p.primerNombre, p.segundoNombre,p.primerApellido, p.segundoApellido, p.Direccion_idDireccion, p.fechaNacimiento, p.nacionalidad, p.genero  from persona as p"
                + "inner join cliente as c on p.cedula=c.Persona_cedula";
        try {
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            clientes = new LinkedList();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCedula(rs.getInt("p.cedula"));
                cliente.setPrimerNombre(rs.getString("p.primerNombre"));
                cliente.setSegundoNombre(rs.getString("p.segundoNombre"));
                cliente.setPrimerApellido(rs.getString("p.primerApellido"));
                cliente.setSegundoApellido(rs.getString("p.segundoApellido"));
                cliente.setFechaNacimiento(rs.getDate("p.fechaNacimiento").toString());
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
                cliente.setDireccion(direccion);
                clientes.add(cliente);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    /**
     *
     * @param cliente
     * @param telefono
     * @return
     */
    @Override
    public boolean agregarTelefono(Cliente cliente, Telefono telefono) {
        /**
         * create procedure agregarTelefonoAPersona ( in tipo varchar(6), in
         * cedula int, in numeroTelefono int )
         */
        try {
            String sql = "call agregarTelefonoAPersona (?,?,?)";
            Connection conn = Conexion.conectado();
            CallableStatement call = conn.prepareCall(sql);
            call.setString("tipo",telefono.getTipoTelefono());
            call.setInt("cedula", cliente.getCedula());
            call.setInt("numeroTelefono", Integer.parseInt(telefono.getNumeroTelefonico()));
            boolean insert = call.execute();
            call.close();           
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

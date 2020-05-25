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

/**
 * Clase de objeto de acceso a datos de las direcciones
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-23
 */
public class DireccionDao implements IDireccionDao {

    /**
     *
     * @param direccion
     * @return
     */
    @Override
    public boolean crear(Direccion direccion) {
        try {
            String sql = "call insertarDireccion(?,?,?,?,?,?,?,?)";
            Connection conn = Conexion.conectado();
            CallableStatement call = conn.prepareCall(sql);
            call.setInt("numeroCalle", direccion.getCalle().getNumeroCalle());
            call.setString("letraCalle", String.valueOf(direccion.getCalle().getLetraCalle()));
            call.setBoolean("bisCalle", direccion.getCalle().isBis());
            call.setBoolean("sur", direccion.getCalle().isSur());
            call.setInt("numeroCarrera", direccion.getCarrera().getNumeroCarrera());
            call.setString("letraCarrera", String.valueOf(direccion.getCarrera().getLetraCarrera()));
            call.setBoolean("bisCarrera", direccion.getCarrera().isBis());
            call.setBoolean("este", direccion.getCarrera().isEste());
            boolean insert = call.execute();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(DireccionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @param clave
     * @return
     */
    @Override
    public Direccion consultar(String clave) {
        Direccion direccion = null;
        try {
            String sql = "select * from direccion as d "
                    + "inner join calle as cal on cal.idCalle = d.Calle_idCalle "
                    + "inner join carrera as car on car.idCarrera = d.Carrera_idCarrera "
                    + "where d.idDireccion='" + clave + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            direccion = new Direccion();
            Calle calle = new Calle();
            Carrera carrera = new Carrera();
            direccion.setIdDireccion(Integer.parseInt(clave));
            if (rs.next()) {
                calle.setIdCalle(rs.getInt("cal.idCalle"));
                calle.setNumeroCalle(rs.getInt("cal.numeroCalle"));
                calle.setLetraCalle(rs.getString("cal.letraCalle").charAt(0));
                calle.setBis(rs.getBoolean("cal.bis"));
                calle.setSur(rs.getBoolean("cal.sur"));
                carrera.setIdCarrera(rs.getInt("car.idCarrera"));
                carrera.setNumeroCarrera(rs.getInt("car.numeroCarrera"));
                carrera.setLetraCarrera(rs.getString("car.letraCarrera").charAt(0));
                carrera.setBis(rs.getBoolean("car.bis"));
                carrera.setEste(rs.getBoolean("car.este"));
            }
            direccion.setCalle(calle);
            direccion.setCarrera(carrera);
        } catch (SQLException ex) {
            Logger.getLogger(DireccionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return direccion;
    }

    /**
     *
     * @param direccion
     * @return
     */
    @Override
    public boolean actualizar(Direccion direccion) {
        try {
            String sql = "call actualizarDireccion(?,?,?,?,?,?,?,?,?,?,?)";
            Connection conn = Conexion.conectado();
            CallableStatement call = conn.prepareCall(sql);
            call.setInt("_idDireccion", direccion.getIdDireccion());
            call.setInt("_idCalle", direccion.getCalle().getIdCalle());
            call.setInt("_numeroCalle", direccion.getCalle().getNumeroCalle());
            call.setString("_letraCalle", String.valueOf(direccion.getCalle().getLetraCalle()));
            call.setBoolean("_bisCalle", direccion.getCalle().isBis());
            call.setBoolean("_sur", direccion.getCalle().isSur());
            call.setInt("_idCarrera", direccion.getCarrera().getIdCarrera());
            call.setInt("_numeroCarrera", direccion.getCarrera().getNumeroCarrera());
            call.setString("_letraCarrea", String.valueOf(direccion.getCarrera().getLetraCarrera()));
            call.setBoolean("_bisCarrera", direccion.getCarrera().isBis());
            call.setBoolean("_este", direccion.getCarrera().isEste());
            boolean update = call.execute();
            call.close();
            return update;
        } catch (SQLException ex) {
            Logger.getLogger(DireccionDao.class.getName()).log(Level.SEVERE, null, ex);
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
            String sql = "delete from direccion where idDireccion='" + clave + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int delete = pat.executeUpdate();
            pat.close();
            return delete > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DireccionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Deprecated
    @Override
    public LinkedList<Direccion> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int consultarIdUltimaDireccion() {
        try {
            int idDireccion =0;
            String sql = "select idDireccion from direccion order by idDireccion desc limit 1";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            if(rs.next()){
                idDireccion = rs.getInt("idDireccion");
            }
            rs.close();
            pat.close();
            return idDireccion;
        } catch (SQLException ex) {
            Logger.getLogger(DireccionDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}

package modelo.dao;

import conexion.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.dto.EquipoOficina;
import modelo.dto.Inventario;
import modelo.dto.Mercancia;
import modelo.dto.Sede;
import modelo.dto.Taller;
import modelo.dto.Telefono;

/**
 * Clase de acceso a datos de las sedes
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-13
 */
public class SedeDao implements ISedeDao {
    /**
     * 
     * @param sede
     * @return 
     */
    @Override
    public boolean crear(Sede sede) {
        try {
            String sql = "call crearSede(?,?);";
            Connection conn = Conexion.conectado();
            CallableStatement call = conn.prepareCall(sql);
            call.setInt("idDireccion", sede.getDireccion().getIdDireccion());
            call.setString("nombreSede", sede.getNombreSede());
            boolean insert = call.execute();
            call.close();
            return insert;
        } catch (SQLException ex) {
            Logger.getLogger(SedeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    /**
     * 
     * @param clave
     * @return 
     */
    @Override
    public Sede consultar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * 
     * @param sede
     * @return 
     */
    @Override
    public boolean actualizar(Sede sede) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * 
     * @param clave
     * @return 
     */
    @Override
    public boolean eliminar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * 
     * @return 
     */
    @Override
    public LinkedList<Sede> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    /**
     * 
     * @param equipoOficina
     * @return 
     */
    @Override
    public boolean agregarEquipoOficina(EquipoOficina equipoOficina) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param taller
     * @return
     */
    @Override
    public boolean agregarTaller(Taller taller) {
        try {
            String sql = "insert into taller (Sede_idSede,totalVentas) values(?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setInt(1, taller.getSede().getIdSede());
            pat.setDouble(2, 0);
            boolean insert=pat.execute();
            pat.close();
            return insert;           
        } catch (SQLException ex) {
            Logger.getLogger(SedeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean agregarAInventario(Inventario inventario, Mercancia mercancia) {
        String sql="";
        
        return false;
    }

    
}

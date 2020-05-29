/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dto.Bicicleta;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimienroBicicleta;

/**
 *
 * @author Andrés C. López R.
 */
public class TrabajosDAO {

    String sql;
    Connection conn = Conexion.conectado();
    PreparedStatement pat;
    ResultSet rs;
    BicicletaDao bicicletaDao = new BicicletaDao();
    Bicicleta bici = new Bicicleta();
    FacturaVenta factura = new FacturaVenta();
    FacturaDao facturaV = new FacturaDao();
    
    
     public int idMantenimineto(){
        int id = 0;
        try {
            sql = "select idMantenimientoBicicleta from mantenimientobicicleta order by idMantenimientoBicicleta desc limit 1";
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while (rs.next()) {
                id = rs.getInt("idMantenimientoBicicleta");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }    

    public boolean crear(Bicicleta bici, MantenimienroBicicleta mantenimiento, FacturaVenta factura) {
        try {
            if (bicicletaDao.crear(bici)) {
                if (facturaV.crear(factura)) {
                    sql = "insert into MantenientoBicicleta (idMantenimientoBicicleta,Bicicleta_marcoSerial,FacturaVenta_id_fventa,descripcion,valorEstimado,fechaEntrega,estado) values (?,?,?,?,?,?,?)";
                    pat = conn.prepareStatement(sql);
                    pat.setInt(1, mantenimiento.getId());
                    pat.setString(2, bici.getMarcoSerial());
                    pat.setInt(3, factura.getId());
                    pat.setString(4, mantenimiento.getDescripccion());
                    pat.setDate(5, mantenimiento.getFechaEntrega());
                    pat.setBoolean(6, mantenimiento.isEstado());
                    pat.execute();
                    return true;
                }
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en el registro del manteniminento:\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public MantenimienroBicicleta consultar(int idMantenimiento) {
        MantenimienroBicicleta mantenimiento = null;
        try {
            sql = "select * from MantenientoBicicleta where idMantenimientoBicicleta =\"" + idMantenimiento + "\"";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            String serial;
            int idFactura;
            while (rs.next()) {
                mantenimiento = new MantenimienroBicicleta();
                mantenimiento.setId(idMantenimiento);
                serial = rs.getString("Bicicleta_marcoSerial");
                idFactura = rs.getInt("FacturaVenta_id_fventa");
                mantenimiento.setDescripccion(rs.getString("descripcion"));
                mantenimiento.setFechaEntrega(rs.getDate("fechaEntrega"));
                mantenimiento.setEstado(rs.getBoolean("estado"));
                bicicletaDao.consultar(serial);
                mantenimiento.setBicicleta(bici);
                facturaV.consultar(Integer.toString(idFactura));
                mantenimiento.setFactura(factura);
                if(bici == null){
                    JOptionPane.showMessageDialog(null, "Error, no se encontro una bicicleta asociada al mantenimiento");
                } else if( factura == null){
                    JOptionPane.showMessageDialog(null, "Error, no se encontro una factura asociada al mantenimiento");
                }
            }
            rs.close();
            pat.close();
            return mantenimiento;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en consulta de mantenimiento\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean actualiazar(MantenimienroBicicleta mantenimiento) {
        try {
            sql = "update MantenientoBicicleta "
                    + "set descripcion = '" + mantenimiento.getDescripccion() +"', "
                    + "fechaEntrega = " + mantenimiento.getFechaEntrega() + "' "
                    + "where idMantenimientoBicicleta = " + mantenimiento.getId() + ";";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.execute();
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean eliminar(MantenimienroBicicleta mantenimiento) {
        try {
            sql = "delete from MantenientoBicicleta where idMantenimientoBicicleta = " + mantenimiento.getId();
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.execute();
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la eliminación del mantenimiento\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     public LinkedList<MantenimienroBicicleta> listar() {
        LinkedList<MantenimienroBicicleta> mantenimientos = new LinkedList();
        try {
            sql = "select * from MantenientoBicicleta";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            int idMantenimiento;
            while (rs.next()) {
                MantenimienroBicicleta mantenimiento;
                idMantenimiento = rs.getInt("idMantenimientoBicicleta");
                mantenimiento = consultar(idMantenimiento);
                mantenimientos.add(mantenimiento);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mantenimientos;
    }
}

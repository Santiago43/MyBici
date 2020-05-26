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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dto.Bicicleta;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimienroBicicleta;

/**
 *
 * @author andre
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

    public boolean crear(Bicicleta bici, MantenimienroBicicleta mantenimiento, FacturaVenta factura) {
        try {
            if (bicicletaDao.crear(bici)) {
                if (facturaV.crear(factura)) {
                    sql = "insert into MantenientoBicicleta (idMantenimientoBicicleta,Bicicleta_marcoSerial,FacturaVenta_id_fventa,descripcion,valorEstimado,fechaEntrega) values (?,?,?,?,?,?)";
                    pat = conn.prepareStatement(sql);
                    pat.setInt(1, mantenimiento.getId());
                    pat.setString(2, bici.getMarcoSerial());
                    pat.setInt(3, factura.getId());
                    pat.setString(4, mantenimiento.getDescripccion());
                    pat.setInt(5, mantenimiento.getValorEstimado());
                    pat.setDate(6, mantenimiento.getFechaEntrega());
                    return pat.execute();
                }
            }
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
                mantenimiento.setValorEstimado(rs.getInt(("valorEstimado")));
                mantenimiento.setFechaEntrega(rs.getDate("fechaEntrega"));
                bicicletaDao.consultar(serial);
                mantenimiento.setBicicleta(bici);
                facturaV.consultar(Integer.toString(idFactura));
                mantenimiento.setFactura(factura);
            }
            return mantenimiento;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en consulta de mantenimiento\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean actualiazar(MantenimienroBicicleta mantenimiento) {
        try {
            //Dependiendo el rol del usuario los datos a actualizar pueden cambiar <- mejorar ese aspecto
            sql = "update MantenientoBicicleta set descripcion = \"" + mantenimiento.getDescripccion() + "\" where idMantenimientoBicicleta = " + mantenimiento.getId();
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos\n" + ex);
            Logger
                    .getLogger(RolesDao.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean eliminar(MantenimienroBicicleta mantenimiento) {
        try {
            //Dependiendo el rol del usuario los datos a actualizar pueden cambiar <- mejorar ese aspecto
            sql = "delete from MantenientoBicicleta where idMantenimientoBicicleta = " + mantenimiento.getId();
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la eliminación del mantenimiento\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

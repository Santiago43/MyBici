/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dto.Bicicleta;
import modelo.dto.Cliente;
import modelo.dto.Empleado;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimienroBicicleta;

/**
 *
 * @author andre
 */
public class TrabajosDAO {

    public boolean crear(Bicicleta bici, MantenimienroBicicleta mantenimiento, FacturaVenta factura) {
        try {
            String sql = "insert into Bicicleta (marcoSerial,grupoMecanico,color,marca,estado) values (?,?,?,?,?)";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.setString(1, bici.getMarcoSerial());
            pat.setString(2, bici.getGrupoMecanico());
            pat.setString(3, bici.getColor());
            pat.setString(4, bici.getMarca());
            pat.setString(5, bici.getEstado());
            pat.execute();
            //En caso de error crear pat2
            sql = "insert into FacturaVenta (id_fventa,Empleado_Persona_cedula,Cliente_Persona_cedula,iva,totalVenta,fecha) values (?,?,?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, factura.getId());
            pat.setString(2, factura.getEmpleado().getCedula());
            pat.setString(3, factura.getCliente().getCedula());
            pat.setDouble(4, factura.getIva());
            pat.setDouble(5, factura.getTotal());
            pat.setDate(6, factura.getFecha());
            pat.execute();
            sql = "insert into MantenientoBicicleta (idMantenimientoBicicleta,Bicicleta_marcoSerial,FacturaVenta_id_fventa,descripcion,valorEstimado,fechaEntrega) values (?,?,?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, mantenimiento.getId());
            pat.setString(2, bici.getMarcoSerial());
            pat.setInt(3, factura.getId());
            pat.setString(4, mantenimiento.getDescripccion());
            pat.setInt(5, mantenimiento.getValorEstimado());
            pat.setDate(6, mantenimiento.getFechaEntrega());
            return pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la incersión de los datos del mantenimiento\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public MantenimienroBicicleta consultar(int idMantenimiento) {
        MantenimienroBicicleta mantenimiento = null;
        try {
            String sql = "select * from MantenientoBicicleta where idMantenimientoBicicleta =\"" + idMantenimiento + "\"";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            String serial = "";
            int idFactura = 0;
            while (rs.next()) {
                mantenimiento = new MantenimienroBicicleta();
                mantenimiento.setId(idMantenimiento);
                serial = rs.getString("Bicicleta_marcoSerial");
                idFactura = rs.getInt("FacturaVenta_id_fventa");
                mantenimiento.setDescripccion(rs.getString("descripcion"));
                mantenimiento.setValorEstimado(rs.getInt(("valorEstimado")));
                mantenimiento.setFechaEntrega(rs.getDate("fechaEntrega"));
            }
            if (mantenimiento != null) {
                Bicicleta bici = new Bicicleta();
                sql = "select * from Bicicleta where marcoSerial =\"" + serial + "\"";
                pat = conn.prepareStatement(sql);
                rs = pat.executeQuery();
                while (rs.next()) {
                    bici.setMarcoSerial(serial);
                    bici.setGrupoMecanico(rs.getString("grupoMecanico"));
                    bici.setColor(rs.getString("color"));
                    bici.setMarca(rs.getString("marca"));
                    bici.setEstado(rs.getString("estado"));
                    mantenimiento.setBicicleta(bici);
                }
                FacturaVenta factura = new FacturaVenta();
                Empleado empleado = new Empleado();
                Cliente cliente = new Cliente();
                sql = "select * from FacturaVenta where id_fventa =\"" + idFactura + "\"";
                pat = conn.prepareStatement(sql);
                rs = pat.executeQuery();
                while (rs.next()) {
                    factura.setId(idFactura);
                    empleado.setCedula(rs.getString("Empleado_Persona_cedula"));
                    factura.setEmpleado(empleado);
                    cliente.setCedula(rs.getString("Cliente_Persona_cedula"));
                    factura.setCliente(cliente);
                    factura.setIva(rs.getDouble("iva"));
                    factura.setFecha(rs.getDate("fecha"));
                    mantenimiento.setFactura(factura);
                }
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
            String sql = "update MantenientoBicicleta set descripcion = \"" + mantenimiento.getDescripccion() + "\" where idMantenimientoBicicleta = " + mantenimiento.getId();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean eliminar(MantenimienroBicicleta mantenimiento) {
        try {
            //Dependiendo el rol del usuario los datos a actualizar pueden cambiar <- mejorar ese aspecto
            String sql = "delete from MantenientoBicicleta where idMantenimientoBicicleta = " + mantenimiento.getId();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la eliminación del registro\n" + ex);
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

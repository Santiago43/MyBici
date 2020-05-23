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
            pat.setString(0, bici.getMarcoSerial());
            pat.setString(1, bici.getGrupoMecanico());
            pat.setString(2, bici.getColor());
            pat.setString(3, bici.getMarca());
            pat.setString(4, bici.getEstado());
            //En caso de error crear pat2
            sql = "insert into MantenientoBicicleta (idMantenimientoBicicleta,Bicicleta_marcoSerial,FacturaVenta_id_fventa,descripcion,valorEstimado,fechaEntrega) values (?,?,?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setInt(0, mantenimiento.getId());
            pat.setString(1, bici.getMarcoSerial());
            pat.setInt(2, factura.getId());
            pat.setString(3, mantenimiento.getDescripccion());
            pat.setInt(4, mantenimiento.getValorEstimado());
            pat.setString(5, mantenimiento.getFechaEntrega());
            sql = "insert into FacturaVenta (id_fventa,Empleado_Persona_cedula,Cliente_Persona_cedula,iva,totalVenta,fecha) values (?,?,?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setInt(0, factura.getId());
            pat.setInt(1, factura.getEmpleado().getCedula());
            pat.setInt(2, factura.getCliente().getCedula());
            pat.setDouble(3, factura.getIva());
            pat.setDouble(4, factura.getTotal());
            pat.setString(5, factura.getFecha());
            JOptionPane.showMessageDialog(null, "Bicicleta Registrada.\nMantenimiento registrado\nFactura generada");
            return pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la incersión de los datos del mantenimiento\n" + ex);
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
                mantenimiento.setId(rs.getInt("idMantenimientoBicicleta"));
                serial = rs.getString("Bicicleta_marcoSerial");
                idFactura = rs.getInt("FacturaVenta_id_fventa");
                mantenimiento.setDescripccion(rs.getString("descripcion"));
                mantenimiento.setValorEstimado(rs.getInt(("valorEstimado")));
                mantenimiento.setFechaEntrega(rs.getString("fechaEntrega"));
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
                }
                FacturaVenta factura = new FacturaVenta();
                Empleado empleado = new Empleado();
                Cliente cliente = new Cliente();
                sql = "select * from FacturaVenta where id_fventa =\"" + idFactura + "\"";
                pat = conn.prepareStatement(sql);
                rs = pat.executeQuery();
                while (rs.next()) {
                    factura.setId(idFactura);
                    empleado.setCedula(rs.getInt("Empleado_Persona_cedula"));
                    factura.setEmpleado(empleado);
                    cliente.setCedula(rs.getInt("Cliente_Persona_cedula"));
                    factura.setCliente(cliente);
                    factura.setIva(rs.getDouble("iva"));
                    factura.setFecha(rs.getString("fecha"));
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en consulta de mantenimiento\n" + ex);
        }
        return mantenimiento;
    }

    public boolean actualiazar(MantenimienroBicicleta mantenimiento) {
        try {
  //Dependiendo el rol del usuario los datos a actualizar pueden cambiar <- mejorar ese aspecto
            String sql = "update MantenientoBicicleta set descripcion = \"" + mantenimiento.getDescripccion() + "\" where id = " + mantenimiento.getId();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la actualización de los datos\n" + ex);
        }
        return false;
    }
    
    public boolean eliminar(MantenimienroBicicleta mantenimiento) {
        try {
  //Dependiendo el rol del usuario los datos a actualizar pueden cambiar <- mejorar ese aspecto
            String sql = "delete from MantenientoBicicleta where id = " + mantenimiento.getId();
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            pat.execute();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la eliminación del registro\n" + ex);
        }
        return false;
    }
}

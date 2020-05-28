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
import modelo.dto.Cliente;
import modelo.dto.Empleado;
import modelo.dto.FacturaVenta;

/**
 *
 * @author Andrés C. López R.
 * @since 25/05/2020
 */
public class FacturaDao implements IFacturaDao {

    Connection conn;
    String sql;
    PreparedStatement pat;
    ResultSet rs;

    @Override
    public boolean crear(FacturaVenta factura) {
        try {
            conn = Conexion.conectado();
            sql = "insert into FacturaVenta (id_fventa,Empleado_Persona_cedula,Cliente_Persona_cedula,iva,totalVenta,fecha) values (?,?,?,?,?,?)";
            pat = conn.prepareStatement(sql);
            pat.setInt(1, factura.getId());
            pat.setInt(2, Integer.parseInt(factura.getEmpleado().getCedula()));
            pat.setInt(3, Integer.parseInt(factura.getCliente().getCedula()));
            pat.setDouble(4, factura.getIva());
            pat.setDouble(5, factura.getTotal());
            pat.setDate(6, factura.getFecha());
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public FacturaVenta consultar(String idFactura) {
        try {
            Empleado empleado = new Empleado();
            Cliente cliente = new Cliente();
            FacturaVenta factura = new FacturaVenta();
            sql = "select * from FacturaVenta where id_fventa =\"" + idFactura + "\"";
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while (rs.next()) {
                factura.setId(Integer.valueOf(idFactura));
                empleado.setCedula(rs.getString("Empleado_Persona_cedula"));
                factura.setEmpleado(empleado);
                cliente.setCedula(rs.getString("Cliente_Persona_cedula"));
                factura.setCliente(cliente);
                factura.setIva(rs.getDouble("iva"));
                factura.setFecha(rs.getDate("fecha"));
                return factura;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean actualizar(FacturaVenta factura) {
        try {
            sql = "update FacturaVenta "
                    + "set Empleado_Persona_cedula = '" + factura.getEmpleado().getCedula() + "', "
                    + "totalVenta = " + factura.getTotal() + " "
                    + "where id_fventa = " + factura.getId() + ";";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BicicletaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String idFacturaV) {
        try {
            sql = "delete from FacturaVenta where id_fventa = " + idFacturaV;
            conn = Conexion.conectado();
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BicicletaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<FacturaVenta> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public double calculoIVA (double iva, double subTotal){
        double total = subTotal * (iva + 1);
        return total;
    }
    
    public double sinIva (double iva, double valor){
        double subTotal = (valor)/(iva + 1);
        return subTotal;
    }
}

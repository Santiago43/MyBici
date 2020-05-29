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
import modelo.dto.Mercancia;
import modelo.dto.Sede;

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
    MercanciaDao mercanciaDao = new MercanciaDao();
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
            pat.setString(6, factura.getFecha());
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int idFactrura() {
        int id = 0;
        try {
            conn = Conexion.conectado();
            sql = "select id_fventa from facturaventa order by id_fventa desc limit 1";
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id_fventa");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    @Override
    public FacturaVenta consultar(String idFactura) {
        try {
            Empleado empleado = new Empleado();
            Cliente cliente = new Cliente();
            FacturaVenta factura = new FacturaVenta();
            conn = Conexion.conectado();
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
                factura.setFecha(rs.getString("fecha"));
                factura.setTotal(rs.getDouble("totalVenta"));
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
        LinkedList<FacturaVenta> facturas = null;
         try {
            sql = "select * from FacturaVenta";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            facturas = new LinkedList();
            FacturaVenta facturaVenta;
            Empleado empleado = new Empleado();
            Cliente cliente = new Cliente();
            while (rs.next()){
                facturaVenta = new FacturaVenta();
                facturaVenta.setId(rs.getInt("id_fventa"));
                empleado.setCedula(rs.getString("Empleado_Persona_cedula"));
                facturaVenta.setEmpleado(empleado);
                cliente.setCedula(rs.getString("Cliente_Persona_cedula"));
                facturaVenta.setCliente(cliente);
                facturaVenta.setIva(rs.getDouble("iva"));
                facturaVenta.setFecha(rs.getString("fecha"));
                facturaVenta.setTotal(rs.getDouble("totalVenta"));
                facturas.add(facturaVenta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return facturas;
    }

    @Override
    public LinkedList<FacturaVenta> listarPorFecha(String fechaInicio, String fechaFinal, Sede sede) {
        LinkedList<FacturaVenta> facturasVenta = null;
        try {
            String sql = "select v.* from facturaventa as v\n"
                    + "inner join empleado as e on v.Empleado_Persona_cedula=e.Persona_cedula\n"
                    + "inner join Sede as s on e.Sede_idSede=s.idSede\n"
                    + "where s.idSede = " + sede.getIdSede() + " and v.fecha between '" + fechaInicio + "' and '" + fechaFinal + "';";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            facturasVenta = new LinkedList();
            while (rs.next()) {
                FacturaVenta facturaVenta = new FacturaVenta();
                facturaVenta.setId(rs.getInt("id_fventa"));
                Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
                Cliente cliente = new ClienteDao().consultar(rs.getString("Cliente_Persona_cedula"));
                facturaVenta.setEmpleado(empleado);
                facturaVenta.setCliente(cliente);
                facturaVenta.setFecha(rs.getString("fecha"));
                facturaVenta.setIva(rs.getDouble("iva"));
                facturaVenta.setTotal(rs.getDouble("totalVenta"));
                facturasVenta.add(facturaVenta);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return facturasVenta;
    }

    public double calculoIVA(double iva, double subTotal) {
        double total = subTotal * (iva + 1);
        return total;
    }

    public double sinIva(double iva, double valor) {
        double subTotal = (valor) / (iva + 1);
        return subTotal;
    }

    public boolean factura_has_mercancia(FacturaVenta factura, LinkedList<Mercancia> articulos) {
        try {
            conn = Conexion.conectado();
            sql = "insert into factura_has_mercancia (FacturaVenta_id_fventa,Mercancia_Objeto_idObjeto) values (?,?)";
            pat = conn.prepareStatement(sql);
            for (Mercancia articulo : articulos) {
                pat.setInt(1, factura.getId());
                pat.setInt(2, (articulo.getIdObjeto()));
                pat.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(FacturaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
     public LinkedList<Mercancia> listarMercancia(String idfv) {
        LinkedList<Mercancia> listarMercancia = null;
         try {
            sql = "select Mercancia_Objeto_idObjeto from factura_has_mercancia where FacturaVenta_id_fventa = " + idfv + ";" ;
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            listarMercancia = new LinkedList();
            Mercancia mercancia;
            Empleado empleado = new Empleado();
            Cliente cliente = new Cliente();
            int codArt;
            while (rs.next()){
                Mercancia check = new Mercancia();
                codArt = (rs.getInt("Mercancia_Objeto_idObjeto"));
                mercancia = mercanciaDao.consultar(String.valueOf(codArt));
                check.setIdObjeto(mercancia.getIdObjeto());
                check.setNombre(mercancia.getNombre());
                check.setCantidad(mercancia.getCantidad());
                listarMercancia.add(check);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolesDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return listarMercancia;
    }
}

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
import modelo.dto.FacturaVenta;
import modelo.dto.Mercancia;

/**
 *
 * @author Andrés C. López R. || ACLXRD
 */
public class MercanciaDao implements IMercanciaDao {

    String sql;
    Connection conn = Conexion.conectado();
    PreparedStatement pat;
    ResultSet rs;
    FacturaVenta facturaVenta = new FacturaVenta();

    @Override
    public boolean crear(Mercancia dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Mercancia consultar(String idArticulo) {
        Mercancia mercancia = null;
        try {            
            sql = "select * from mercancia where Objeto_idObjeto = " + idArticulo + ";";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while(rs.next()){
                mercancia = new Mercancia();
                mercancia.setIdObjeto(Integer.valueOf(idArticulo));
                mercancia.setNombre(rs.getString("nombre"));
                mercancia.setValorAdquisicion(rs.getDouble("valor_adg"));
                mercancia.setPrecioVenta(rs.getDouble("precio_venta"));
                mercancia.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MercanciaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mercancia;
    }

    @Override
    public boolean actualizar(Mercancia dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<Mercancia> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

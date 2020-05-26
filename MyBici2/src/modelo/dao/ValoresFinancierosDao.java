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
import modelo.dto.ValoresFinancieros;

/**
 *
 * @author Andrés C. López R. || ACLXRD
 */
public class ValoresFinancierosDao implements IValoresFinancierosDao {

    Connection conn;
    String sql;
    PreparedStatement pat;
    ResultSet rs;
    ValoresFinancieros valoresFinancieros;
    @Override
    public boolean crear(ValoresFinancieros proveedor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public ValoresFinancieros consultar(String txt) {
        try {
            sql = "select * from  Valores_Financieros";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while(rs.next()){
                valoresFinancieros = new ValoresFinancieros();
                valoresFinancieros.setIva(rs.getDouble("iva"));
                valoresFinancieros.setAuxTrans(rs.getDouble("auxTrans"));
                valoresFinancieros.setSmlv(rs.getDouble("smlv"));
                return valoresFinancieros;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ValoresFinancierosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean actualizar(ValoresFinancieros valoresFinancieros) {
        try {
            sql = "select * from  Valores_Financieros";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while(rs.next()){
                valoresFinancieros = new ValoresFinancieros();
                valoresFinancieros.setIva(rs.getDouble("iva"));
                valoresFinancieros.setAuxTrans(rs.getDouble("auxTrans"));
                valoresFinancieros.setSmlv(rs.getDouble("smlv"));
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ValoresFinancierosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String clave) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LinkedList<ValoresFinancieros> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

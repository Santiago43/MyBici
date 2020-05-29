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

/**
 *
 * @author andre
 */
public class BicicletaDao implements IBicicletaDao {

    String sql;
    Connection conn;
    PreparedStatement pat;
    ResultSet rs;

    @Override
    public boolean crear(Bicicleta bici) {
        try {
            sql = "insert into Bicicleta (marcoSerial,grupoMecanico,color,marca,estado,valorEstimado) values (?,?,?,?,?,?)";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.setString(1, bici.getMarcoSerial());
            pat.setString(2, bici.getGrupoMecanico());
            pat.setString(3, bici.getColor());
            pat.setString(4, bici.getMarca());
            pat.setString(5, bici.getEstado());
            pat.setDouble(6, bici.getValorEstimado());
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(BicicletaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Bicicleta consultar(String serial) {
        Bicicleta bici = null;
        conn = Conexion.conectado();
        try {
            bici = new Bicicleta();
            sql = "select * from Bicicleta where marcoSerial =\"" + serial + "\"";
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while (rs.next()) {
                bici.setMarcoSerial(serial);
                bici.setGrupoMecanico(rs.getString("grupoMecanico"));
                bici.setColor(rs.getString("color"));
                bici.setMarca(rs.getString("marca"));
                bici.setEstado(rs.getString("estado"));
                bici.setValorEstimado(rs.getDouble("valorEstimado"));
                return bici;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BicicletaDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error en consulta de la bicilceta\n" + ex);
        }
        if (bici == null) {
            JOptionPane.showMessageDialog(null, "El serial " + serial + " no corresponde a ninguna bicicleta registrada");
        }
        return bici;
    }

    @Override
    public boolean actualizar(Bicicleta bici) {
        try {
            sql = "update Bicicleta "
                    + "set grupoMecanico = '" + bici.getGrupoMecanico() + "', "
                    + "color = '" + bici.getColor() + "', "
                     + "valorEstimado = '" + bici.getValorEstimado()+ "', "
                    + "estado = '" + bici.getEstado() + "' "
                    + "where marcoSerial = '" + bici.getMarcoSerial() + "';";
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
    public boolean eliminar(String marcoSerial) {
        try {
            String sql = "delete from Bicicleta where marcoSerial = '" + marcoSerial + "'";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int delete = pat.executeUpdate();
            pat.close();
            if (delete > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BicicletaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public LinkedList<Bicicleta> listar() {
        LinkedList<Bicicleta> bicicletas = null;
        Bicicleta bicicleta = null;
        try {

            String sql = "select * from Bicicleta";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();
            bicicletas = new LinkedList();
            while (rs.next()) {
                bicicleta = new Bicicleta();
                bicicleta.setMarcoSerial(rs.getString("marcoSerial"));
                bicicleta.setGrupoMecanico(rs.getString("grupoMecanico"));
                bicicleta.setColor(rs.getString("color"));
                bicicleta.setMarca(rs.getString("marca"));
                bicicleta.setEstado(rs.getString("estado"));
                bicicleta.setValorEstimado(rs.getDouble("valorEstimado"));
                bicicletas.add(bicicleta);
            }
            rs.close();
            pat.close();
        } catch (SQLException ex) {
            Logger.getLogger(BicicletaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bicicletas;
    }
    
    

}

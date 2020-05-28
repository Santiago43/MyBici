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
import modelo.dto.Empleado;
import modelo.dto.Nomina;

/**
 *
 * @author F. PLAZA
 */
public class NominaDao implements INominaDao {

    String sql;
    Connection conn;
    PreparedStatement pat;
    ResultSet rs;

    @Override
    public boolean crear(Nomina nom) {
        try {
            sql = "insert into Nomina(Empleado_Persona_cedula,horasExtra,fechaNomina,auxilioTransporte,descuento,diasAusencia) values(?,?,?,?,?,?)";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.setString(1, nom.getCedula().getCedula());
            pat.setInt(2, nom.getHorasExtra());
            pat.setString(3, nom.getFechaNomina());
            pat.setDouble(4, nom.getAuxTransportedouble());
            pat.setDouble(5, nom.getDescuento());
            pat.setInt(6, nom.getDiasAusencia());
        } catch (SQLException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Nomina consultar(String idNomina) {
        Nomina nom = null;
        try {
            nom = new Nomina();
            Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
            sql = "select * from Nomina where idNomina =\"" + idNomina + "\"";
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            while (rs.next()) {
                nom.setIdNomina(Integer.getInteger(idNomina));
                nom.setCedula(empleado);
                nom.setHorasExtra(rs.getInt("horasExtra"));
                nom.setFechaNomina(rs.getString("fechaNomina"));
                nom.setAuxTransportebool(rs.getBoolean("auxilioTransporte"));
                nom.setAuxTransportedouble(rs.getDouble("valorAuxilio"));
                nom.setDescuento(rs.getDouble("descuento"));
                nom.setDiasAusencia(rs.getInt("diasAusencia"));
                return nom;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error en consulta de la nomina\n" + ex);
        }
        if (nom == null) {
            JOptionPane.showMessageDialog(null, "El ID " + idNomina + " no corresponde a ninguna nomina registrada");
        }
        return nom;
    }

    @Override
    public boolean actualizar(Nomina nom) {
        try{
            sql= "update Nomina "
                    +"set Empleado_Persona_cedula = '"+nom.getCedula() + "',"
                    +"set horasExtra = '"+nom.getHorasExtra()+ "',"
                    +"set fechaNomina = '"+nom.getFechaNomina()+ "',"
                    +"set auxilioTransporte = '"+nom.isAuxTransportebool()+ "',"
                    +"set valorAuxilio = '"+nom.getAuxTransportedouble()+ "',"
                    +"set descuento = '"+nom.getDescuento()+ "',"
                    +"set diasAusencia = '"+nom.getDiasAusencia()+ "';";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.execute();
        }catch(SQLException ex){
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String idNomina) {
        try{
            sql= "delete from Nomina where idNomina =" +idNomina;
             conn = Conexion.conectado();
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        }
    

    @Override
    public LinkedList<Nomina> listar() {
        LinkedList<Nomina> nominas = null;
        Nomina nomina = null;
        try{
             Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
            String sql = "select * from Nomina";
         Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();    
            nominas = new LinkedList(); 
            while(rs.next()){
                nomina = new Nomina();
                nomina.setIdNomina(rs.getInt("idNomina"));
                nomina.setCedula(empleado);
                nomina.setFechaNomina(rs.getString("fechaNomina"));
                nomina.setAuxTransportebool(rs.getBoolean("auxilioTransporte"));
                nomina.setAuxTransportedouble(rs.getDouble("valorAuxilio"));
                nomina.setDescuento(rs.getDouble("descuento"));
                nomina.setDiasAusencia(rs.getInt("diasAusencia"));
                nominas.add(nomina);
            }
            rs.close();
            pat.close();
        }catch(SQLException ex){
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nominas;
    }

}

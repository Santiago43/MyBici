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
            sql = "insert into Nomina(Empleado_Persona_cedula,horasExtra,fechaNomina,auxilioTransporte,auxilioTransporteval,descuento,diasAusencia) values(?,?,?,?,?,?,?)";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.setString(1, nom.getCedula().getCedula());
            pat.setInt(2, nom.getHorasExtra());
            pat.setString(3, nom.getFechaNomina());
            pat.setBoolean(4, nom.isAuxTransportebool());
            pat.setDouble(5, nom.getAuxTransportedouble());
            pat.setDouble(6, nom.getDescuento());
            pat.setInt(7, nom.getDiasAusencia());
            pat.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Nomina consultar(String idNomina) {
         conn = Conexion.conectado();
      Nomina nom = new Nomina();
        try {
          
           
            sql = "select * from Nomina where idNomina =\"" + idNomina + "\"";
            
            
            pat = conn.prepareStatement(sql);
            rs = pat.executeQuery();
            
            while (rs.next()) {
                 Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
                nom.setIdNomina(Integer.parseInt(idNomina));
                nom.setCedula(empleado);
                nom.setHorasExtra(rs.getInt("horasExtra"));
                nom.setFechaNomina(rs.getString("fechaNomina"));
                nom.setAuxTransportebool(rs.getBoolean("auxilioTransporte"));
                nom.setAuxTransportedouble(rs.getDouble("auxilioTransporteval"));
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
                    +"set Empleado_Persona_cedula = '"+nom.getCedula().getCedula() + "',"
                    +" horasExtra = "+nom.getHorasExtra()+ ","
                    +" fechaNomina = '"+nom.getFechaNomina()+ "',"
                    +" auxilioTransporte = "+nom.isAuxTransportebool()+ ","
                    +" descuento = "+nom.getDescuento()+ ","
                    +" diasAusencia = '"+nom.getDiasAusencia()+ "' where idNomina = "+nom.getIdNomina()+";";
            conn = Conexion.conectado();
            pat = conn.prepareStatement(sql);
            pat.executeUpdate();
            return true;
        }catch(SQLException ex){
            Logger.getLogger(NominaDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean eliminar(String idNomina) {
        try {
            String sql = "delete from Nomina where idNomina ="+idNomina+"";
            Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            int delete = pat.executeUpdate();
            pat.close();
            if(delete>0){
                return true;
            }  
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        }
    

    @Override
    public LinkedList<Nomina> listar() {
        LinkedList<Nomina> nominas = null;
        Nomina nomina = null;
        try{
            
            String sql = "select * from Nomina";
         Connection conn = Conexion.conectado();
            PreparedStatement pat = conn.prepareStatement(sql);
            ResultSet rs = pat.executeQuery();    
            nominas = new LinkedList(); 
            while(rs.next()){
                 Empleado empleado = new EmpleadosDao().consultar(rs.getString("Empleado_Persona_cedula"));
                nomina = new Nomina();
                nomina.setIdNomina(rs.getInt("idNomina"));
                nomina.setCedula(empleado);
                nomina.setHorasExtra(rs.getInt("horasExtra"));
                nomina.setFechaNomina(rs.getString("fechaNomina"));
                nomina.setAuxTransportebool(rs.getBoolean("auxilioTransporte"));
                nomina.setAuxTransportedouble(rs.getDouble("auxilioTransporteval"));
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

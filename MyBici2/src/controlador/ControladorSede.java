/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import modelo.dao.SedeDao;
import modelo.dao.UsuariosDao;
import modelo.dto.Sede;
import vista.VistaSede;
import vista.VistaPrincipal;

/**
 *
 * @author F. PLAZA
 */
public class ControladorSede implements ActionListener{
    /**
     * 
     */
    VistaSede vista;
    /**
     * 
     */
    VistaPrincipal vistaAnterior;
    UsuariosDao usuariosDao;
    SedeDao sedeDao;
    /**
     * 
     * @param vista
     * @param vistaAnterior
     * @param usuariosDao 
     */
    public ControladorSede(VistaSede vista, VistaPrincipal vistaAnterior, UsuariosDao usuariosDao) {
        this.sedeDao = new SedeDao();
        
        Sede sede = this.sedeDao.consultar("id");
        LinkedList <Sede> sedes = this.sedeDao.listar();
        
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.usuariosDao = usuariosDao;
        
        this.vistaAnterior.setVisible(false);
        
        this.vista.setVisible(true);
    }
    
    
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}

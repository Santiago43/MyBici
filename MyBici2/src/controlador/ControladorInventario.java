/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.dao.UsuariosDao;
import vista.VistaInventario;
import vista.VistaPrincipal;

/**
 *
 * @author F. PLAZA
 */
public class ControladorInventario implements ActionListener{
    
    VistaInventario vista;
    VistaPrincipal vistaAnterior;
    UsuariosDao usuariosDao;

    public ControladorInventario(VistaInventario vista, VistaPrincipal vistaAnterior, UsuariosDao usuariosDao) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.usuariosDao = usuariosDao;
    }
    
    
    
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}

package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.dao.UsuariosDao;
import vista.VistaLogin;
import vista.VistaPrincipal;

/**
 *
 * @author Santiago Pérez
 */
public class ControladorPrincipal implements ActionListener{
    VistaPrincipal vista;
    VistaLogin vistaAnterior;
    UsuariosDao usuariosDao;

    public ControladorPrincipal(VistaPrincipal vista, VistaLogin vistaAnterior, UsuariosDao usuariosDao) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.vistaAnterior.setVisible(false);
        this.usuariosDao = usuariosDao;
        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

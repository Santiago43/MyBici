package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.dao.EmpleadosDao;
import modelo.dao.RolesDao;
import modelo.dao.UsuariosDao;
import vista.VistaSede;
import vista.VistaLogin;
import vista.VistaPrincipal;
import vista.VistaUsuarios;

/**
 *
 * @author Santiago Pérez
 */
public class ControladorPrincipal implements ActionListener {

    VistaPrincipal vista;
    VistaLogin vistaAnterior;
    UsuariosDao usuariosDao;
    VistaSede vistaInv;
    /**
     * 
     * @param vista
     * @param vistaAnterior
     * @param usuariosDao 
     */
    public ControladorPrincipal(VistaPrincipal vista, VistaLogin vistaAnterior, UsuariosDao usuariosDao) {
        this.vista = vista;
      
        this.vistaAnterior = vistaAnterior;
        this.vistaAnterior.setVisible(false);
        this.usuariosDao = usuariosDao;
        this.vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

       if(e.getSource().equals(this.vista.itemUsuarios)){
           
           ControladorUsuarios controladorUsuarios = new ControladorUsuarios(this.vista, new VistaUsuarios(),this.usuariosDao, new RolesDao(),new EmpleadosDao());
           
       }

    }

}

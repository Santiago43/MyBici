package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.dao.EmpleadosDao;
import modelo.dao.RolesDao;
import modelo.dao.TrabajosDAO;
import modelo.dao.UsuariosDao;
import vista.VistaSede;
import vista.VistaLogin;
import vista.VistaPrincipal;
import vista.VistaTrabajos;
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
        this.vista.itemTrabajos.addActionListener(this);
        this.vistaAnterior = vistaAnterior;
        this.vistaAnterior.setVisible(false);
        this.usuariosDao = usuariosDao;
        this.vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(this.vista.itemUsuarios)) {

            ControladorUsuarios controladorUsuarios;
            controladorUsuarios = new ControladorUsuarios(this.vista, new VistaUsuarios(), this.usuariosDao, new RolesDao(), new EmpleadosDao());

        }
        
        if(e.getSource().equals(this.vista.itemTrabajos)){
            ControladorTrabajos  controladorTrabajos ;
            controladorTrabajos = new ControladorTrabajos(this.vista, new VistaTrabajos(), this.usuariosDao, new RolesDao(), new TrabajosDAO());
        }

    }

}

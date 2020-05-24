package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.dao.EmpleadosDao;
import modelo.dao.RolesDao;
import modelo.dao.TrabajosDAO;
import modelo.dao.UsuariosDao;
import modelo.dto.Usuario;
import vista.VistaSede;
import vista.VistaLogin;
import vista.VistaPrincipal;
import vista.VistaTrabajos;
import vista.VistaUsuarios;

/**
 * Clase controlador principal. Aquí se lleva a cabo el control de la
 * aplicación. Se habilitan o se deshabilitan vistas de acuerdo a los permisos
 * asignados a un usuario
 *
 * @author Santiago Pérez, Andrés Camilo López, Carlos Antonio Plaza
 * @version 1.0
 * @since 2020-05-10
 */
public class ControladorPrincipal implements ActionListener {
    /**
     * 
     */
    private VistaPrincipal vista;
    /**
     * 
     */
    private VistaLogin vistaAnterior;
    private UsuariosDao usuariosDao;
    private VistaSede vistaInv;
    private Usuario usuario;

    /**
     * Constructor de la clase
     * @param vista que es la vista que usa este controlador
     * @param vistaAnterior que es la vista login
     * @param usuariosDao que es el objeto de acceso a datos del controlador
     */
    public ControladorPrincipal(VistaPrincipal vista, VistaLogin vistaAnterior, UsuariosDao usuariosDao, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.vista.ItemTrabajos.addActionListener(this);
        this.vista.ItemTMantenimiento.addActionListener(this);
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

        if (e.getSource().equals(this.vista.ItemTrabajos)) {
            ControladorTrabajos controladorTrabajos;
            controladorTrabajos = new ControladorTrabajos(this.vista, new VistaTrabajos(), this.usuariosDao, this.usuario, new TrabajosDAO(), new EmpleadosDao());
        }

    }

}

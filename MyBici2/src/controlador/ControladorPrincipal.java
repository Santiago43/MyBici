package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.dao.UsuariosDao;
import vista.VistaInventario;
import vista.VistaLogin;
import vista.VistaPrincipal;

/**
 *
 * @author Santiago Pérez
 */
public class ControladorPrincipal implements ActionListener {

    VistaPrincipal vista;
    VistaLogin vistaAnterior;
    UsuariosDao usuariosDao;
    VistaInventario vistaInv;

    public ControladorPrincipal(VistaPrincipal vista, VistaLogin vistaAnterior, UsuariosDao usuariosDao) {
        this.vista = vista;
        this.vista.BtnIventario.addActionListener(this);
        this.vistaAnterior = vistaAnterior;
        this.vistaAnterior.setVisible(false);
        this.usuariosDao = usuariosDao;
        this.vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(this.vista.BtnIventario)) {

            ControladorInventario cInventario = new ControladorInventario(new VistaInventario(), this.vista, this.usuariosDao);
        }

    }

}

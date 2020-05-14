package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.dao.EmpleadosDao;
import modelo.dao.RolesDao;
import modelo.dao.UsuariosDao;
import modelo.dto.Empleado;
import modelo.dto.Rol;
import modelo.dto.Usuario;
import vista.VistaPrincipal;
import vista.VistaUsuarios;

/**
 *
 * @author F. PLAZA
 */
public class ControladorUsuarios implements ActionListener {

    VistaPrincipal vistaAnterior;
    VistaUsuarios vista;
    UsuariosDao usuariosDao;
    RolesDao rolesDao;
    EmpleadosDao empleadosDao;

    public ControladorUsuarios(VistaPrincipal vistaAnterior, VistaUsuarios vista, UsuariosDao usuariosDao, RolesDao rolesDao, EmpleadosDao empleadosDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.usuariosDao = usuariosDao;
        this.rolesDao = rolesDao;
        this.empleadosDao = empleadosDao;

        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);

        this.vistaAnterior.setVisible(false);
        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource().equals(this.vista.btnInsertar)) {
                Usuario usuario = new Usuario();
                usuario.setUsuario(MiExcepcion.capturaString(this.vista.txtUsuario));
                String nombreRol =this.vista.cmbRoles.getSelectedItem().toString();
                Rol rol = this.rolesDao.consultarPorNombre(nombreRol);
                usuario.setRol(rol);
                String cedula = MiExcepcion.capturaString(this.vista.txtCedula);
                Empleado empleado = this.empleadosDao.consultar(cedula);
                usuario.setEmpleado(empleado);
                usuario.setContraseña(MiExcepcion.capturaString((JTextField)this.vista.txtPassword));
                this.usuariosDao.crear(usuario);
            } else if (ae.getSource().equals(this.vista.btnConsultar)) {

            } else if (ae.getSource().equals(this.vista.btnModificar)) {

            } else if (ae.getSource().equals(this.vista.btnEliminar)) {

            } else if (ae.getSource().equals(this.vista.btnListar)) {

            } else if (ae.getSource().equals(this.vista.btnRegresar)) {

            }
        } catch (MiExcepcion ex) {
           JOptionPane.showMessageDialog(null,"Error :" + ex.getMessage());
        }

    }

}

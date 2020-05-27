package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
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
 * Clase controlador de usuarios
 *
 * @author F. PLAZA
 * @version 1.0
 * @since 2020-05-25
 */
public class ControladorUsuarios implements ActionListener {

    private final VistaPrincipal vistaAnterior;
    private final VistaUsuarios vista;
    private final UsuariosDao usuariosDao;
    private final RolesDao rolesDao;
    private final EmpleadosDao empleadosDao;

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
        this.vista.btnRegresar.addActionListener(this);
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        listarRoles();
        this.vistaAnterior.setVisible(false);
        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource().equals(this.vista.btnInsertar)) {
                crearUsuario();
            } else if (ae.getSource().equals(this.vista.btnConsultar)) {
                consultarUsuario();
            } else if (ae.getSource().equals(this.vista.btnModificar)) {
                modificarUsuario();
            } else if (ae.getSource().equals(this.vista.btnEliminar)) {
                eliminarUsuario();
            } else if (ae.getSource().equals(this.vista.btnRegresar)) {
                salir();
            }
        } catch (MiExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Error :" + ex.getMessage());
        }

    }

    private void listarRoles() {

        LinkedList<Rol> roles = rolesDao.listar();
        for (Rol rol : roles) {
            this.vista.cmbRoles.addItem(rol.getNombre());
        }
    }

    private void crearUsuario() throws MiExcepcion {
        Usuario usuario = new Usuario();

        String nombreRol = this.vista.cmbRoles.getSelectedItem().toString();
        Rol rol = this.rolesDao.consultarPorNombre(nombreRol);
        usuario.setRol(rol);
        String cedula = MiExcepcion.capturaString(this.vista.txtCedula);
        Empleado empleado = this.empleadosDao.consultar(cedula);
        if(empleado==null){
            throw new MiExcepcion("no existe empleado con esa cédula");
        }
        usuario.setEmpleado(empleado);
        usuario.setContraseña(MiExcepcion.capturaString((JTextField) this.vista.txtPassword));
        String nombreUsuario = rol.getNombreCorto() + cedula.substring(cedula.length() - 5, cedula.length() - 1);
        usuario.setUsuario(nombreUsuario);
        if (this.usuariosDao.consultar(usuario.getUsuario()) == null||this.usuariosDao.consultarPorCedula(cedula)==null) {
            if (this.usuariosDao.crear(usuario)) {
                JOptionPane.showMessageDialog(null, "Usuario " + usuario.getUsuario() + " creado satisfactoriamente");
                this.vista.limpiar();
            }
        } else {
            throw new MiExcepcion("Ese usuario ya existe");
        }
    }

    private void consultarUsuario() throws MiExcepcion {
        String nombreUsuario = JOptionPane.showInputDialog("Digite el nombre del usuario que desea consultar");
        if (nombreUsuario.equals("")) {
            throw new MiExcepcion("no puede dejar este campo vacío");
        } else {
            Usuario usuario = this.usuariosDao.consultar(nombreUsuario);
            if (usuario == null) {
                throw new MiExcepcion("ese usuario no existe");
            } else {
                this.vista.cmbRoles.setSelectedItem(usuario.getRol().getNombre());
                this.vista.txtUsuario.setText(usuario.getUsuario());
                this.vista.txtCedula.setText(usuario.getEmpleado().getCedula());
            }
        }
    }

    private void modificarUsuario() throws MiExcepcion {
        Usuario usuario = new Usuario();

        String nombreRol = this.vista.cmbRoles.getSelectedItem().toString();
        Rol rol = this.rolesDao.consultarPorNombre(nombreRol);
        usuario.setRol(rol);
        String cedula = MiExcepcion.capturaString(this.vista.txtCedula);
        Empleado empleado = this.empleadosDao.consultar(cedula);
        usuario.setEmpleado(empleado);
        usuario.setContraseña(MiExcepcion.capturaString((JTextField) this.vista.txtPassword));
        String nombreUsuario = MiExcepcion.capturaString(this.vista.txtUsuario);
        usuario.setUsuario(nombreUsuario);
        if (this.usuariosDao.consultar(usuario.getUsuario()) != null) {
            if (this.usuariosDao.actualizar(usuario)) {
                JOptionPane.showMessageDialog(null, "Usuario " + usuario.getUsuario() + " actualizado satisfactoriamente");
                this.vista.limpiar();
            }
        } else {
            throw new MiExcepcion("Ese usuario no existe");
        }
    }

    private void eliminarUsuario() throws MiExcepcion {
        String nombreUsuario = JOptionPane.showInputDialog("Escriba el nombre del usuario que desea eliminar");
        if (nombreUsuario.equals("")) {
            throw new MiExcepcion("no puede dejar este campo vacío");
        } else {
            if (this.usuariosDao.consultar(nombreUsuario) == null) {
                throw new MiExcepcion("ese usuario no existe");
            } else {
                if (this.usuariosDao.eliminar(nombreUsuario)) {
                    JOptionPane.showMessageDialog(null, "Usuario " + nombreUsuario + " elimnado");
                }
            }
        }
    }

    private void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

}

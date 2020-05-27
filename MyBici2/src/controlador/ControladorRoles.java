package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.PermisosDao;
import modelo.dao.RolesDao;
import modelo.dto.Rol;
import modelo.dto.Usuario;
import vista.VistaModificarPermisoRol;
import vista.VistaPrincipal;
import vista.VistaRoles;

/**
 * Clase controlador del módulo de roles
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class ControladorRoles implements ActionListener {

    public VistaPrincipal vistaAnterior;
    public VistaRoles vista;
    public RolesDao rolesDao;
    public Usuario usuario;
    public DefaultTableModel modeloTablaRoles;

    public ControladorRoles(VistaPrincipal vistaAnterior, VistaRoles vista, RolesDao rolesDao, Usuario usuario) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.rolesDao = rolesDao;
        this.usuario = usuario;
        this.modeloTablaRoles = (DefaultTableModel) this.vista.tblRoles.getModel();
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnModificarPermisos.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        this.vistaAnterior.setVisible(false);
        listarRoles();

        this.vista.setVisible(true);
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnInsertar)) {
                crearRol();
            } else if (e.getSource().equals(this.vista.btnEliminar)) {
                eliminarRol();
            } else if (e.getSource().equals(this.vista.btnModificarPermisos)) {
                ControladorModificarPermisosRol cModificarPermiso = new ControladorModificarPermisosRol(this.vista, new VistaModificarPermisoRol(), this.rolesDao, new PermisosDao());
            } else if (e.getSource().equals(this.vista.btnRegresar)) {
                salir();
            }
        } catch (MiExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }

    /**
     *
     */
    public void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    /**
     *
     */
    private void listarRoles() {
        //Lista los roles en la tabla
        LinkedList<Rol> roles = rolesDao.listar();
        for (Rol rol : roles) {
            Object fila[] = {rol.getId(), rol.getNombre(),rol.getNombreCorto()};
            modeloTablaRoles.addRow(fila);
        }
    }

    private void crearRol() throws MiExcepcion {
        Rol rol = new Rol();
        rol.setNombre(MiExcepcion.capturaString(this.vista.txtNombreRol));
        rol.setNombreCorto(MiExcepcion.capturaString(this.vista.txtNombreCorto));
        if (this.rolesDao.consultarPorNombre(rol.getNombre()) != null) {
            throw new MiExcepcion("Ya existe un nombre con ese rol");
        } else {
            if (this.rolesDao.crear(rol)) {
                JOptionPane.showMessageDialog(null, "Rol creado satisfactoriamente");
                limpiarTabla();
                listarRoles();
                this.vista.limpiar();
            }else{
                throw new MiExcepcion("Error al crear rol");
            }

        }

    }

    public void limpiarTabla() {
        while(this.modeloTablaRoles.getRowCount()>0){
            this.modeloTablaRoles.removeRow(0);
        }
    }

    private void eliminarRol() throws MiExcepcion {
        String nombreRol = JOptionPane.showInputDialog("Ingrese el nombre del rol que desea eliminar");
        if (nombreRol.equals("")) {
            throw new MiExcepcion("No puede dejar ese campo vacío");
        } else {
            Rol rol = this.rolesDao.consultarPorNombre(nombreRol);
            if (rol == null) {
                throw new MiExcepcion("Ese rol no existe");
            } else {
                int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar el rol " + rol.getNombre());
                if (opc == JOptionPane.YES_OPTION) {
                    if (this.rolesDao.eliminar(String.valueOf(rol.getId()))) {
                        JOptionPane.showMessageDialog(null, "Rol creado satisfactoriamente");
                    } else {
                        throw new MiExcepcion("Error al eliminar rol");
                    }
                    limpiarTabla();
                    listarRoles();
                }

            }
        }
    }
}

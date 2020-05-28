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
import modelo.dto.Permiso;
import modelo.dto.Rol;
import vista.VistaModificarPermisoRol;
import vista.VistaRoles;

/**
 * Controlador de modificación de permisos de roles
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class ControladorModificarPermisosRol implements ActionListener {

    private final VistaRoles vistaAnterior;
    private final VistaModificarPermisoRol vista;
    private final RolesDao rolesDao;
    private final PermisosDao permisoDao;
    private DefaultTableModel modeloTablaPermisos;
    private DefaultTableModel modeloTablaPermisosRol;

    public ControladorModificarPermisosRol(VistaRoles vistaAnterior, VistaModificarPermisoRol vista, RolesDao rolesDao, PermisosDao permisoDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.rolesDao = rolesDao;
        this.vista.btnAgregarPermiso.addActionListener(this);
        this.vista.btnRemoverPermiso.addActionListener(this);
        this.vista.btnCrearPermiso.addActionListener(this);
        this.vista.btnEliminarPermiso.addActionListener(this);
        this.vista.btnSalir.addActionListener(this);
        this.permisoDao = permisoDao;
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        this.modeloTablaPermisos = (DefaultTableModel) this.vista.tblPermisos.getModel();
        this.modeloTablaPermisosRol = (DefaultTableModel) this.vista.tblPermisosRol.getModel();
        this.vistaAnterior.setVisible(false);
        this.vista.setVisible(true);
        cargarCombos();
        cargarTablaPermisos();
        this.vista.comboRoles.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnAgregarPermiso)) {
                String nombrePermiso = this.vista.comboPermisos.getSelectedItem().toString();
                String nombreRol = this.vista.comboRoles.getSelectedItem().toString();
                Rol rol = this.rolesDao.consultarPorNombre(nombreRol);
                Permiso permiso = this.permisoDao.consultarPorNombre(nombrePermiso);
                if (rolesDao.agregarPermiso(permiso, rol)) {
                    JOptionPane.showMessageDialog(null, "Permiso agregado " + nombrePermiso + " a " + nombreRol);
                    this.limpiarTablas();
                    this.cargarTablaPermisos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar el permiso");
                }
            } else if (e.getSource().equals(this.vista.btnRemoverPermiso)) {
                String nombrePermiso = this.vista.comboPermisos.getSelectedItem().toString();
                String nombreRol = this.vista.comboRoles.getSelectedItem().toString();
                Rol rol = this.rolesDao.consultarPorNombre(nombreRol);
                Permiso permiso = this.permisoDao.consultarPorNombre(nombrePermiso);
                if (rolesDao.removerPermiso(permiso, rol)) {
                    JOptionPane.showMessageDialog(null, "Permiso removido " + nombrePermiso + " a " + nombreRol);
                    this.limpiarTablas();
                    this.cargarTablaPermisos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al remover el permiso");
                }
            } else if (e.getSource().equals(this.vista.btnCrearPermiso)) {
                String nuevoPermiso = JOptionPane.showInputDialog("Escriba el nombre del permiso que desea ingresar. "
                        + "Tenga en cuenta que estos permisos no deben ser iguales a los que aparecen en la tabla");
                if (nuevoPermiso.equals("")) {
                    throw new MiExcepcion("no puede dejar este campo vacío");
                } else if (permisoDao.consultarPorNombre(nuevoPermiso) == null) {
                    Permiso permiso = new Permiso();
                    permiso.setNombrePermiso(nuevoPermiso);
                    if (permisoDao.crear(permiso)) {
                        JOptionPane.showMessageDialog(null, "Permiso creado satisfactoriamente");
                    }
                    this.limpiarTablas();
                    this.cargarTablaPermisos();
                }
            } else if (e.getSource().equals(this.vista.btnEliminarPermiso)) {
                String permisoARemover = JOptionPane.showInputDialog("Escriba el nombre del permiso que desea eliminar. "
                        + "Tenga en cuenta que estos permisos no deben ser iguales a los que aparecen en la tabla");
                if (permisoARemover.equals("")) {
                    throw new MiExcepcion("no puede dejar este campo vacío");
                } else {
                    Permiso permiso = permisoDao.consultarPorNombre(permisoARemover);
                    if (permiso != null) {
                        int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar el permiso " + permiso.getNombrePermiso());
                        if (opc == JOptionPane.YES_OPTION) {
                            if (permisoDao.eliminar(String.valueOf(permiso.getIdPermiso()))) {
                                JOptionPane.showMessageDialog(null, "Permiso eliminado satisfactoriamente");
                                this.limpiarTablas();
                                this.cargarTablaPermisos();
                            }
                            else{
                                throw new MiExcepcion("Error al eliminar");
                            }
                        }
                    } else {
                        throw new MiExcepcion("ese permiso no existe");
                    }

                }
            } else if (e.getSource().equals(this.vista.btnSalir)) {
                salir();
            } else if (e.getSource().equals(this.vista.comboRoles)) {
                limpiarTablas();
                cargarTablaPermisos();
            }
        } catch (MiExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }

    private void cargarTablaPermisos() {
        LinkedList<Permiso> permisos = this.permisoDao.listar();
        for (int i = 0; i < permisos.size(); i++) {
            Object[] fila = {permisos.get(i).getNombrePermiso()};
            this.modeloTablaPermisos.addRow(fila);
        }

        LinkedList<Permiso> permisosRol = this.rolesDao.consultarPorNombre(this.vista.comboRoles.getSelectedItem().toString()).getPermisos();
        for (int i = 0; i < permisosRol.size(); i++) {
            Object[] fila = {permisosRol.get(i).getNombrePermiso()};
            this.modeloTablaPermisosRol.addRow(fila);
        }
    }

    private void limpiarTablas() {
        while (this.modeloTablaPermisos.getRowCount() > 0) {
            this.modeloTablaPermisos.removeRow(0);
        }
        while (this.modeloTablaPermisosRol.getRowCount() > 0) {
            this.modeloTablaPermisosRol.removeRow(0);
        }
    }

    public void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    private void cargarCombos() {
        LinkedList<Rol> roles = this.rolesDao.listar();
        LinkedList<Permiso> permisos = this.permisoDao.listar();
        for (int i = 0; i < roles.size(); i++) {
            this.vista.comboRoles.addItem(roles.get(i).getNombre());
        }
        for (int i = 0; i < permisos.size(); i++) {
            this.vista.comboPermisos.addItem(permisos.get(i).getNombrePermiso());
        }

    }
}

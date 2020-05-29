package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.DireccionDao;
import modelo.dao.ProveedoresDao;
import modelo.dto.Calle;
import modelo.dto.Carrera;
import modelo.dto.Direccion;
import modelo.dto.Proveedor;
import modelo.dto.Telefono;
import vista.VistaAgregarTelefonoProveedor;
import vista.VistaListaProveedores;
import vista.VistaPrincipal;
import vista.VistaProveedores;

/**
 * Clase controlador proveedor
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-28
 */
public class ControladorProveedores implements ActionListener {

    private VistaProveedores vista;
    private VistaPrincipal vistaAnterior;
    private ProveedoresDao proveedoresDao;
    private DireccionDao direccionDao;
    private DefaultTableModel modeloTabla;

    public ControladorProveedores(VistaProveedores vista, VistaPrincipal vistaAnterior, ProveedoresDao proveedores, DireccionDao direccionDao) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.proveedoresDao = proveedores;
        this.direccionDao = direccionDao;
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnAgregarTelefono.addActionListener(this);
        this.vista.btnRemoverTelefono.addActionListener(this);
        this.vista.setVisible(true);
        this.vistaAnterior.setVisible(false);
        this.modeloTabla=(DefaultTableModel)this.vista.tblTelefono.getModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnInsertar)) {
                crearProveedor();
            } else if (e.getSource().equals(this.vista.btnConsultar)) {
                consultarProveedor();
            } else if (e.getSource().equals(this.vista.btnModificar)) {
                modificarProveedor();
            } else if (e.getSource().equals(this.vista.btnEliminar)) {
                eliminarProveedor();
            } else if (e.getSource().equals(this.vista.btnListar)) {
                listarProveedores();
            } else if (e.getSource().equals(this.vista.btnAgregarTelefono)) {
                agregarTelefonoAProveedor();
            } else if (e.getSource().equals(this.vista.btnRemoverTelefono)) {
                removerTelefonoAProveedor();
            } else if (e.getSource().equals(this.vista.btnRegresar)) {
                salir();
            }
        } catch (MiExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }

    private void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    private void crearProveedor() throws MiExcepcion {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(MiExcepcion.capturaString(this.vista.txtNombre));
        Direccion direccion = new Direccion();
        Calle calle = new Calle();
        Carrera carrera = new Carrera();
        calle.setNumeroCalle(MiExcepcion.capturaEntero(this.vista.txtCalle));
        calle.setLetraCalle(this.vista.txtLetraCalle.getText().charAt(0));
        if (!this.vista.radioBisFalseCalle.isSelected() && !this.vista.radioBisTrueCalle.isSelected()) {
            throw new MiExcepcion("debe elegir si su dirección tiene bis en 'calle'");
        } else {
            calle.setBis(this.vista.radioBisTrueCalle.isSelected());
        }
        if (!this.vista.radioSur.isSelected() && !this.vista.radioNorte.isSelected()) {
            throw new MiExcepcion("debe elegir si la dirección está en el norte o en el sur");
        } else {
            calle.setSur(this.vista.radioSur.isSelected());
        }
        carrera.setNumeroCarrera(MiExcepcion.capturaEntero(this.vista.txtCarrera));
        carrera.setLetraCarrera(this.vista.txtLetraCalle.getText().charAt(0));
        if (!this.vista.radioBisFalseCarrera.isSelected() && !this.vista.radioBisTrueCalle.isSelected()) {
            throw new MiExcepcion("debe elegir si su dirección tiene un bis en 'carrera'");
        } else {
            carrera.setBis(this.vista.radioBisTrueCarrera.isSelected());
        }
        if (!this.vista.radioEste.isSelected() && !this.vista.radioOeste.isSelected()) {
            throw new MiExcepcion("Debe elegir si la dirección está en el norte o en el sur");
        } else {
            carrera.setEste(this.vista.radioEste.isSelected());
        }
        direccion.setCalle(calle);
        direccion.setCarrera(carrera);
        if (this.direccionDao.crear(direccion)) {

        }
        int idDireccion = this.direccionDao.consultarIdUltimaDireccion();
        Direccion direccionConfirmada = this.direccionDao.consultar(String.valueOf(idDireccion));
        proveedor.setDireccion(direccionConfirmada);
        if (this.proveedoresDao.crear(proveedor)) {
            JOptionPane.showMessageDialog(null, "Proveedor creado");
        } else {
            throw new MiExcepcion("Error al crear proveedor");
        }
    }

    private void consultarProveedor() throws MiExcepcion {
        String idProveedor = JOptionPane.showInputDialog("Digite el id del proveedor que desea consultar");
        if (idProveedor.equals("")) {
            throw new MiExcepcion("no puede dejar ese campo vacío");
        }
        Proveedor proveedor = this.proveedoresDao.consultar(idProveedor);
        if (proveedor == null) {
            throw new MiExcepcion("ese proveedor no existe");
        }
        this.vista.txtIDProveedor.setText(idProveedor);
        this.vista.txtNombre.setText(proveedor.getNombre());
        vaciarTabla();
        listarTelefonos(proveedor);
        Direccion direccion = proveedor.getDireccion();
        
        this.vista.txtCalle.setText(String.valueOf(direccion.getCalle().getNumeroCalle()));
        this.vista.txtLetraCalle.setText(String.valueOf(direccion.getCalle().getLetraCalle()));
        if (direccion.getCalle().isBis()) {
            this.vista.radioBisTrueCalle.setSelected(true);
        } else {
            this.vista.radioBisFalseCalle.setSelected(true);
        }

        if (direccion.getCalle().isSur()) {
            this.vista.radioSur.setSelected(true);
        } else {
            this.vista.radioNorte.setSelected(true);
        }

        this.vista.txtCarrera.setText(String.valueOf(direccion.getCarrera().getNumeroCarrera()));
        this.vista.txtLetraCarrera.setText(String.valueOf(direccion.getCarrera().getLetraCarrera()));
        if (direccion.getCarrera().isBis()) {
            this.vista.radioBisTrueCarrera.setSelected(true);
        } else {
            this.vista.radioBisFalseCarrera.setSelected(true);
        }
        if (direccion.getCarrera().isEste()) {
            this.vista.radioEste.setSelected(true);
        } else {
            this.vista.radioOeste.setSelected(true);
        }
    }

    private void modificarProveedor() throws MiExcepcion {
        Proveedor proveedor = this.proveedoresDao.consultar(MiExcepcion.capturaString(this.vista.txtIDProveedor));
        if(proveedor==null){
            throw new MiExcepcion("Ese proveedor no existe");
        }
        Direccion direccion = this.direccionDao.consultar(String.valueOf(proveedor.getDireccion().getIdDireccion()));
        Calle calle = direccion.getCalle();
        Carrera carrera = direccion.getCarrera();
        calle.setNumeroCalle(MiExcepcion.capturaEntero(this.vista.txtCalle));
        calle.setLetraCalle(this.vista.txtLetraCalle.getText().charAt(0));
        if (!this.vista.radioBisFalseCalle.isSelected() && !this.vista.radioBisTrueCalle.isSelected()) {
            throw new MiExcepcion("debe elegir si su dirección tiene bis en 'calle'");
        } else {
            calle.setBis(this.vista.radioBisTrueCalle.isSelected());
        }
        if (!this.vista.radioSur.isSelected() && !this.vista.radioNorte.isSelected()) {
            throw new MiExcepcion("debe elegir si la dirección está en el norte o en el sur");
        } else {
            calle.setSur(this.vista.radioSur.isSelected());
        }
        carrera.setNumeroCarrera(MiExcepcion.capturaEntero(this.vista.txtCarrera));
        carrera.setLetraCarrera(this.vista.txtLetraCalle.getText().charAt(0));
        if (!this.vista.radioBisFalseCarrera.isSelected() && !this.vista.radioBisTrueCalle.isSelected()) {
            throw new MiExcepcion("debe elegir si su dirección tiene un bis en 'carrera'");
        } else {
            carrera.setBis(this.vista.radioBisTrueCarrera.isSelected());
        }
        if (!this.vista.radioEste.isSelected() && !this.vista.radioOeste.isSelected()) {
            throw new MiExcepcion("Debe elegir si la dirección está en el norte o en el sur");
        } else {
            carrera.setEste(this.vista.radioEste.isSelected());
        }
        direccion.setCalle(calle);
        direccion.setCarrera(carrera);
        if(this.proveedoresDao.actualizar(proveedor)){
            JOptionPane.showMessageDialog(null, "Proveedor actualizado");
        }
    }

    private void eliminarProveedor() throws MiExcepcion {
        String idProveedor = JOptionPane.showInputDialog("Digite el id del proveedor que desea eliminar");
        Proveedor proveedor = this.proveedoresDao.consultar(idProveedor);
        if(proveedor==null){
            throw new MiExcepcion("ese proveedor no existe");
        }
        int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar al proveedor "+proveedor.getNombre()+" ?");
        if(opc==JOptionPane.YES_OPTION){
            if(this.proveedoresDao.eliminar(idProveedor)){
                JOptionPane.showMessageDialog(null, "Proveedor "+proveedor.getNombre()+" eliminado");
            }
            else{
                throw new MiExcepcion("error al eliminar proveedor");
            }
        }
        
    }

    private void listarProveedores() {
        ControladorListaProveedores controlador = new ControladorListaProveedores(this.vista,new VistaListaProveedores(),this.proveedoresDao);
    }

    private void agregarTelefonoAProveedor() throws MiExcepcion {
        Proveedor proveedor = this.proveedoresDao.consultar(MiExcepcion.capturaString(this.vista.txtIDProveedor));
        if(proveedor==null){
            throw new MiExcepcion("ese proveedor no existe");
        }
        ControladorAgregarTelefonoAProveedor controlador = new ControladorAgregarTelefonoAProveedor(this.vista,new VistaAgregarTelefonoProveedor(),this.proveedoresDao,proveedor);
        this.vaciarTabla();
        this.listarTelefonos(proveedor);
        
    }

    private void removerTelefonoAProveedor() throws MiExcepcion {
        String numero = JOptionPane.showInputDialog("Digite el número de teléfono que desea borrar ");
        Telefono telefono = new Telefono();
        telefono.setNumeroTelefonico(numero);
        if(this.proveedoresDao.removerTelefono(telefono)){
            JOptionPane.showMessageDialog(null, "Teléfono eliminado");
        }else{
            throw new MiExcepcion("Error al eliminar el teléfono");
        }
        this.vaciarTabla();
        this.listarTelefonos(this.proveedoresDao.consultar(MiExcepcion.capturaString(this.vista.txtIDProveedor)));
    }

    private void listarTelefonos(Proveedor proveedor) {
        LinkedList<Telefono> telefonos = proveedor.getTelefono();
        for(Telefono telefono : telefonos){
            Object fila[] ={telefono.getTipoTelefono(),telefono.getNumeroTelefonico()};
            this.modeloTabla.addRow(fila);
        }
    }

    private void vaciarTabla() {
        while(this.modeloTabla.getRowCount()>0){
            this.modeloTabla.removeRow(0);
        }
    }

}

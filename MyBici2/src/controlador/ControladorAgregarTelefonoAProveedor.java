package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dao.ProveedoresDao;
import modelo.dto.Proveedor;
import modelo.dto.Telefono;
import vista.VistaAgregarTelefonoProveedor;
import vista.VistaPrincipal;
import vista.VistaProveedores;

/**
 *
 * @author Santiago Pérez
 */
public class ControladorAgregarTelefonoAProveedor implements ActionListener {

    VistaProveedores vistaAnterior;
    VistaAgregarTelefonoProveedor vista;
    ProveedoresDao proveedoresDao;
    Proveedor proveedor;

    public ControladorAgregarTelefonoAProveedor(VistaProveedores vistaAnterior, VistaAgregarTelefonoProveedor vista, ProveedoresDao proveedoresDao,Proveedor proveedor) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.proveedoresDao = proveedoresDao;
        this.proveedor=proveedor;
        this.vista.btn.addActionListener(this);
        this.vista.setVisible(true);
        this.vistaAnterior.setVisible(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Telefono telefono = new Telefono();
            telefono.setTipoTelefono(this.vista.comboTipo.getSelectedItem().toString());
            telefono.setNumeroTelefonico(MiExcepcion.capturaString(this.vista.txtNumero));
            if(this.proveedoresDao.agregarTelefono(proveedor, telefono)){
                JOptionPane.showMessageDialog(null, "Teléfono creado");
            }else{
                throw new MiExcepcion("Error al crear teléfono");
            }
            this.vista.dispose();
            this.vistaAnterior.setVisible(true);
        } catch (MiExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage());
        }
    }

}

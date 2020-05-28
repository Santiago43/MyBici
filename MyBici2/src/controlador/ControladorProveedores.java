package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import modelo.dao.ProveedoresDao;
import vista.VistaPrincipal;
import vista.VistaProveedores;

/**
 * Clase controlador proveedor
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-28
 */
public class ControladorProveedores implements ActionListener{

    private VistaProveedores vista;
    private VistaPrincipal vistaAnterior;
    private ProveedoresDao proveedores;

    public ControladorProveedores(VistaProveedores vista, VistaPrincipal vistaAnterior, ProveedoresDao proveedores) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.proveedores = proveedores;
        this.vista.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.setVisible(true);
        this.vistaAnterior.setVisible(false);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.vista.btnRegresar)){
            salir();
        }
    }

    private void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }
    
}

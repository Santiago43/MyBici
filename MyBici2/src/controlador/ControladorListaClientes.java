package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ClienteDao;
import modelo.dto.Cliente;
import vista.VistaClientes;
import vista.VistaListaClientes;

/**
 * Clase controlador de la lista de clientes
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-27
 */
public class ControladorListaClientes implements ActionListener{
    private final VistaClientes vistaAnterior;
    private final VistaListaClientes vista;
    private final ClienteDao clienteDao;
    private final DefaultTableModel modeloTabla;

    /**
     *
     * @param vistaAnterior
     * @param vista
     * @param clienteDao
     */
    public ControladorListaClientes(VistaClientes vistaAnterior, VistaListaClientes vista, ClienteDao clienteDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.clienteDao = clienteDao;
        this.vista.btnRegresar.addActionListener(this);
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        this.modeloTabla=(DefaultTableModel)this.vista.tblClientes.getModel();
        listarClientes();
        this.vistaAnterior.setVisible(false);
        this.vista.setVisible(true);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
       salir();
    }

    private void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    private void listarClientes() {
       LinkedList<Cliente> clientes = this.clienteDao.listar();
        for (Cliente cliente:clientes) {
            
             String genero="";
            switch (cliente.getGenero().charAt(0)) {
                case 'm':
                    genero="Masculino";
                    break;
                case 'f':
                    genero="Femenino";
                    break;
                case 'n':
                    genero="Otro";
                    break;
                default:
                    genero="Otro";
                    break;
            }
            
            Object fila[] ={cliente.getCedula(),cliente.getPrimerNombre(),cliente.getSegundoNombre(),cliente.getPrimerApellido(),cliente.getSegundoApellido(),cliente.getDireccion().toString(),cliente.getNacionalidad(),genero};
            this.modeloTabla.addRow(fila);
        }
    }
    
}

package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ProveedoresDao;
import modelo.dto.Proveedor;
import vista.VistaListaProveedores;
import vista.VistaProveedores;

/**
 * Controlador de la lista de proveedores
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-28
 */
public class ControladorListaProveedores implements ActionListener{

    private VistaProveedores vistaAnterior;
    private VistaListaProveedores vista;
    private ProveedoresDao proveedoresDao;
    private DefaultTableModel modeloTabla;

    public ControladorListaProveedores(VistaProveedores vistaAnterior, VistaListaProveedores vista, ProveedoresDao proveedoresDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.proveedoresDao = proveedoresDao;
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
        
        this.modeloTabla= (DefaultTableModel)this.vista.tblProveedores.getModel();
        this.vistaAnterior.setVisible(false);
        this.vista.setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.vista.btnConsultar)){
            vaciarTabla();
            consultar();
        }
        else if(e.getSource().equals(this.vista.btnRegresar)){
            salir();
        }
    }

    private void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    private void consultar() {
        LinkedList <Object []> consulta=null;
        switch(this.vista.comboConsultas.getSelectedIndex()){
            case 0:
                consulta=this.proveedoresDao.listarVentaMayor();
                break;
            case 1:
                consulta=this.proveedoresDao.listarVentaMenor();
                break;
            case 2:
                consulta=this.proveedoresDao.listarFrecuentes();
                break;
        }
        for (Object[] pro : consulta) {
            Proveedor proveedor = (Proveedor)pro[0];
            Object fila[] = {proveedor.getIdProveedor(),proveedor.getNombre(),proveedor.getDireccion().toString(),pro[1],pro[2]};
            this.modeloTabla.addRow(fila);
        }
    }
    private void vaciarTabla(){
        while(this.modeloTabla.getRowCount()>0){
            this.modeloTabla.removeRow(0);
        }
    }
    
}

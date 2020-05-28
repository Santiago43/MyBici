package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import modelo.dao.PeticionDao;
import modelo.dto.Peticion;
import vista.VistaListaPeticiones;
import vista.VistaPeticiones;

/**
 * Controlador de lista de peticiones
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-28
 */
public class ControladorListaPeticiones implements ActionListener{
    
    private VistaPeticiones vistaAnterior;
    private VistaListaPeticiones vista;
    private PeticionDao peticionDao;
    private DefaultTableModel modeloTabla;

    /**
     *
     * @param vistaAnterior
     * @param vista
     * @param peticionDao
     */
    public ControladorListaPeticiones(VistaPeticiones vistaAnterior, VistaListaPeticiones vista, PeticionDao peticionDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.vista.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.vista.btnRegresar.addActionListener(this);
        this.modeloTabla=(DefaultTableModel) this.vista.tblPeticiones.getModel();
        this.peticionDao = peticionDao;
        cargarTabla();
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

    private void cargarTabla() {
        LinkedList <Peticion> peticiones = this.peticionDao.listar();
        for(Peticion peticion: peticiones){
            String aprobado ="";
            if(peticion.isAprobado()){
                aprobado ="aprobado";
            }else{
                aprobado = "no aprobado";
            }
                    
            Object fila[]= {peticion.getIdPeticionEmpleado(),peticion.getEmpleado().getPrimerNombre()+" "+peticion.getEmpleado().getPrimerApellido(),aprobado,peticion.getPeticion()};
            this.modeloTabla.addRow(fila);
        }
    }
    
}

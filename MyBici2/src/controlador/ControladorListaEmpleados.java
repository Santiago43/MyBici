package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import modelo.dao.EmpleadosDao;
import modelo.dao.SedeDao;
import modelo.dto.Empleado;
import modelo.dto.Sede;
import vista.VistaEmpleado;
import vista.VistaListaEmpleados;

/**
 * Controlador de la lista de empleados
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class ControladorListaEmpleados implements ActionListener{
    private VistaListaEmpleados vista;
    private VistaEmpleado vistaAnterior;
    private EmpleadosDao empleadoDao;
    private DefaultTableModel modeloTabla;
    private SedeDao sedeDao;

    public ControladorListaEmpleados(VistaListaEmpleados vista, VistaEmpleado vistaAnterior, EmpleadosDao empleadoDao,SedeDao sedeDao) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.empleadoDao = empleadoDao;
        this.sedeDao=sedeDao;
        this.vista.btnRegresar.addActionListener(this);
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        this.modeloTabla=(DefaultTableModel)this.vista.tblEmpleados.getModel();
        listarEmpleados();
        this.vista.setVisible(true);
        this.vistaAnterior.setVisible(false);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        salir();
    }

    private void listarEmpleados() {
        LinkedList<Empleado> empleados = empleadoDao.listar();
        for (Empleado empleado: empleados) {
            String genero="";
            switch (empleado.getGenero().charAt(0)) {
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
            Sede sede = sedeDao.consultar(String.valueOf(empleado.getIdSede()));
            Object fila[] ={empleado.getCedula(),empleado.getPrimerNombre(),empleado.getSegundoNombre(),empleado.getPrimerApellido(),empleado.getSegundoApellido(),empleado.getDireccion().toString(),empleado.getNacionalidad(),genero,empleado.getProfesion(),empleado.getCargo(),empleado.getSalario(),sede.getNombreSede()};
            this.modeloTabla.addRow(fila);
        }
    }
    public void salir(){
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }
}

package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.dao.DireccionDao;
import modelo.dao.EmpleadosDao;
import modelo.dao.SedeDao;
import modelo.dto.Calle;
import modelo.dto.Carrera;
import modelo.dto.Direccion;
import modelo.dto.Empleado;
import modelo.dto.Sede;
import vista.VistaEmpleado;
import vista.VistaListaEmpleados;
import vista.VistaPrincipal;

/**
 * Clase controlador de empleados
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class ControladorEmpleados implements ActionListener {

    private final VistaEmpleado vista;
    private final VistaPrincipal vistaAnterior;
    private final EmpleadosDao empleadoDao;
    private final DireccionDao direccionDao;
    private final SedeDao sedeDao;

    public ControladorEmpleados(VistaEmpleado vista, VistaPrincipal vistaAnterior, EmpleadosDao empleadoDao, DireccionDao direccionDao, SedeDao sedeDao) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.empleadoDao = empleadoDao;
        this.direccionDao = direccionDao;
        this.sedeDao = sedeDao;
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        cargarCombo();
        this.vistaAnterior.setVisible(false);
        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnInsertar)) {
                crearEmpleado();
            } else if (e.getSource().equals(this.vista.btnConsultar)) {
                consultarEmpleado();
            } else if (e.getSource().equals(this.vista.btnModificar)) {
                actualizarEmpleado();
            } else if (e.getSource().equals(this.vista.btnEliminar)) {
                eliminarEmpleado();
            } else if (e.getSource().equals(this.vista.btnListar)) {
                listarEmpleados();
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

    private void crearEmpleado() throws MiExcepcion {
        Empleado empleado = new Empleado();
        empleado.setCedula(MiExcepcion.capturaString(this.vista.txtCedula));
        empleado.setPrimerNombre(MiExcepcion.capturaString(this.vista.txtPrimerNombre));
        empleado.setSegundoNombre(this.vista.txtSegundoNombre.getText());
        empleado.setPrimerApellido(MiExcepcion.capturaString(this.vista.txtPrimerApellido));
        empleado.setSegundoApellido(MiExcepcion.capturaString(this.vista.txtSegundoApellido));
        empleado.setFechaNacimiento(MiExcepcion.capturaString((JTextField) this.vista.FecNacimiento.getDateEditor().getUiComponent()));
        empleado.setCargo(MiExcepcion.capturaString(this.vista.txtCargo));
        empleado.setNacionalidad(MiExcepcion.capturaString(this.vista.txtNacionalidad));
        char genero = '1';
        if (this.vista.radioHombre.isSelected()) {
            genero = 'm';
        } else if (this.vista.radioMujer.isSelected()) {
            genero = 'f';
        } else if (this.vista.radioOtro.isSelected()) {
            genero = 'n';
        } else {
            throw new MiExcepcion("Debe seleccionar un género");
        }
        empleado.setGenero(String.valueOf(genero));
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
        empleado.setDireccion(direccionConfirmada);
        Sede sede = this.sedeDao.consultarPorNombre(this.vista.comboSedes.getSelectedItem().toString());
        if (sede != null) {
            empleado.setIdSede(sede.getIdSede());
        } else {
            throw new MiExcepcion("esa sede no existe");
        }
        if(this.empleadoDao.consultar(empleado.getCedula())!=null){
            throw new MiExcepcion("ese empleado ya existe");
        }
        else{
            if(this.empleadoDao.crear(empleado)){
                JOptionPane.showMessageDialog(null, "Empleado creado satisfactoriamente");
            }
            else{
                throw new MiExcepcion("error al crear empleado");
            }
        }
    }

    private void consultarEmpleado() throws MiExcepcion {
       Empleado empleado = this.empleadoDao.consultar(MiExcepcion.capturaString(this.vista.txtCedula));
       if(empleado==null){
           throw new MiExcepcion("ese empleado no existe");
       }
       else{
           this.vista.txtCedula.setText(empleado.getCedula());
           this.vista.txtPrimerNombre.setText(empleado.getPrimerNombre());
           this.vista.txtSegundoNombre.setText(empleado.getSegundoNombre());
           this.vista.txtPrimerApellido.setText(empleado.getPrimerApellido());
           this.vista.txtSegundoApellido.setText(empleado.getSegundoApellido());
           this.vista.txtNacionalidad.setText(empleado.getNacionalidad());
           ((JTextField)this.vista.FecNacimiento.getDateEditor().getUiComponent()).setText(empleado.getFechaNacimiento());
           Sede sede = this.sedeDao.consultar(String.valueOf(empleado.getIdSede()));
           this.vista.comboSedes.setSelectedItem(sede.getNombreSede());
           this.vista.txtProfesion.setText(empleado.getProfesion());
           this.vista.txtCargo.setText(empleado.getCargo());
           this.vista.txtSalario.setText(String.valueOf(empleado.getSalario()));
           switch (empleado.getGenero().charAt(0)) {
               case 'm':
                   this.vista.radioHombre.setSelected(true);
                   break;
               case 'f':
                   this.vista.radioMujer.setSelected(true);
                   break;
               case 'n':
                   this.vista.radioOtro.setSelected(true);
                   break;
               default:
                   break;
           }
           
           Direccion direccion = empleado.getDireccion();
           this.vista.txtCalle.setText(String.valueOf(direccion.getCalle().getNumeroCalle()));
           this.vista.txtLetraCalle.setText(String.valueOf(direccion.getCalle().getLetraCalle()));
           if(direccion.getCalle().isBis()){
               this.vista.radioBisTrueCalle.setSelected(true);
           }else{
               this.vista.radioBisFalseCalle.setSelected(true);
           }
           
           if(direccion.getCalle().isSur()){
               this.vista.radioSur.setSelected(true);
           }else{
               this.vista.radioNorte.setSelected(true);
           }
           
           this.vista.txtCarrera.setText(String.valueOf(direccion.getCarrera().getNumeroCarrera()));
           this.vista.txtLetraCarrera.setText(String.valueOf(direccion.getCarrera().getLetraCarrera()));
           if(direccion.getCarrera().isBis()){
               this.vista.radioBisTrueCarrera.setSelected(true);
           }
           else{
               this.vista.radioBisFalseCarrera.setSelected(true);
           }
           if(direccion.getCarrera().isEste()){
               this.vista.radioEste.setSelected(true);
           }else{
               this.vista.radioOeste.setSelected(true);
           }
       }
    }

    private void actualizarEmpleado() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void eliminarEmpleado() throws MiExcepcion {
        String cedulaEmpleado = MiExcepcion.capturaString(this.vista.txtCedula);
        Empleado empleado = this.empleadoDao.consultar(cedulaEmpleado);
        if(empleado==null){
            throw new MiExcepcion("Ese empleado no existe");
        }
        else{
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar al empleado" +empleado.getPrimerNombre()+" "+empleado.getPrimerApellido());
            if(opc==JOptionPane.YES_OPTION){
                if(this.empleadoDao.eliminar(cedulaEmpleado)){
                    JOptionPane.showMessageDialog(null, "Empleado "+empleado.getPrimerNombre()+" "+empleado.getPrimerApellido()+" eliminado");
                }
                else{
                    throw new MiExcepcion("Error al eliminar empleado");
                }
            }
        }
    }

    private void listarEmpleados() {
        ControladorListaEmpleados controlador = new ControladorListaEmpleados(new VistaListaEmpleados(),this.vista,this.empleadoDao,this.sedeDao);
    }

    private void cargarCombo() {
        LinkedList<Sede> sedes = this.sedeDao.listar();
        for (Sede sede : sedes) {
            this.vista.comboSedes.addItem(sede.getNombreSede());
        }
    }

}

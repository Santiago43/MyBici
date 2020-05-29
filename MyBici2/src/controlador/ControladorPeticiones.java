package controlador;

import excepcion.MiExcepcion;
import funciones.Verificador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import modelo.dao.EmpleadosDao;
import modelo.dao.PeticionDao;
import modelo.dto.Empleado;
import modelo.dto.Peticion;
import modelo.dto.Usuario;
import vista.VistaListaPeticiones;
import vista.VistaPeticiones;
import vista.VistaPrincipal;

/**
 * Controlador de peticiones de empleados
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-27
 */
public class ControladorPeticiones implements ActionListener {

    private final VistaPrincipal vistaAnterior;
    private final VistaPeticiones vista;
    private final EmpleadosDao empleadosDao;
    private final PeticionDao peticionDao;
    private final Usuario usuario;

    /**
     *
     * @param vistaAnterior
     * @param vista
     * @param empleadosDao
     * @param peticionDao
     * @param usuario
     */
    public ControladorPeticiones(VistaPrincipal vistaAnterior, VistaPeticiones vista, EmpleadosDao empleadosDao, PeticionDao peticionDao,Usuario usuario) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.empleadosDao = empleadosDao;
        this.peticionDao = peticionDao;
        this.usuario=usuario;
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.btnPendientes.addActionListener(this);
        this.vista.btnListar.setEnabled(Verificador.tienePermiso(usuario, "listar peticion"));
        this.vista.btnPendientes.setEnabled(Verificador.tienePermiso(usuario, "listar peticion"));
        this.vista.btnEliminar.setEnabled(Verificador.tienePermiso(usuario, "eliminar peticion"));
        this.vista.btnModificar.setEnabled(Verificador.tienePermiso(usuario,"modificar peticion"));
        this.vista.radioSi.setEnabled(Verificador.tienePermiso(usuario,"aprobar peticion"));
        this.vista.radioNo.setEnabled(Verificador.tienePermiso(usuario, "aprobar peticion"));
        this.vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }

        });
        this.vista.setVisible(true);
        this.vistaAnterior.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnInsertar)) {
                insertarPeticion();

            } else if (e.getSource().equals(this.vista.btnConsultar)) {
                consultarPeticion();
            } else if (e.getSource().equals(this.vista.btnModificar)) {
                actualizarPeticion();
            } else if (e.getSource().equals(this.vista.btnEliminar)) {
                eliminarPeticion();
            } else if (e.getSource().equals(this.vista.btnListar)) {
                listarPeticiones();
            }else if(e.getSource().equals(this.vista.btnPendientes)){
                listarPeticionesPendientes();
            } 
            else if (e.getSource().equals(this.vista.btnRegresar)) {
                salir();
            }
        }catch (MiExcepcion ex){
            JOptionPane.showMessageDialog(null,"Error: "+ex.getMessage());
        }

    }

    private void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    private void insertarPeticion() throws MiExcepcion {
        Peticion peticion = new Peticion();
        String cedula = MiExcepcion.capturaString(this.vista.txtCCEmpleado);
        Empleado empleado = this.empleadosDao.consultar(cedula);
        if(empleado==null){
            throw new MiExcepcion("dse empleado no existe");
        }else{
            peticion.setEmpleado(empleado);
        }
        String descripcion = this.vista.txtDescripcionPeticion.getText();
        if(descripcion.equals("")){
            throw new MiExcepcion("debe agregar una descripción");
        }
        else{
            peticion.setPeticion(descripcion);
        }
        if(this.peticionDao.crear(peticion)){
            JOptionPane.showMessageDialog(null, "Petición creada");
            this.vista.limpiar();;
        }
        else{
            throw new MiExcepcion("Error al crear la petición");
        }
    }

    private void consultarPeticion() throws MiExcepcion {
        String idPeticion = JOptionPane.showInputDialog("Digite el id de la petición a consultar");
        Peticion peticion = this.peticionDao.consultar(idPeticion);
        if(peticion ==null){
            throw new MiExcepcion("Esa petición no existe");
        }else{
            this.vista.txtIDPeticion.setText(String.valueOf(peticion.getIdPeticionEmpleado()));
            this.vista.txtCCEmpleado.setText(peticion.getEmpleado().getCedula());
            this.vista.txtDescripcionPeticion.setText(peticion.getPeticion());
            if(peticion.isAprobado()){
                this.vista.radioSi.setSelected(true);
            }else{
                this.vista.radioNo.setSelected(true);
            }
        }
    }

    private void actualizarPeticion() throws MiExcepcion {
        Peticion peticion = this.peticionDao.consultar(MiExcepcion.capturaString(this.vista.txtIDPeticion));
        if(peticion==null){
            throw new MiExcepcion("Esa petición no existe");
        }
        String cedula = MiExcepcion.capturaString(this.vista.txtCCEmpleado);
        Empleado empleado = this.empleadosDao.consultar(cedula);
        if(empleado==null){
            throw new MiExcepcion("dse empleado no existe");
        }else{
            peticion.setEmpleado(empleado);
        }
        String descripcion = this.vista.txtDescripcionPeticion.getText();
        if(descripcion.equals("")){
            throw new MiExcepcion("debe agregar una descripción");
        }
        else{
            peticion.setPeticion(descripcion);
        }
        peticion.setAprobado(this.vista.radioSi.isSelected());
        if(this.peticionDao.actualizar(peticion)){
            JOptionPane.showMessageDialog(null, "Petición "+peticion.getIdPeticionEmpleado()+" actualizada");
            this.vista.limpiar();
        }
        else{
            throw new MiExcepcion("Error al crear la petición");
        }
    }

    private void eliminarPeticion() throws MiExcepcion {
        String idPeticion = JOptionPane.showInputDialog("Digite el id de la petición que desea eliminar");
        if(idPeticion.equals("")){
            throw new MiExcepcion("No puede dejar este campo vacío");
        }
        else{
            Peticion peticion = this.peticionDao.consultar(idPeticion);
            if(peticion==null){
                throw new MiExcepcion("Esa petición no existe");
            }else{
                int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la petición "+peticion.getIdPeticionEmpleado()+"?");
                if(opc==JOptionPane.YES_OPTION){
                    if(this.peticionDao.eliminar(idPeticion)){
                        JOptionPane.showMessageDialog(null, "Petición "+peticion.getIdPeticionEmpleado()+" eliminada");
                    }
                }
            }
            
        }
    }

    private void listarPeticiones() {
        ControladorListaPeticiones controlador = new ControladorListaPeticiones(this.vista,new VistaListaPeticiones(),this.peticionDao);
    }

    private void listarPeticionesPendientes(){
        ControladorListaPeticiones controlador;
        controlador = new ControladorListaPeticiones(this.vista,new VistaListaPeticiones(),this.peticionDao.listarPendientes());
    }
}

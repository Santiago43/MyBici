package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.dao.ClienteDao;
import modelo.dao.DireccionDao;
import modelo.dto.Calle;
import modelo.dto.Carrera;
import modelo.dto.Cliente;
import modelo.dto.Direccion;
import vista.VistaClientes;
import vista.VistaListaClientes;
import vista.VistaPrincipal;

/**
 * Controlador de cliente
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class ControladorClientes implements ActionListener {

    private VistaPrincipal vistaAnterior;
    private VistaClientes vista;
    private ClienteDao clienteDao;
    private DireccionDao direccionDao;

    public ControladorClientes(VistaPrincipal vistaAnterior, VistaClientes vista, ClienteDao clienteDao, DireccionDao direccionDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.clienteDao = clienteDao;
        this.direccionDao = direccionDao;
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
        this.vistaAnterior.setVisible(false);
        this.vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnInsertar)) {
                crearCliente();
            } else if (e.getSource().equals(this.vista.btnConsultar)) {
                consultarCliente();
            } else if (e.getSource().equals(this.vista.btnModificar)) {
                actualizarCliente();
            } else if (e.getSource().equals(this.vista.btnEliminar)) {
                eliminarCliente();
            } else if (e.getSource().equals(this.vista.btnListar)) {
                listarClientes();
            } else if (e.getSource().equals(this.vista.btnRegresar)) {
                salir();
            }
        }catch(MiExcepcion ex){
            JOptionPane.showMessageDialog(null, "Error: "+ex.getMessage());
        }

    }

    private void crearCliente() throws MiExcepcion {
        Cliente cliente = new Cliente();
        cliente.setCedula(MiExcepcion.capturaString(this.vista.txtCedula));
        cliente.setPrimerNombre(MiExcepcion.capturaString(this.vista.txtPrimerNombre));
        cliente.setSegundoNombre(this.vista.txtSegundoNombre.getText());
        cliente.setPrimerApellido(MiExcepcion.capturaString(this.vista.txtPrimerApellido));
        cliente.setSegundoApellido(MiExcepcion.capturaString(this.vista.txtSegundoApellido));
        cliente.setNacionalidad(MiExcepcion.capturaString(this.vista.txtNacionalidad));
        cliente.setFechaNacimiento(((JTextField)(this.vista.FecNacimiento.getDateEditor().getUiComponent())).getText());
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
        cliente.setGenero(String.valueOf(genero));
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
        cliente.setDireccion(direccionConfirmada);
        if(this.clienteDao.consultar(cliente.getCedula())!=null){
            throw new MiExcepcion("Ya existe un cliente con esa cédula");
        }else{
            if(this.clienteDao.crear(cliente)){
                JOptionPane.showMessageDialog(null, "Cliente creado satisfactoriamente");
            }
        }
    }

    private void consultarCliente() throws MiExcepcion {
        Cliente cliente = this.clienteDao.consultar(MiExcepcion.capturaString(this.vista.txtCedula));
        if(cliente==null){
            throw new MiExcepcion("Ese cliente no existe");
        }else{
            this.vista.txtCedula.setText(cliente.getCedula());
            this.vista.txtPrimerNombre.setText(cliente.getPrimerNombre());
            this.vista.txtPrimerNombre.setText(cliente.getPrimerNombre());
            this.vista.txtSegundoNombre.setText(cliente.getSegundoNombre());
            this.vista.txtPrimerApellido.setText(cliente.getPrimerApellido());
            this.vista.txtSegundoApellido.setText(cliente.getSegundoApellido());
            this.vista.txtNacionalidad.setText(cliente.getNacionalidad());
            ((JTextField) this.vista.FecNacimiento.getDateEditor().getUiComponent()).setText(cliente.getFechaNacimiento());
            switch (cliente.getGenero().charAt(0)) {
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
            Direccion direccion = cliente.getDireccion();
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
    }

    private void actualizarCliente() throws MiExcepcion {
        String cedula = MiExcepcion.capturaString(this.vista.txtCedula);
        Cliente cliente = this.clienteDao.consultar(cedula);
        if (cliente == null) {
            throw new MiExcepcion("Ese cliente no existe");
        }
        cliente.setCedula(MiExcepcion.capturaString(this.vista.txtCedula));
        cliente.setPrimerNombre(MiExcepcion.capturaString(this.vista.txtPrimerNombre));
        cliente.setSegundoNombre(this.vista.txtSegundoNombre.getText());
        cliente.setPrimerApellido(MiExcepcion.capturaString(this.vista.txtPrimerApellido));
        cliente.setSegundoApellido(MiExcepcion.capturaString(this.vista.txtSegundoApellido));
        cliente.setFechaNacimiento(MiExcepcion.capturaString((JTextField) this.vista.FecNacimiento.getDateEditor().getUiComponent()));
        cliente.setNacionalidad(MiExcepcion.capturaString(this.vista.txtNacionalidad));
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
        cliente.setGenero(String.valueOf(genero));
        Direccion direccion = this.direccionDao.consultar(String.valueOf(cliente.getDireccion().getIdDireccion()));
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
        if (this.direccionDao.actualizar(direccion)) {

        }
        if (this.clienteDao.actualizar(cliente)) {
            JOptionPane.showMessageDialog(null, "cliente Actualizado satisfactoriamente");
        } else {
            throw new MiExcepcion("error al modificar cliente");
        }
    }

    private void eliminarCliente() throws MiExcepcion {
        String cedulacliente = MiExcepcion.capturaString(this.vista.txtCedula);
        Cliente cliente = this.clienteDao.consultar(cedulacliente);
        if (cliente == null) {
            throw new MiExcepcion("Ese cliente no existe");
        } else {
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar al cliente" + cliente.getPrimerNombre() + " " + cliente.getPrimerApellido());
            if (opc == JOptionPane.YES_OPTION) {
                if (this.clienteDao.eliminar(cedulacliente)) {
                    JOptionPane.showMessageDialog(null, "cliente " + cliente.getPrimerNombre() + " " + cliente.getPrimerApellido() + " eliminado");
                } else {
                    throw new MiExcepcion("Error al eliminar cliente");
                }
            }
        }
    }

    private void listarClientes() {
        ControladorListaClientes controlador = new ControladorListaClientes(this.vista,new VistaListaClientes(),this.clienteDao);
    }

    private void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

}

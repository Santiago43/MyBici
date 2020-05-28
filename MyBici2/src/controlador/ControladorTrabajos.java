package controlador;

import excepcion.MiExcepcion;
import funciones.Verificador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dao.BicicletaDao;
import modelo.dao.EmpleadosDao;
import modelo.dao.FacturaDao;
import modelo.dao.UsuariosDao;
import modelo.dao.TrabajosDAO;
import modelo.dto.Bicicleta;
import modelo.dto.Cliente;
import modelo.dto.Empleado;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimienroBicicleta;
import modelo.dto.Usuario;
import modelo.dto.ValoresFinancieros;
import vista.VistaPrincipal;
import vista.VistaTrabajos;

/**
 *
 * @author Andrés C. López R.
 */
public class ControladorTrabajos implements ActionListener {

    //Objetos vista
    VistaPrincipal vistaPrincipal;
    VistaTrabajos vista;

    //Objetos para control de datos
    Cliente cliente;
    Empleado empleado;
    FacturaVenta factura;

    //Objetos para la manipulación de datos
    Bicicleta bicicleta;
    BicicletaDao bicicletaDao = new BicicletaDao();
    FacturaDao facturaDao =  new FacturaDao();
    EmpleadosDao empleadosDao;
    TrabajosDAO trabajosDAO;
    Usuario usuario;
    UsuariosDao usuariosDao;
    ValoresFinancieros valoresFinancieros;

    //Objetos para ultilidades
    Calendar fecha = new GregorianCalendar();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int mes = fecha.get(Calendar.MONTH);
    int año = fecha.get(Calendar.YEAR);
    String idmantenimiento = "";
    Date sendF;
    long d;
    java.sql.Date sendD;
    String fechV = dia + "/" + mes + "/" + año;
    Date date1;

    ControladorTrabajos(VistaPrincipal vista, VistaTrabajos vistaTrabajos, UsuariosDao usuariosDao, Usuario usuario, TrabajosDAO trabajosDAO, EmpleadosDao empleadosDao, ValoresFinancieros valoresFinancieros) {
        this.vista = vistaTrabajos;
        this.vistaPrincipal = vista;
        this.empleadosDao = empleadosDao;
        this.usuario = usuario;
        this.usuariosDao = usuariosDao;
        this.trabajosDAO = trabajosDAO;
        this.valoresFinancieros = valoresFinancieros;
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.btnBuscarBici.addActionListener(this);
        this.vista.setVisible(true);
        this.vistaPrincipal.setVisible(false);
        this.vista.setLocationRelativeTo(null);
    }
//Agregar un atributo de terminado o en proceso para la parte del mantenimiento

    @Override
    public void actionPerformed(ActionEvent e) {
        //--- Condiciones CRUD
        if (e.getSource().equals(this.vista.btnInsertar)) {
            cliente = new Cliente();
            factura = new FacturaVenta();
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento == null) {
                    //Datos cliente
                    cliente.setCedula(MiExcepcion.capturaString(this.vista.txtCedulaCliente));
                    //Datos Empleado
                    empleado = this.empleadosDao.consultar((usuario.getEmpleado().getCedula()));
                    //Datos de la factura
                    factura.setId(MiExcepcion.capturaEntero(this.vista.txtIDFactura));
                    factura.setCliente(cliente);
                    factura.setEmpleado(empleado);
                    date1 = new SimpleDateFormat("yyyy/MM/dd").parse(fechV);
                    d = date1.getTime();
                    sendD = new java.sql.Date(d);
                    factura.setFecha(sendD);
                    double totalMantenimiento;
                    if (this.vista.rbtnSiIVA.isSelected()) {
                        factura.setIva(valoresFinancieros.getIva());
                        totalMantenimiento = facturaDao.calculoIVA(valoresFinancieros.getIva(), MiExcepcion.capturaDouble(this.vista.txtSubTotal));
                        this.vista.txtValorMantenimiento.setText("$ " + totalMantenimiento);
                    }else{
                        totalMantenimiento = MiExcepcion.capturaDouble(this.vista.txtSubTotal);
                        this.vista.txtValorMantenimiento.setText("$ " + totalMantenimiento);
                    }
                    factura.setTotal(totalMantenimiento);
                    //Datos del mantenimiento
                    mantenimiento = new MantenimienroBicicleta();
                    mantenimiento.setId(MiExcepcion.capturaEntero(this.vista.txtIDMantenimiento));
                    mantenimiento.setFactura(factura);
                    if (bicicletaDao.consultar(MiExcepcion.capturaString(this.vista.txtSerial)) == null) {
                        bicicleta = new Bicicleta();
                        bicicleta.setMarcoSerial(MiExcepcion.capturaString(this.vista.txtSerial));
                        bicicleta.setMarca(MiExcepcion.capturaString(this.vista.txtMarca));
                        bicicleta.setColor(MiExcepcion.capturaString(this.vista.txtColor));
                        bicicleta.setGrupoMecanico(MiExcepcion.capturaString(this.vista.txtGrupo));
                        bicicleta.setEstado(MiExcepcion.capturaString(this.vista.txtEstado));
                        bicicleta.setValorEstimado(MiExcepcion.capturaDouble(this.vista.txtValorEstimado));
                    } else {
                        e.setSource(this.vista.btnBuscarBici);
                    }
                    mantenimiento.setBicicleta(bicicleta);
                    sendF = this.vista.FecEntrega.getDate();
                    d = sendF.getTime();
                    sendD = new java.sql.Date(d);
                    mantenimiento.setFechaEntrega(sendD);
                    mantenimiento.setDescripccion(this.vista.txtDescripcion.getText());
                    if(this.vista.rbtnEnProceso.isSelected()){
                        mantenimiento.setEstado(true);
                    }else if(this.vista.rbtnTerminado.isSelected()){
                        mantenimiento.setEstado(false);
                    }
                    if (this.trabajosDAO.crear(bicicleta, mantenimiento, factura)) {
                        JOptionPane.showMessageDialog(null, "Bicicleta Registrada.\nMantenimiento registrado\nFactura generada");
                    } else {
                        JOptionPane.showMessageDialog(null, "Mantenimiento no guardado");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID " + this.vista.txtIDMantenimiento.getText() + " ya existe");
                }
            } catch (MiExcepcion | ParseException ex) {
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnBuscarBici)) {
            try {
                bicicleta = bicicletaDao.consultar(MiExcepcion.capturaString(this.vista.txtSerial));
                this.vista.txtMarca.setText(bicicleta.getMarca());
                this.vista.txtColor.setText(bicicleta.getColor());
                this.vista.txtGrupo.setText(bicicleta.getGrupoMecanico());
                this.vista.txtEstado.setText(bicicleta.getEstado());
                this.vista.txtValorEstimado.setText(String.valueOf(bicicleta.getValorEstimado()));
                JOptionPane.showMessageDialog(null, "Se encontró una bicilceta registrada con el serial " + this.vista.txtSerial.getText());
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnConsultar)) {
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento != null) {
                    this.vista.txtIDFactura.setText(Integer.toString(mantenimiento.getFactura().getId()));
                    this.vista.txtCedulaCliente.setText(mantenimiento.getFactura().getCliente().getCedula());
                    this.vista.txtSerial.setText(mantenimiento.getBicicleta().getMarcoSerial());
                    this.vista.txtMarca.setText(mantenimiento.getBicicleta().getMarca());
                    this.vista.txtColor.setText(mantenimiento.getBicicleta().getColor());
                    this.vista.txtGrupo.setText(mantenimiento.getBicicleta().getGrupoMecanico());
                    this.vista.txtEstado.setText(mantenimiento.getBicicleta().getEstado());
                    this.vista.txtValorEstimado.setText(String.valueOf(mantenimiento.getBicicleta().getValorEstimado()));
                    this.vista.FecEntrega.setDate(mantenimiento.getFechaEntrega());
                    this.vista.txtDescripcion.setText(mantenimiento.getDescripccion());
                    if(mantenimiento.isEstado()){
                        this.vista.rbtnEnProceso.setSelected(true);
                    }else{
                        this.vista.rbtnTerminado.setSelected(true);
                    }
                    if (mantenimiento.getFactura().getIva() != 0) {
                        this.vista.rbtnSiIVA.setSelected(true);
                        this.vista.txtSubTotal.setText("$ " + facturaDao.sinIva(valoresFinancieros.getIva(), mantenimiento.getFactura().getTotal()));
                    }else{
                        this.vista.txtSubTotal.setText("$ " + mantenimiento.getFactura().getTotal());
                    }
                    this.vista.txtValorMantenimiento.setText("$ " + mantenimiento.getFactura().getTotal());
                    noEditabel();
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID " + this.vista.txtIDMantenimiento.getText() + " no existe");
                }

            } catch (MiExcepcion | NullPointerException ex) {
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnModificar)) {
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento != null) {
                    //Desabilitar o abilitar los campos que se pueden editar, esto dependiendo de los persmosos del usuario 
                    mantenimiento.setDescripccion(this.vista.txtDescripcion.getText());
                    //Mejorar metodo actualizar en el dao
                    if (this.trabajosDAO.actualiazar(mantenimiento)) {
                        JOptionPane.showMessageDialog(null, "Datos del mantenimineto " + this.vista.txtIDMantenimiento.getText() + " actualizados correctamente!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID " + this.vista.txtIDMantenimiento.getText() + " no existe");
                }
            } catch (MiExcepcion ex) {
                JOptionPane.showMessageDialog(null, "Ingresa un ID de mantenimiento para poder editarlo");
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnEliminar)) {
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento != null) {
                    //Mejorar metodo eliminar en el dao
                    if (this.trabajosDAO.eliminar(mantenimiento)) {
                        JOptionPane.showMessageDialog(null, "Registro de mantenimiento eliminado exitosamente");
                    }
                    limpiar();
                }
            } catch (MiExcepcion ex) {
                JOptionPane.showMessageDialog(null, "Identificador del mantenimiento no fue encontrado");
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (e.getSource().equals(this.vista.btnListar)) {

        }
        //--- Condiciones para funcionalidad
        if (e.getSource().equals(this.vista.btnRegresar)) {
            this.vistaPrincipal.setVisible(true);
            this.vista.dispose();
        }
        if (e.getSource().equals(this.vista.btnLimpiar)) {
            limpiar();
        }
    }

    private void limpiar() {
        this.vista.txtIDMantenimiento.setText("");
        this.vista.txtIDFactura.setText("");
        this.vista.txtCedulaCliente.setText("");
        this.vista.txtSerial.setText("");
        this.vista.FecEntrega.setDateFormatString(dia + "/" + mes + "/" + año);
        this.vista.txtMarca.setText("");
        this.vista.txtColor.setText("");
        this.vista.txtGrupo.setText("");
        this.vista.txtEstado.setText("");
        this.vista.txtValorEstimado.setText("");
        this.vista.txtDescripcion.setText("");
        this.vista.txtValorMantenimiento.setText("");
        this.vista.txtSubTotal.setText("");
        editabel();
    }

    private void noEditabel() {
        this.vista.txtIDMantenimiento.setEditable(false);
        this.vista.txtIDFactura.setEditable(false);
        this.vista.txtCedulaCliente.setEditable(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.txtSerial.setEditable(false);
        this.vista.txtMarca.setEditable(false);
        this.vista.txtColor.setEditable(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.txtGrupo.setEditable(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.txtEstado.setEditable(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.txtValorEstimado.setEditable(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.txtValorMantenimiento.setEditable(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.rbtnNoIVA.setEnabled(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.rbtnSiIVA.setEnabled(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.FecEntrega.setEnabled(Verificador.tienePermiso(this.usuario, "sede"));
        this.vista.txtSubTotal.setEnabled(Verificador.tienePermiso(this.usuario, "sede"));
    }

    public void editabel() {
        this.vista.txtIDMantenimiento.setEditable(true);
        this.vista.txtIDFactura.setEditable(true);
        this.vista.txtCedulaCliente.setEditable(true);
        this.vista.txtSerial.setEditable(true);
        this.vista.txtMarca.setEditable(true);
        this.vista.txtColor.setEditable(true);
        this.vista.txtGrupo.setEditable(true);
        this.vista.txtEstado.setEditable(true);
        this.vista.txtValorEstimado.setEditable(true);
        this.vista.txtValorMantenimiento.setEditable(true);
        this.vista.rbtnNoIVA.setEnabled(true);
        this.vista.rbtnSiIVA.setEnabled(true);
        this.vista.FecEntrega.setEnabled(true);
        this.vista.txtSubTotal.setEnabled(true);
    }
}

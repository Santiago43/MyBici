package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.dao.BicicletaDao;
import modelo.dao.ClienteDao;
import modelo.dao.EmpleadosDao;
import modelo.dao.FacturaDao;
import modelo.dao.UsuariosDao;
import modelo.dao.TrabajosDAO;
import modelo.dto.Bicicleta;
import modelo.dto.Cliente;
import modelo.dto.Empleado;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimientoBicicleta;
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
    ClienteDao clienteDao = new ClienteDao();
    FacturaDao facturaDao = new FacturaDao();
    EmpleadosDao empleadosDao;
    TrabajosDAO trabajosDAO;
    Usuario usuario;
    UsuariosDao usuariosDao;
    ValoresFinancieros valoresFinancieros;
    DefaultTableModel modeloTabla;

    //Objetos para ultilidades
    Calendar fecha = new GregorianCalendar();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int mes = fecha.get(Calendar.MONTH);
    int año = fecha.get(Calendar.YEAR);
    String idmantenimiento = "";
    Date sendF;
    long d;
    java.sql.Date sendD;
    String fechV = año + "-" + mes + "-" + dia;
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
        this.vista.btnNuevo.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.btnBuscarBici.addActionListener(this);
        this.vista.btnResetBici.addActionListener(this);
        this.vista.btnResetBici.setVisible(false);
        this.vista.btnModificar.setEnabled(false);
        this.vista.btnEliminar.setEnabled(false);
        this.modeloTabla = (DefaultTableModel) this.vista.tblMantenimiento.getModel();
        this.vista.setVisible(true);
        this.vistaPrincipal.setVisible(false);
        this.vista.setLocationRelativeTo(null);
        this.vista.txtValorMantenimiento.setEditable(false);
    }
//Agregar un atributo de terminado o en proceso para la parte del mantenimiento

    @Override
    public void actionPerformed(ActionEvent e) {
        //--- Condiciones CRUD
        if (e.getSource().equals(this.vista.btnInsertar)) {
            factura = new FacturaVenta();
            this.vista.txtIDFactura.setText(String.valueOf(facturaDao.idFactrura()));
            this.vista.txtIDMantenimiento.setText(String.valueOf(this.trabajosDAO.idMantenimineto()));
            editableID(false);
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimientoBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento == null) {
                    //Datos cliente
                    cliente = clienteDao.consultar(MiExcepcion.capturaString(this.vista.txtCedulaCliente));
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(null, "No se encontro ningun cliente registrado con C.C: " + this.vista.txtCedulaCliente.getText());
                    } else {
                        //Datos Empleado
                        empleado = this.empleadosDao.consultar(this.vista.txtCCEmpleado.getText());
                        if (empleado == null) {
                            JOptionPane.showMessageDialog(null, "No se encontro ningun empleado registrado con C.C: " + this.vista.txtCCEmpleado.getText());
                        } else {
                            //Datos de la factura
                            factura.setId(MiExcepcion.capturaEntero(this.vista.txtIDFactura));
                            factura.setCliente(cliente);
                            factura.setEmpleado(empleado);
                            factura.setFecha(fechV);
                            double totalMantenimiento;
                            if (this.vista.rbtnSiIVA.isSelected()) {
                                factura.setIva(valoresFinancieros.getIva() * MiExcepcion.capturaDouble(this.vista.txtSubTotal));
                                totalMantenimiento = facturaDao.calculoIVA(valoresFinancieros.getIva(), MiExcepcion.capturaDouble(this.vista.txtSubTotal));
                                this.vista.txtValorMantenimiento.setText("$ " + totalMantenimiento);
                            } else {
                                totalMantenimiento = MiExcepcion.capturaDouble(this.vista.txtSubTotal);
                                this.vista.txtValorMantenimiento.setText("$ " + totalMantenimiento);
                            }
                            if (totalMantenimiento != 0) {
                                factura.setTotal(totalMantenimiento);
                                //Datos del mantenimiento
                                mantenimiento = new MantenimientoBicicleta();
                                mantenimiento.setId(MiExcepcion.capturaEntero(this.vista.txtIDMantenimiento));
                                mantenimiento.setFactura(factura);
                                bicicleta = bicicletaDao.consultar(MiExcepcion.capturaString(this.vista.txtSerial));
                                if (bicicleta == null) {
                                    bicicleta = new Bicicleta();
                                    bicicleta.setMarcoSerial(MiExcepcion.capturaString(this.vista.txtSerial));
                                    bicicleta.setMarca(MiExcepcion.capturaString(this.vista.txtMarca));
                                    bicicleta.setColor(MiExcepcion.capturaString(this.vista.txtColor));
                                    bicicleta.setGrupoMecanico(MiExcepcion.capturaString(this.vista.txtGrupo));
                                    bicicleta.setEstado(MiExcepcion.capturaString(this.vista.txtEstado));
                                    bicicleta.setValorEstimado(MiExcepcion.capturaDouble(this.vista.txtValorEstimado));
                                } else {
                                    buscarbici();
                                }
                                mantenimiento.setBicicleta(bicicleta);
                                sendF = this.vista.FecEntrega.getDate();
                                d = sendF.getTime();
                                sendD = new java.sql.Date(d);
                                mantenimiento.setFechaEntrega(MiExcepcion.capturaString((JTextField) this.vista.FecEntrega.getDateEditor().getUiComponent()));
                                mantenimiento.setDescripccion(this.vista.txtDescripcion.getText());
                                if (this.vista.rbtnEnProceso.isSelected()) {
                                    mantenimiento.setEstado(true);
                                } else if (this.vista.rbtnTerminado.isSelected()) {
                                    mantenimiento.setEstado(false);
                                }
                                if (this.trabajosDAO.crear(bicicleta, mantenimiento, factura)) {
                                    JOptionPane.showMessageDialog(null, "Bicicleta Registrada: Serial " + this.vista.txtSerial.getText() + ".\nMantenimiento registrado: ID " + this.vista.txtIDMantenimiento.getText() + "\nFactura generada: ID " + this.vista.txtIDFactura.getText());
                                } else {
                                    JOptionPane.showMessageDialog(null, "Mantenimiento no guardado");
                                }
                                editableBici(true);
                            }

                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID " + this.vista.txtIDMantenimiento.getText() + " ya existe");
                }
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
            limpiar();
        }
        if (e.getSource().equals(this.vista.btnBuscarBici)) {
            buscarbici();
        }
        if (e.getSource().equals(this.vista.btnConsultar)) {
            editableID(false);
            editableBici(false);
            editableFactura(false);
            editableMantenimiento(false);
            this.vista.FecEntrega.setEnabled(true);
            this.vista.txtDescripcion.setEnabled(true);
            this.vista.btnModificar.setEnabled(true);
            this.vista.btnEliminar.setEnabled(true);
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimientoBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento != null) {
                    this.vista.txtIDFactura.setText(Integer.toString(mantenimiento.getFactura().getId()));
                    this.vista.txtCedulaCliente.setText(mantenimiento.getFactura().getCliente().getCedula());
                    this.vista.txtSerial.setText(mantenimiento.getBicicleta().getMarcoSerial());
                    this.vista.txtMarca.setText(mantenimiento.getBicicleta().getMarca());
                    this.vista.txtColor.setText(mantenimiento.getBicicleta().getColor());
                    this.vista.txtGrupo.setText(mantenimiento.getBicicleta().getGrupoMecanico());
                    this.vista.txtEstado.setText(mantenimiento.getBicicleta().getEstado());
                    this.vista.txtValorEstimado.setText(String.valueOf(mantenimiento.getBicicleta().getValorEstimado()));
                    (((JTextField) this.vista.FecEntrega.getDateEditor().getUiComponent())).setText(mantenimiento.getFechaEntrega());
                    this.vista.txtDescripcion.setText(mantenimiento.getDescripccion());
                    if (mantenimiento.isEstado()) {
                        this.vista.rbtnEnProceso.setSelected(true);
                    } else {
                        this.vista.rbtnTerminado.setSelected(true);
                    }

                    if (mantenimiento.getFactura().getIva() > 0) {
                        this.vista.rbtnSiIVA.setSelected(true);
                        this.vista.txtSubTotal.setText("$ " + mantenimiento.getFactura().getIva());
                    } else {
                        this.vista.txtSubTotal.setText("$ " + mantenimiento.getFactura().getTotal());
                    }
                    this.vista.txtValorMantenimiento.setText("$ " + mantenimiento.getFactura().getTotal());
                    this.vista.txtCCEmpleado.setText(mantenimiento.getFactura().getEmpleado().getCedula());
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID " + this.vista.txtIDMantenimiento.getText() + " no existe");
                }
                editableBici(false);
                editableFactura(false);
            } catch (MiExcepcion | NullPointerException ex) {
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnModificar)) {
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimientoBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento != null) {
                    mantenimiento.setFechaEntrega(MiExcepcion.capturaString((JTextField) this.vista.FecEntrega.getDateEditor().getUiComponent()));
                    mantenimiento.setDescripccion(this.vista.txtDescripcion.getText());
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
            this.vista.btnModificar.setEnabled(false);
            this.vista.btnEliminar.setEnabled(false);
        }
        if (e.getSource().equals(this.vista.btnEliminar)) {
            if (aprobado()) {
                try {
                    idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                    MantenimientoBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                    if (mantenimiento != null) {

                        if (this.trabajosDAO.eliminar(mantenimiento)) {
                            JOptionPane.showMessageDialog(null, "Registro de mantenimiento eliminado exitosamente");
                        }
                        limpiar();
                    }
                } catch (MiExcepcion ex) {
                    JOptionPane.showMessageDialog(null, "Identificador del mantenimiento no fue encontrado");
                    Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.vista.btnModificar.setEnabled(false);
                this.vista.btnEliminar.setEnabled(false);
            }
        }
        if (e.getSource().equals(this.vista.btnListar)) {
            listar();
        }
        //--- Condiciones para funcionalidad
        if (e.getSource().equals(this.vista.btnNuevo)) {
            limpiar();
            this.vista.txtIDFactura.setText(String.valueOf(facturaDao.idFactrura()));
            this.vista.txtIDMantenimiento.setText(String.valueOf(this.trabajosDAO.idMantenimineto()));
            editableID(false);
            editableMantenimiento(true);
            editableFactura(true);
            editableBici(true);
        }
        if (e.getSource().equals(this.vista.btnResetBici)) {
            editableBici(true);
            this.vista.btnResetBici.setVisible(false);
        }
        if (e.getSource().equals(this.vista.btnRegresar)) {
            this.vistaPrincipal.setVisible(true);
            this.vista.dispose();
        }
        if (e.getSource().equals(this.vista.btnLimpiar)) {
            limpiar();
            listar();
        }
    }

    private void limpiar() {
        this.vista.txtIDMantenimiento.setText("");
        this.vista.txtIDFactura.setText("");
        this.vista.txtCedulaCliente.setText("");
        this.vista.txtSerial.setText("");
        this.vista.txtMarca.setText("");
        this.vista.txtColor.setText("");
        this.vista.txtGrupo.setText("");
        this.vista.txtEstado.setText("");
        this.vista.txtValorEstimado.setText("");
        this.vista.txtDescripcion.setText("");
        this.vista.txtValorMantenimiento.setText("");
        this.vista.txtSubTotal.setText("");
        this.vista.txtCCEmpleado.setText("");
        this.vista.FecEntrega.setDateFormatString("yyyy-MM-dd");
        editableBici(true);
        editableFactura(true);
        editableID(true);
        editableMantenimiento(true);
    }

    public void editableID(boolean bool) {
        this.vista.txtIDFactura.setEditable(bool);
        this.vista.txtIDMantenimiento.setEditable(bool);
    }

    public void editableMantenimiento(boolean bool) {
        this.vista.FecEntrega.setEnabled(bool);
        this.vista.txtSubTotal.setEnabled(bool);
        this.vista.txtDescripcion.setEnabled(bool);
    }

    public void editableBici(boolean bool) {
        this.vista.txtSerial.setEditable(bool);
        this.vista.txtMarca.setEditable(bool);
        this.vista.txtColor.setEditable(bool);
        this.vista.txtGrupo.setEditable(bool);
        this.vista.txtEstado.setEditable(bool);
        this.vista.txtValorEstimado.setEditable(bool);
    }

    public void editableFactura(boolean bool) {
        this.vista.txtCedulaCliente.setEditable(bool);
        this.vista.txtCCEmpleado.setEditable(bool);
        this.vista.rbtnNoIVA.setEnabled(bool);
        this.vista.rbtnSiIVA.setEnabled(bool);
    }

    public boolean aprobado() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Confirmación del Gerente:");
        JTextField user = new JTextField();
        JPasswordField pass = new JPasswordField(10);
        panel.add(label);
        panel.add(pass);
        String[] options = new String[]{"Aprobar", "Cancelar"};
        int option = JOptionPane.showOptionDialog(null, panel, "Eliminar",
                JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);
        String clave = "";
        for (char c : pass.getPassword()) {
            clave += c;
        }
        Usuario gerente = usuariosDao.consultar(clave);
        if (clave.equals(this.usuario.getContraseña())) {
            return true;
        }

        return false;
    }

    private void buscarbici() {
        try {
            bicicleta = bicicletaDao.consultar(MiExcepcion.capturaString(this.vista.txtSerial));
            this.vista.txtMarca.setText(bicicleta.getMarca());
            this.vista.txtColor.setText(bicicleta.getColor());
            this.vista.txtGrupo.setText(bicicleta.getGrupoMecanico());
            this.vista.txtEstado.setText(bicicleta.getEstado());
            this.vista.txtValorEstimado.setText(String.valueOf(bicicleta.getValorEstimado()));
            editableBici(false);
            this.vista.btnResetBici.setVisible(true);
            JOptionPane.showMessageDialog(null, "Se encontró una bicilceta registrada con el serial " + this.vista.txtSerial.getText());
        } catch (MiExcepcion ex) {
            Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listar() {
        LinkedList<MantenimientoBicicleta> mantenimientos = trabajosDAO.listar();
        while (this.modeloTabla.getRowCount() > 0) {
            this.modeloTabla.removeRow(0);
        }
        for (MantenimientoBicicleta mantenimiento : mantenimientos) {
            String estado;
            if (mantenimiento.isEstado()) {
                estado = "En porceso";
            } else {
                estado = "Entregado";
            }
            Object fila[] = {mantenimiento.getId(), mantenimiento.getFactura().getId(), mantenimiento.getFechaEntrega(), estado, mantenimiento.getDescripccion(), mantenimiento.getBicicleta().getMarcoSerial(), mantenimiento.getBicicleta().getMarca(), mantenimiento.getBicicleta().getColor(), mantenimiento.getBicicleta().getEstado()};
            this.modeloTabla.addRow(fila);
        }
    }
}

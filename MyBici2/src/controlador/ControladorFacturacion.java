package controlador;

import excepcion.MiExcepcion;
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
import modelo.dao.ClienteDao;
import modelo.dao.EmpleadosDao;
import modelo.dao.FacturaDao;
import modelo.dto.Cliente;
import modelo.dto.Empleado;
import modelo.dto.FacturaVenta;
import modelo.dto.Mercancia;
import vista.VistaFacturacion;
import vista.VistaPrincipal;

/**
 *
 * @author Andrés C. López R. || ACLXRD
 */
public class ControladorFacturacion implements ActionListener {

    //Objetos vista
    VistaPrincipal vistaPrincipal;
    VistaFacturacion vista;

    //Objetos para control de datos
    Cliente cliente;
    Empleado empleado;
    EmpleadosDao empleadosDao = new EmpleadosDao();
    FacturaVenta factura;
    ClienteDao clienteDao = new ClienteDao();
    FacturaDao facturaDao = new FacturaDao();
    Mercancia mercancia;
    String idFactura;

    //Objetos para utilidades
    Calendar fecha = new GregorianCalendar();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int mes = fecha.get(Calendar.MONTH);
    int año = fecha.get(Calendar.YEAR);
    String idmantenimiento = "";
    int idArticulo = 0;
    Date sendF;
    long d;
    java.sql.Date sendD;
    String fechV = dia + "/" + mes + "/" + año;
    Date date1;

    public ControladorFacturacion(VistaPrincipal vistaPrincipal, VistaFacturacion vista) {
        this.vistaPrincipal = vistaPrincipal;
        this.vista = vista;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vista.btnInsertar)) {
            this.vista.txtIDFactura.setText(String.valueOf(facturaDao.idFactrura()));
            factura = new FacturaVenta();
            mercancia = new Mercancia();
            try {
                idFactura = MiExcepcion.capturaString(this.vista.txtIDFactura);
                factura = facturaDao.consultar(idFactura);
                if (factura == null) {
                    cliente = clienteDao.consultar(MiExcepcion.capturaString(this.vista.txtCCCliente));
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(null, "No se encontro ningun cliente registrado con C.C: " + this.vista.txtCCEmpleado.getText());
                    } else {
                        //Datos Empleado
                        empleado = this.empleadosDao.consultar(this.vista.txtCCEmpleado.getText());
                        factura.setCliente(cliente);
                        factura.setEmpleado(empleado);
                        factura.setIva(MiExcepcion.capturaDouble(this.vista.txtIVA));
                        factura.setTotal(MiExcepcion.capturaDouble(this.vista.txtTotalVenta));
                        date1 = new SimpleDateFormat("yyyy/MM/dd").parse(fechV);
                        d = date1.getTime();
                        sendD = new java.sql.Date(d);
                        factura.setFecha(sendD);
                        facturaDao.crear(factura);
                    }
                }
            } catch (MiExcepcion | ParseException ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnConsultar)) {
            try {
                idFactura = MiExcepcion.capturaString(this.vista.txtIDFactura);
                factura = facturaDao.consultar(idFactura);
                if (factura != null) {
                    this.vista.txtIDFactura.setText(String.valueOf(factura.getId()));
                    this.vista.txtFechaVenta.setText(String.valueOf(factura.getFecha()));
                    this.vista.txtCCEmpleado.setText(factura.getEmpleado().getCedula());
                    this.vista.txtIVA.setText(String.valueOf(factura.getIva()));
                    this.vista.txtCCCliente.setText(factura.getCliente().getCedula());
                    this.vista.txtTotalVenta.setText(String.valueOf(factura.getTotal()));
                }
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource().equals(this.vista.btnBuscarInventario)){
            try {
                idArticulo = MiExcepcion.capturaEntero(this.vista.txtCodigoArticulo);
                
                
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void limpiarFactura(){
        this.vista.txtIDFactura.setText("");
        this.vista.txtCCCliente.setText("");
        this.vista.txtCCEmpleado.setText("");
        this.vista.txtFechaVenta.setText("");
        this.vista.txtIVA.setText("");
        this.vista.txtTotalVenta.setText("");
    }
    
    public void limpiarArticulos(){
        this.vista.txtCodigoArticulo.setText("");
        this.vista.txtNombreProducto.setText("");
        this.vista.txtStock.setText("");
        this.vista.txtProveedor.setText("");
        this.vista.txtCantidadComprar.setText("");
    }
}

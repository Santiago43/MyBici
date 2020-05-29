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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.dao.ClienteDao;
import modelo.dao.EmpleadosDao;
import modelo.dao.FacturaDao;
import modelo.dao.MercanciaDao;
import modelo.dao.ValoresFinancierosDao;
import modelo.dto.Cliente;
import modelo.dto.Empleado;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimientoBicicleta;
import modelo.dto.Mercancia;
import modelo.dto.ValoresFinancieros;
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
    MercanciaDao mercanciaDao = new MercanciaDao();
    String idFactura;
    LinkedList<Mercancia> articulos = new LinkedList<>();
    DefaultTableModel modeloTabla;
    DefaultTableModel modeloTabla2;
    ValoresFinancierosDao valoresFinancierosDao = new ValoresFinancierosDao();
    ValoresFinancieros valoresFinancieros = valoresFinancierosDao.consultar("");

    //Objetos para utilidades
    Calendar fecha = new GregorianCalendar();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int mes = fecha.get(Calendar.MONTH);
    int año = fecha.get(Calendar.YEAR);
    String idmantenimiento = "";
    String idArticulo = "";
    Date sendF;
    long d;
    java.sql.Date sendD;
    String fechV = año + "-" + mes + "-" + dia;
    Date date1;
    double precio;
    
    public ControladorFacturacion(VistaPrincipal vistaPrincipal, VistaFacturacion vista) {
        this.vistaPrincipal = vistaPrincipal;
        this.vista = vista;
        this.vista.btnArtFact.addActionListener(this);
        this.vista.btnBuscarFactura.addActionListener(this);
        this.vista.btnBuscarInventario.addActionListener(this);
        this.vista.btnCarrito.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.btnSacarCarrito.addActionListener(this);
        this.vista.btnlimpiarArt.addActionListener(this);
        this.vista.btnNuevo.addActionListener(this);
        enablebtnF(false);
        this.modeloTabla = (DefaultTableModel) this.vista.tblArticulos.getModel();
        this.modeloTabla2 = (DefaultTableModel) this.vista.tblFacturas.getModel();
        this.vista.setVisible(true);
        this.vistaPrincipal.setVisible(false);
        this.vista.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.vista.btnInsertar)) {
            try {
                this.vista.txtIDFactura.setText(String.valueOf(facturaDao.idFactrura()));
                factura = new FacturaVenta();
                mercancia = new Mercancia();
                idFactura = MiExcepcion.capturaString(this.vista.txtIDFactura);
                factura = facturaDao.consultar(idFactura);
                if (factura == null) {
                    cliente = clienteDao.consultar(MiExcepcion.capturaString(this.vista.txtCCCliente));
                    if (cliente == null) {
                        JOptionPane.showMessageDialog(null, "No se encontro ningun cliente registrado con C.C: " + this.vista.txtCCEmpleado.getText());
                    } else {
                        try {
                            //Datos Empleado
                            empleado = this.empleadosDao.consultar(this.vista.txtCCEmpleado.getText());
                            factura.setCliente(cliente);
                            factura.setEmpleado(empleado);
                            factura.setIva(MiExcepcion.capturaDouble(this.vista.txtIVA));
                            factura.setTotal(MiExcepcion.capturaDouble(this.vista.txtTotalVenta));
                            factura.setFecha(fechV);
                            facturaDao.crear(factura);
                            if (facturaDao.factura_has_mercancia(factura, articulos)) {
                                JOptionPane.showMessageDialog(null, "Factura registrada correctamente");
                            }
                        } catch (MiExcepcion ex) {
                            Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         if (e.getSource().equals(this.vista.btnNuevo)) {
            limpiar();
            this.vista.txtIDFactura.setText(String.valueOf(facturaDao.idFactrura()));
            this.vista.txtIDFactura.setEditable(false);
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
        if (e.getSource().equals(this.vista.btnBuscarInventario)) {
            try {
                idArticulo = MiExcepcion.capturaString(this.vista.txtCodigoArticulo);
                mercancia = mercanciaDao.consultar(idArticulo);
                if (mercancia != null) {
                    this.vista.txtNombreProducto.setText(mercancia.getNombre());
                    this.vista.txtStock.setText(String.valueOf(mercancia.getCantidad() - 1));
                    
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro un articulo registrado con el codigo " + this.vista.txtCodigoArticulo.getText());
                }

            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnCarrito)) {
            try {
                mercancia = mercanciaDao.consultar(MiExcepcion.capturaString(this.vista.txtCodigoArticulo));
                mercancia.setCantidad(MiExcepcion.capturaEntero(this.vista.txtCantidadComprar));
                articulos.add(mercancia);
                enablebtnF(true);
                listaCarrito(articulos);
                precio += mercancia.getCantidad() * mercancia.getPrecioVenta();
                this.vista.txtIVA.setText(String.valueOf(precio * valoresFinancieros.getIva()));
                this.vista.txtTotalVenta.setText(String.valueOf(precio * (1 + valoresFinancieros.getIva())));
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnSacarCarrito)) {
            sacarCarrito(articulos);
            limpiarCarrito();
        }
        if (e.getSource().equals(this.vista.btnListar)) {
            listar();
        }
        if (e.getSource().equals(this.vista.btnArtFact)) {
            try {
                listarArticulosdeFactura(MiExcepcion.capturaString(this.vista.txtIDFactura));
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnRegresar)) {
            this.vistaPrincipal.setVisible(true);
            this.vista.dispose();
        }
        if (e.getSource().equals(this.vista.btnlimpiarArt)) {
            limpiarArticulos();
        }
        if(e.getSource().equals(this.vista.btnBuscarFactura)){
            try {
                buscarartenfact(articulos, MiExcepcion.capturaEntero(this.vista.txtCodigoArticulo));
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void limpiar() {
        limpiarArticulos();
        limpiarFactura();
        enablebtnF(false);
        articulos = new LinkedList<>();
    }

    public void limpiarFactura() {
        this.vista.txtIDFactura.setText("");
        this.vista.txtCCCliente.setText("");
        this.vista.txtCCEmpleado.setText("");
        this.vista.txtFechaVenta.setText("");
        this.vista.txtIVA.setText("");
        this.vista.txtTotalVenta.setText("");
    }

    public void limpiarArticulos() {
        this.vista.txtCodigoArticulo.setText("");
        this.vista.txtNombreProducto.setText("");
        this.vista.txtStock.setText("");
        this.vista.txtCantidadComprar.setText("");
        limpiarCarrito();
    }

    public void enablebtnF(boolean bool) {
        this.vista.btnInsertar.setEnabled(bool);
        this.vista.btnLimpiar.setEnabled(bool);
        this.vista.btnConsultar.setEnabled(bool);
        this.vista.btnBuscarFactura.setEnabled(bool);
    }

    public void listaCarrito(LinkedList<Mercancia> articulos) {
        limpiarCarrito();
        for (Mercancia articulo : articulos) {
            Object fila[] = {articulo.getIdObjeto(), articulo.getNombre(), articulo.getCantidadCompra()};
            this.modeloTabla.addRow(fila);
        }
    }

    public void limpiarCarrito() {
        while (this.modeloTabla.getRowCount() > 0) {
            this.modeloTabla.removeRow(0);
        }
        articulos = new LinkedList<>();
    }

    public void sacarCarrito(LinkedList<Mercancia> articulos) {
        for (Mercancia articulo : articulos) {
            try {
                if (articulo.getIdObjeto() == MiExcepcion.capturaEntero(this.vista.txtCodigoArticulo)) {
                    articulos.remove(articulo);
                }
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorFacturacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        listaCarrito(articulos);
    }

    private void listar() {
        LinkedList<FacturaVenta> facturas = facturaDao.listar();
        while (this.modeloTabla2.getRowCount() > 0) {
            this.modeloTabla2.removeRow(0);
        }
        for (FacturaVenta factura : facturas) {
            Object fila[] = {factura.getId(), factura.getEmpleado().getCedula(), factura.getCliente().getCedula(), factura.getFecha(), factura.getIva(), factura.getTotal()};
            this.modeloTabla.addRow(fila);
        }

    }

    private void listarArticulosdeFactura(String idfv) {
        limpiarCarrito();
        LinkedList<Mercancia> mercancias = facturaDao.listarMercancia(idfv);
        listaCarrito(mercancias);
    }

    public void buscarartenfact(LinkedList<Mercancia> articulos, int idart) {
        limpiarCarrito();
        for (Mercancia articulo : articulos) {
            if (idart == articulo.getIdObjeto()) {
                Object fila[] = {articulo.getIdObjeto(), articulo.getNombre(), articulo.getCantidadCompra()};
                this.modeloTabla.addRow(fila);
            }

        }
    }
}

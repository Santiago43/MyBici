/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dao.EmpleadosDao;
import modelo.dao.RolesDao;
import modelo.dao.UsuariosDao;
import modelo.dao.TrabajosDAO;
import modelo.dto.Bicicleta;
import modelo.dto.Cliente;
import modelo.dto.Empleado;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimienroBicicleta;
import vista.VistaPrincipal;
import vista.VistaTrabajos;

/**
 *
 * @author andre
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
    EmpleadosDao empleadosDao;
    RolesDao rolesDao;
    TrabajosDAO trabajosDAO;
    UsuariosDao usuariosDao;

    //Objetos para ultilidades
    Calendar fecha = new GregorianCalendar();
    int dia = fecha.get(Calendar.DAY_OF_MONTH);
    int mes = fecha.get(Calendar.MONTH);
    int año = fecha.get(Calendar.YEAR);
    String idmantenimiento = "";

    ControladorTrabajos(VistaPrincipal vista, VistaTrabajos vistaTrabajos, UsuariosDao usuariosDao, RolesDao rolesDao, TrabajosDAO trabajosDAO, EmpleadosDao empleadosDao) {
        this.vista = vistaTrabajos;
        this.vistaPrincipal = vista;
        this.empleadosDao = empleadosDao;
        this.rolesDao = rolesDao;
        this.usuariosDao = usuariosDao;
        this.trabajosDAO = trabajosDAO;
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.setVisible(true);
        this.vistaPrincipal.setVisible(false);
        this.vista.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //--- Condiciones CRUD
        if (e.getSource().equals(this.vista.btnInsertar)) {
            cliente = new Cliente();
            empleado = new Empleado();
            factura = new FacturaVenta();
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento == null) {
                    //Datos cliente
                    //Es necesario hacer un dao cliente para traer la información.?
                    cliente.setCedula(Integer.parseInt(this.vista.txtCedulaCliente.getText()));
                    //Datos Empleado
                    empleado = this.empleadosDao.consultar(Integer.toString(empleado.getCedula()));
                    //Datos de la factura
                    factura.setId(Integer.parseInt(this.vista.txtIDFactura.getText()));
                    factura.setCliente(cliente);
                    factura.setEmpleado(empleado);
                    factura.setFecha(dia + "/" + mes + "/" + año);
                    if (this.vista.rbtnSiIVA.isSelected() == true) {
                        factura.setIva(0.19);
                    }
                    factura.setTotal(Integer.parseInt(this.vista.txtValorMantenimiento.getText()));
                    //Datos del mantenimiento
                    mantenimiento = new MantenimienroBicicleta();
                    mantenimiento.setId(Integer.parseInt(this.vista.txtIDMantenimiento.getText()));
                    mantenimiento.setFactura(factura);
                    //Validar la existencia de bicicletas previamente segistradas
                    bicicleta = new Bicicleta();
                    bicicleta.setMarcoSerial(this.vista.txtSerial.getText());
                    bicicleta.setMarca(this.vista.txtMarca.getText());
                    bicicleta.setColor(this.vista.txtColor.getText());
                    bicicleta.setGrupoMecanico(this.vista.txtGrupo.getText());
                    bicicleta.setEstado(this.vista.txtEstado.getText());
                    mantenimiento.setBicicleta(bicicleta);
                    mantenimiento.setFechaEntrega(this.vista.FecEntrega.getDateFormatString());
                    mantenimiento.setDescripccion(this.vista.txtDescripcion.getText());
                    mantenimiento.setValorEstimado(Integer.parseInt(this.vista.txtValorEstimado.getText()));
                    this.trabajosDAO.crear(bicicleta, mantenimiento, factura);
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID" + this.vista.txtIDMantenimiento.getText() + " ya existe");
                }
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
                    this.vista.txtSerial.setText(mantenimiento.getBicicleta().getMarcoSerial());
                    this.vista.txtMarca.setText(mantenimiento.getBicicleta().getMarca());
                    this.vista.txtColor.setText(mantenimiento.getBicicleta().getColor());
                    this.vista.txtGrupo.setText(mantenimiento.getBicicleta().getGrupoMecanico());
                    this.vista.txtEstado.setText(mantenimiento.getBicicleta().getEstado());
                    this.vista.txtEstado.setText(mantenimiento.getDescripccion());
                    this.vista.txtValorMantenimiento.setText(Integer.toString(mantenimiento.getFactura().getId()));
                    if (mantenimiento.getFactura().getIva() == 0.19) {
                        this.vista.rbtnSiIVA.setSelected(true);
                    }
                    this.vista.txtValorEstimado.setText("$ " + mantenimiento.getFactura().getTotal());
                    this.vista.txtDescripcion.setText(mantenimiento.getDescripccion());
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID" + this.vista.txtIDMantenimiento.getText() + " no existe");
                }

            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource().equals(this.vista.btnModificar)) {
            try {
                idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                if (mantenimiento != null) {
                    //Desabilitar o abilitar los campos que se pueden editar, esto dependiendo de los persmosos del usuario
                    this.vista.txtIDMantenimiento.setEditable(false);
                    this.vista.txtIDFactura.setEditable(false);
                    this.vista.txtSerial.setEditable(false);
                    this.vista.txtMarca.setEditable(false);
                    this.vista.txtColor.setEditable(false);
                    this.vista.txtGrupo.setEditable(false);
                    this.vista.txtEstado.setEditable(false);
                    this.vista.txtValorEstimado.setEditable(false);
                    this.vista.txtValorMantenimiento.setEditable(false);
                    mantenimiento.setDescripccion(this.vista.txtDescripcion.getText());
                    this.vista.rbtnNoIVA.setEnabled(false);
                    this.vista.rbtnSiIVA.setEnabled(false);
     //Mejorar metodo actualizar en el dao
                    this.trabajosDAO.actualiazar(mantenimiento);
                } else {
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID" + this.vista.txtIDMantenimiento.getText() + " no existe");
                }
            } catch (MiExcepcion ex) {
                JOptionPane.showMessageDialog(null, "Ingresa un ID de mantenimiento para poder editarlo");
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (e.getSource().equals(this.vista.btnEliminar)) {
                try {
                    idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                    MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(idmantenimiento));
                    if (mantenimiento != null) {
        //Mejorar metodo eliminar en el dao
                    this.trabajosDAO.eliminar(mantenimiento);
                    }
                } catch (MiExcepcion ex) {
                    JOptionPane.showMessageDialog(null, "Ingresa un ID de mantenimiento para poder eliminarlo");
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
    }

    private void limpiar() {
        this.vista.txtIDMantenimiento.setText("");
        this.vista.txtIDFactura.setText("");
        this.vista.txtSerial.setText("");
        this.vista.txtMarca.setText("");
        this.vista.txtColor.setText("");
        this.vista.txtGrupo.setText("");
        this.vista.txtEstado.setText("");
        this.vista.txtValorEstimado.setText("");
        this.vista.txtDescripcion.setText("");
        this.vista.txtValorMantenimiento.setText("");
    }

}


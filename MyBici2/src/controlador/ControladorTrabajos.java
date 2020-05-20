/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.dao.EmpleadosDao;
import modelo.dao.RolesDao;
import modelo.dao.UsuariosDao;
import modelo.dao.TrabajosDAO;
import modelo.dto.Bicicleta;
import modelo.dto.Cliente;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimienroBicicleta;
import vista.VistaPrincipal;
import vista.VistaTrabajos;

/**
 *
 * @author andre
 */
public class ControladorTrabajos implements ActionListener {

    VistaPrincipal vistaPrincipal;
    VistaTrabajos vista;
    RolesDao rolesDao;
    TrabajosDAO trabajosDAO;
            
    ControladorTrabajos(VistaPrincipal vista, VistaTrabajos vistaTrabajos, UsuariosDao usuariosDao, RolesDao rolesDao, TrabajosDAO trabajosDAO) {
        this.vista = vistaTrabajos;
        this.vistaPrincipal = vista;
        this.rolesDao = rolesDao;
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
        if(e.getSource().equals(this.vista.btnInsertar)){
            try {
                String idmantenimiento = MiExcepcion.capturaString(this.vista.txtIDMantenimiento);
                MantenimienroBicicleta mantenimiento = this.trabajosDAO.consultar(Integer.parseInt(this.vista.txtIDMantenimiento.getText()));
                if(mantenimiento == null){
                    mantenimiento.setId(Integer.parseInt(this.vista.txtIDMantenimiento.getText()));
                    FacturaVenta factura = new FacturaVenta();
                    factura.setId(Integer.parseInt(this.vista.txtIDFactura.getText()));
                    Cliente cliente = new Cliente();
                    cliente.setCedula(Integer.parseInt(this.vista.txtCedulaCliente.getText()));
                    factura.setCliente(cliente);
                    //factura.setFecha();
                    //Crear datos de empleado apartir de usuario
                    factura.setIva(0);
                    factura.setTotal(Integer.parseInt(this.vista.txtValorMantenimiento.getText()));
                    mantenimiento.setFactura(factura);
                    Bicicleta bicicleta = new Bicicleta();
                    bicicleta.setMarcoSerial(this.vista.txtSerial.getText());
                    bicicleta.setMarca(this.vista.txtMarca.getText());
                    bicicleta.setColor(this.vista.txtColor.getText());
                    bicicleta.setGrupoMecanico(this.vista.txtGrupo.getText());
                    bicicleta.setEstado(this.vista.txtEstado.getText());
                    mantenimiento.setBicicleta(bicicleta);
                    mantenimiento.setFechaEntrega(this.vista.FecEntrega.getDateFormatString());
                    mantenimiento.setDescripccion(this.vista.txtDescripcion.getText());
                    mantenimiento.setValorEstimado(Integer.parseInt(this.vista.txtValorEstimado.getText()));
                    //Enviar los 3 objetos o solo enviar el objeto de mantenimiento y de el sacar la información de los otros dos objetos
                    this.trabajosDAO.crear(bicicleta, mantenimiento, factura);
                }else{
                    JOptionPane.showMessageDialog(null, "El mantenimiento con ID" + this.vista.txtIDMantenimiento.getText() + " ya existe");
                }
            } catch (MiExcepcion ex) {
                Logger.getLogger(ControladorTrabajos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource().equals(this.vista.btnConsultar)){
            
        }
        if(e.getSource().equals(this.vista.btnModificar)){
            
        }
         if(e.getSource().equals(this.vista.btnEliminar)){
            
        }
         if(e.getSource().equals(this.vista.btnListar)){
            
         }       
        //--- Condiciones para funcionalidad
        if(e.getSource().equals(this.vista.btnRegresar)){
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
        this.vista.txtSerial.setText("");
        this.vista.txtMarca.setText("");
        this.vista.txtColor.setText("");
        this.vista.txtGrupo.setText("");
        this.vista.txtEstado.setText("");
        this.vista.txtValorEstimado.setText("");
        this.vista.txtDescripcion.setText("");
    }

}

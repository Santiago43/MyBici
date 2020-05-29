/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import modelo.dao.DireccionDao;
import modelo.dao.SedeDao;
import modelo.dao.UsuariosDao;
import modelo.dto.Calle;
import modelo.dto.Carrera;
import modelo.dto.Direccion;
import modelo.dto.Sede;
import vista.VistaSede;
import vista.VistaPrincipal;

/**
 * Controlador de Sedes
 *
 * @author F. PLAZA, Santiago Pérez
 * @version 1.0
 * @since 2020-05-15
 */
public class ControladorSede implements ActionListener {

    /**
     *
     */
    VistaSede vista;
    /**
     *
     */
    VistaPrincipal vistaAnterior;
    UsuariosDao usuariosDao;
    SedeDao sedeDao;
    DireccionDao direccionDao;

    /**
     *
     * @param vista
     * @param vistaAnterior
     * @param usuariosDao
     * @param sedeDao
     * @param direccionDao
     */
    public ControladorSede(VistaSede vista, VistaPrincipal vistaAnterior, UsuariosDao usuariosDao, SedeDao sedeDao, DireccionDao direccionDao) {
        this.vista = vista;
        this.vistaAnterior = vistaAnterior;
        this.usuariosDao = usuariosDao;
        this.sedeDao = sedeDao;
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
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
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource().equals(this.vista.btnInsertar)) {
                insertarSede();
            } else if (ae.getSource().equals(this.vista.btnConsultar)) {
                consultarSede();
            } else if (ae.getSource().equals(this.vista.btnModificar)) {
                modificarSede();
            } else if (ae.getSource().equals(this.vista.btnEliminar)) {
                eliminarSede();
            } else if (ae.getSource().equals(this.vista.btnRegresar)) {
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

    private void insertarSede() throws MiExcepcion {
        Sede sede = new Sede();
        sede.setNombreSede(MiExcepcion.capturaString(this.vista.txtNombreSede));
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
        sede.setDireccion(direccionConfirmada);
        if(this.sedeDao.crear(sede)){
            JOptionPane.showMessageDialog(null, "Sede creada");
        }else{
            throw new MiExcepcion("Error al añadir sede");
        }
    }

    private void consultarSede() throws MiExcepcion {
        String idSede = JOptionPane.showInputDialog("Digite el id de la sede");
        if(idSede.equals("")){
            throw new MiExcepcion("No puede dejar ese campo vacío");
        }
        Sede sede = this.sedeDao.consultar(idSede);
        if(sede==null){
            throw new MiExcepcion("Esa sede no existe");
        }
        Direccion direccion = sede.getDireccion();
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

    private void modificarSede() throws MiExcepcion {
        Sede sede = this.sedeDao.consultar(MiExcepcion.capturaString(this.vista.txtIDsede));
        if(sede==null){
            throw new MiExcepcion("Esa sede no existe");
        }
        sede.setNombreSede(MiExcepcion.capturaString(this.vista.txtNombreSede));
        Direccion direccion = sede.getDireccion();
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
        sede.setDireccion(direccion);
        if(this.sedeDao.actualizar(sede)){
            JOptionPane.showMessageDialog(null, "Sede "+sede.getNombreSede()+" actualizada");
        }else{
            throw new MiExcepcion("Error al editar sede");
        }
    }

    private void eliminarSede() throws MiExcepcion {
        
        String idSede = JOptionPane.showInputDialog("Digite el id de la sede");
        if(idSede.equals("")){
            JOptionPane.showMessageDialog(null,"No puede dejar este campo vacío");
        }
        Sede sede = this.sedeDao.consultar(idSede);
        if(sede==null){
            throw new MiExcepcion("Esa sede no existe");
        }
        else{
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar la sede "+sede.getNombreSede()+"?");
            if(opc==JOptionPane.YES_OPTION){
                if(this.sedeDao.eliminar(idSede)){
                    JOptionPane.showMessageDialog(null, "Sede eliminada");
                }else{
                    throw new MiExcepcion("Error al eliminar la sede");
                }
            }
        }
        
    }

}

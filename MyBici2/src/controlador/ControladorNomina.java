package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.dao.EmpleadosDao;
import modelo.dao.NominaDao;
import modelo.dto.Empleado;
import modelo.dto.Nomina;
import vista.VistaContabilidad;
import vista.VistaNomina;

/**
 * Clase controlador de nómina
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-26
 */
public class ControladorNomina implements ActionListener {

    public VistaContabilidad vistaAnterior;
    public VistaNomina vista;
    public NominaDao nominaDao;
    public DefaultTableModel modeloTablaNomina;

    public ControladorNomina(VistaContabilidad vistaAnterior, VistaNomina vista, NominaDao nominaDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.nominaDao = nominaDao;
        this.modeloTablaNomina = (DefaultTableModel) this.vista.tblNominas.getModel();
        this.vista.btnInsertar.addActionListener(this);
        this.vista.btnConsultar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
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
                crearNomina();
            } else if (e.getSource().equals(this.vista.btnConsultar)) {
                consultarNomina();
            } else if (e.getSource().equals(this.vista.btnModificar)) {
                actualizarNomina();
            } else if (e.getSource().equals(this.vista.btnEliminar)) {
                eliminarNomina();
            } else if (e.getSource().equals(this.vista.btnRegresar)) {
                salir();
            }
        } catch (MiExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }

    public void crearNomina() throws MiExcepcion {

        Empleado empleado = new EmpleadosDao().consultar(MiExcepcion.capturaString(this.vista.txtCCEmpleado));
        Nomina nomina = new Nomina();
        nomina.setCedula(empleado);
        nomina.setHorasExtra(MiExcepcion.capturaEntero(this.vista.txtHorasExtra));
        nomina.setFechaNomina(MiExcepcion.capturaString((JTextField) this.vista.FechaNomina.getDateEditor().getUiComponent()));
        boolean ausenciaBool = false;
        if (this.vista.RadioAuxSi.isSelected()) {
            ausenciaBool = true;
            this.vista.txtAuxTransporte.setText("102854");
        } else if (this.vista.RadioAuxNo.isSelected()) {
            ausenciaBool = false;
            this.vista.txtAuxTransporte.disable();
            this.vista.txtAuxTransporte.setText("0");

        }
        nomina.setAuxTransportebool(ausenciaBool);
        nomina.setAuxTransportedouble(MiExcepcion.capturaDouble(this.vista.txtAuxTransporte));
        nomina.setDescuento(MiExcepcion.capturaDouble(this.vista.txtDescuento));
        if (this.vista.RadioAusNo.isSelected()) {
            this.vista.txtDiasAusencia.disable();
            this.vista.txtAuxTransporte.setText("0");
        } else {
            nomina.setDiasAusencia(MiExcepcion.capturaEntero(this.vista.txtDiasAusencia));
        }

    }

    public void consultarNomina() throws MiExcepcion {
        String IDConsulta = JOptionPane.showInputDialog("Inserte el ID a consultar");

        Nomina nomina = this.nominaDao.consultar(IDConsulta);
        if (nomina == null) {
            throw new MiExcepcion("Esa nomina no Existe");
        } else {
            Empleado empleado = new EmpleadosDao().consultar(IDConsulta);
            this.vista.txtCCEmpleado.setText(empleado.getCedula());
            this.vista.txtHorasExtra.setText(String.valueOf(nomina.getHorasExtra()));
            ((JTextField) this.vista.FechaNomina.getDateEditor().getUiComponent()).setText(nomina.getFechaNomina());
            if (nomina.isAuxTransportebool()) {
                this.vista.RadioAuxSi.setSelected(true);
                this.vista.RadioAuxNo.setSelected(false);
            } else {
                this.vista.RadioAuxNo.setSelected(true);
                this.vista.RadioAuxSi.setSelected(false);
            }
            this.vista.txtAuxTransporte.setText(String.valueOf(nomina.getAuxTransportedouble()));
            this.vista.txtDescuento.setText(String.valueOf(nomina.getDescuento()));
            this.vista.txtDiasAusencia.setText(String.valueOf(nomina.getDiasAusencia()));
        }
    }

    public void actualizarNomina() throws MiExcepcion {

        String IDConsulta = JOptionPane.showInputDialog("Inserte el ID a Modificar");

        Nomina nomina = this.nominaDao.consultar(IDConsulta);
        if (nomina == null) {
            throw new MiExcepcion("Esa nomina no Existe");
        } else {
            Empleado empleado = new EmpleadosDao().consultar(MiExcepcion.capturaString(this.vista.txtCCEmpleado));

            nomina.setCedula(empleado);
            nomina.setHorasExtra(MiExcepcion.capturaEntero(this.vista.txtHorasExtra));
            nomina.setFechaNomina(MiExcepcion.capturaString((JTextField) this.vista.FechaNomina.getDateEditor().getUiComponent()));
            boolean ausenciaBool = false;
            if (this.vista.RadioAuxSi.isSelected()) {
                ausenciaBool = true;
                this.vista.txtAuxTransporte.setText("102854");
            } else if (this.vista.RadioAuxNo.isSelected()) {
                ausenciaBool = false;
                this.vista.txtAuxTransporte.disable();
                this.vista.txtAuxTransporte.setText("0");

            }
            nomina.setAuxTransportebool(ausenciaBool);
            nomina.setAuxTransportedouble(MiExcepcion.capturaDouble(this.vista.txtAuxTransporte));
            nomina.setDescuento(MiExcepcion.capturaDouble(this.vista.txtDescuento));
            if (this.vista.RadioAusNo.isSelected()) {
                this.vista.txtDiasAusencia.disable();
                this.vista.txtAuxTransporte.setText("0");
            } else {
                nomina.setDiasAusencia(MiExcepcion.capturaEntero(this.vista.txtDiasAusencia));
            }
        }

    }

    public void eliminarNomina() throws MiExcepcion {
        String IDConsulta = JOptionPane.showInputDialog("Inserte el ID a Eliminar");

        Nomina nomina = this.nominaDao.consultar(IDConsulta);

        if (nomina == null) {
            throw new MiExcepcion("Esa Nomina no existe");
        }
        else{
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la nomina  del Empleado con CC" + nomina.getCedula()+"?");
            if (opc == JOptionPane.YES_OPTION) {
                if (this.nominaDao.eliminar(IDConsulta)) {
                    JOptionPane.showMessageDialog(null, "Nomina eliminada");
                } else {
                    throw new MiExcepcion("Error al eliminar la Nomina");
                }
            }
        }
    }
    
    private void listarClientes() {
       LinkedList<Nomina> nominas = this.nominaDao.listar();
        for (Nomina nomina:nominas) {
            
             
            
            
            Object fila[] ={nomina.getIdNomina(),nomina.getCedula(),nomina.getHorasExtra(),nomina.getFechaNomina(),nomina.getDescuento(),nomina.getDiasAusencia()};
            this.modeloTablaNomina.addRow(fila);
        }
    }

    public void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

}

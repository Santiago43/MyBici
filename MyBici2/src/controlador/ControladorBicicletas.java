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
import javax.swing.table.DefaultTableModel;
import modelo.dao.BicicletaDao;
import modelo.dto.Bicicleta;
import vista.VistaBicicletas;
import vista.VistaPrincipal;

/**
 *
 * @author Familia Plaza
 */
public class ControladorBicicletas implements ActionListener {

    public VistaPrincipal vistaAnterior;
    public VistaBicicletas vista;
    public BicicletaDao bicicletaDao;
    public DefaultTableModel modeloTablaBicicleta;

    public ControladorBicicletas(VistaPrincipal vistaAnterior, VistaBicicletas vista, BicicletaDao bicicletaDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.bicicletaDao = bicicletaDao;
        this.modeloTablaBicicleta = (DefaultTableModel) this.vista.tblBicicletas.getModel();
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
        listarBicicletas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnInsertar)) {
                crearBicicleta();
            } else if (e.getSource().equals(this.vista.btnConsultar)) {
                consultarBicicleta();
            } else if (e.getSource().equals(this.vista.btnModificar)) {
                actualizarBicicleta();
            } else if (e.getSource().equals(this.vista.btnEliminar)) {
                eliminarBicicleta();
            } else if (e.getSource().equals(this.vista.btnListar)) {
                listarBicicletas();
            } else if (e.getSource().equals(this.vista.btnRegresar)) {
                salir();
            }
        } catch (MiExcepcion ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }

    }

    public void crearBicicleta() throws MiExcepcion {
        Bicicleta bicicleta = new Bicicleta();

        bicicleta.setMarcoSerial(MiExcepcion.capturaString(this.vista.txtMarcoSerial));
        bicicleta.setGrupoMecanico(MiExcepcion.capturaString(this.vista.txtGrupo));
        bicicleta.setColor(MiExcepcion.capturaString(this.vista.txtColor));
        bicicleta.setMarca(MiExcepcion.capturaString(this.vista.txtMarca));
        bicicleta.setEstado(MiExcepcion.capturaString(this.vista.txtEstado));
        bicicleta.setValorEstimado(MiExcepcion.capturaDouble(this.vista.txtvalorEstimado));

        if (this.bicicletaDao.consultar(bicicleta.getMarcoSerial()) == null) {
            throw new MiExcepcion("Esta Bicicleta ya existe");
        } else {
            if (this.bicicletaDao.crear(bicicleta)) {
                JOptionPane.showMessageDialog(null, "Bicicleta creada satisfactoriamente");
                Limpiar();
            } else {
                throw new MiExcepcion("error al crear la Bicicleta");
            }
        }
    }

    public void consultarBicicleta() throws MiExcepcion {
        Bicicleta bicicleta = this.bicicletaDao.consultar(MiExcepcion.capturaString(this.vista.txtMarcoSerial));
        if (bicicleta == null) {
            throw new MiExcepcion("ese bicicleta no existe");
        } else {
            this.vista.txtGrupo.setText(bicicleta.getGrupoMecanico());
            this.vista.txtColor.setText(bicicleta.getColor());
            this.vista.txtMarca.setText(bicicleta.getMarca());
            this.vista.txtEstado.setText(bicicleta.getEstado());
            this.vista.txtvalorEstimado.setText(String.valueOf(bicicleta.getValorEstimado()));
        }
    }

    public void actualizarBicicleta() throws MiExcepcion {
        String marco = MiExcepcion.capturaString(this.vista.txtMarcoSerial);
        Bicicleta bicicleta = this.bicicletaDao.consultar(marco);
        if (bicicleta == null) {
            throw new MiExcepcion("ese bicicleta no existe");
        }
        bicicleta.setGrupoMecanico(MiExcepcion.capturaString(this.vista.txtGrupo));
        bicicleta.setColor(MiExcepcion.capturaString(this.vista.txtColor));
        bicicleta.setMarca(MiExcepcion.capturaString(this.vista.txtMarca));
        bicicleta.setEstado(MiExcepcion.capturaString(this.vista.txtEstado));
        bicicleta.setValorEstimado(MiExcepcion.capturaDouble(this.vista.txtvalorEstimado));

        if (this.bicicletaDao.actualizar(bicicleta)) {
            JOptionPane.showMessageDialog(null, "bicicleta Actualizada Satisfactoriamente");
            Limpiar();
        } else {
            throw new MiExcepcion("Error al Actualizar La bicicleta");
        }
    }

    public void eliminarBicicleta() throws MiExcepcion {
        String marco = MiExcepcion.capturaString(this.vista.txtMarcoSerial);
        Bicicleta bicicleta = this.bicicletaDao.consultar(marco);

        if (bicicleta == null) {
            throw new MiExcepcion("Esa bicicleta no existe");
        } else {
            int opc = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea eliminar la Bicicleta con el serial" + bicicleta.getMarcoSerial() + "?");
            if (opc == JOptionPane.YES_OPTION) {
                if (this.bicicletaDao.eliminar(marco)) {
                    JOptionPane.showMessageDialog(null, "Bicicleta Eliminada");
                } else {
                    throw new MiExcepcion("Error al eliminar la bicicleta");
                }
            }
        }
    }

    private void listarBicicletas() {
        limpiarTabla();
        LinkedList<Bicicleta> bicicletas = this.bicicletaDao.listar();
        for (Bicicleta bicicleta : bicicletas) {

            Object fila[] = {bicicleta.getMarcoSerial(), bicicleta.getGrupoMecanico(), bicicleta.getColor(), bicicleta.getMarca(), bicicleta.getEstado(), bicicleta.getValorEstimado()};
            this.modeloTablaBicicleta.addRow(fila);
        }
    }

    public void salir() {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    public void Limpiar() {
        this.vista.txtMarcoSerial.setText("");
        this.vista.txtMarca.setText("");
        this.vista.txtGrupo.setText("");
        this.vista.txtEstado.setText("");
        this.vista.txtColor.setText("");
        this.vista.txtvalorEstimado.setText("");
    }

    public void limpiarTabla() {
        while (this.modeloTablaBicicleta.getRowCount() > 0) {
            this.modeloTablaBicicleta.removeRow(0);
        }
    }}

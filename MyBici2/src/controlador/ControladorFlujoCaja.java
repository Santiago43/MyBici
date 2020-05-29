package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import modelo.dao.SedeDao;
import vista.VistaContabilidad;
import vista.VistaFC;

/**
 * Controlador que se encarga de mostrar el estado de resultados de la empresa
 * @author Santiago Pérez 
 * @version 1.0
 * @since 2020-05-24
 */
public class ControladorFlujoCaja implements ActionListener{

    VistaContabilidad vistaAnterior;
    VistaFC vista;
    SedeDao sedeDao;

    /**
     *
     * @param vistaAnterior
     * @param vista
     * @param sedeDao
     */
    public ControladorFlujoCaja(VistaContabilidad vistaAnterior, VistaFC vista, SedeDao sedeDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.sedeDao = sedeDao;
        this.vista.btnRegresar.addActionListener(this);
        this.vista.btnCalcular.addActionListener(this);
        this.vista.addWindowListener(new WindowAdapter(){
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
        if(e.getSource().equals(this.vista.btnCalcular)){
            int mes = this.vista.comboMes.getSelectedIndex()+1;
            String textoMes="";
            if(mes<10){
                textoMes="0"+mes;
            }
            else{
                textoMes+=mes;
            }
            
        }else if(e.getSource().equals(this.vista.btnRegresar)){
            salir();
        }
    }

    private void salir() {
        
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }
    
}

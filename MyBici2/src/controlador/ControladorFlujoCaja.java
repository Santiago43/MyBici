package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import modelo.dao.FacturaDao;
import modelo.dao.NominaDao;
import modelo.dao.SedeDao;
import modelo.dto.FacturaVenta;
import modelo.dto.Nomina;
import modelo.dto.Sede;
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
    FacturaDao facturaDao;

    /**
     *
     * @param vistaAnterior
     * @param vista
     * @param sedeDao
     * @param facturaDao
     */
    public ControladorFlujoCaja(VistaContabilidad vistaAnterior, VistaFC vista, SedeDao sedeDao,FacturaDao facturaDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.sedeDao = sedeDao;
        this.vista.btnRegresar.addActionListener(this);
        this.vista.btnCalcular.addActionListener(this);
        this.facturaDao=facturaDao;
        this.vista.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }
        });
        listarSedes();
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
            String año = this.vista.comboAño.getSelectedItem().toString();
            int diaFinal =obtenerUltimoDíaDeMes(Integer.parseInt(año),mes);
            double ingreso = this.calcularVentas(this.facturaDao.listarPorFecha(año+"-"+textoMes+"-"+"01", año+"-"+textoMes+"-"+diaFinal, this.sedeDao.consultarPorNombre(this.vista.comboSedes.getSelectedItem().toString())));
            double gasto = this.calcularSalario();
            this.vista.txtIngresos.setText(String.valueOf(ingreso));
            this.vista.txtCostos.setText(String.valueOf(gasto));
            this.vista.txtUAI.setText(String.valueOf(ingreso-gasto));
        }else if(e.getSource().equals(this.vista.btnRegresar)){
            salir();
        }
    }

    private void salir() {
        
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }
    private int obtenerUltimoDíaDeMes(int año, int mes) {
        mes++;
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                if(año%4==0){
                    return 29;
                }
                else{
                    return 28;
                }
        }
    }
       private double calcularVentas(LinkedList<FacturaVenta> facturas){
        double ingresoTotal=0;
        for(FacturaVenta factura: facturas){
            ingresoTotal+=factura.getTotal();
        }
        return ingresoTotal;
    }
    
    private double calcularSalario(){
        double salarios=0;
        NominaDao nominaDao = new NominaDao();
        LinkedList<Nomina>nominas=nominaDao.listar();
        for(Nomina nomina: nominas){
            salarios+=nomina.getCedula().getSalario()*(30-nomina.getDiasAusencia())/30;
        }
        return salarios;
    }

    private void listarSedes() {
        LinkedList<Sede> sedes = this.sedeDao.listar();
        for(Sede sede: sedes){
            this.vista.comboSedes.addItem(sede.getNombreSede());
        }
    }
}

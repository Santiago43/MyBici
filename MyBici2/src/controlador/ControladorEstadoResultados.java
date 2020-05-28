package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import modelo.dao.FacturaDao;
import modelo.dao.SedeDao;
import modelo.dto.Sede;
import modelo.dto.Taller;
import vista.VistaContabilidad;
import vista.VistaER;

/**
 * Controlador que se encarga de mostrar el estado de resultados de la empresa
 *
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-24
 */
public class ControladorEstadoResultados implements ActionListener {

    private final VistaContabilidad vistaAnterior;
    private final VistaER vista;
    private final SedeDao sedeDao;
    private final FacturaDao facturaDao;

    public ControladorEstadoResultados(VistaContabilidad vistaAnterior, VistaER vista, SedeDao sedeDao, FacturaDao facturaDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.sedeDao = sedeDao;
        this.facturaDao = facturaDao;
        this.vistaAnterior.setVisible(false);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.addWindowFocusListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                salir(); //To change body of generated methods, choose Tools | Templates.
            }
        });
        listarSedes();
        this.vista.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource().equals(this.vista.btnConsultar)) {
            int mes= this.vista.comboMes.getSelectedIndex()+1;
            String textoMes ="";
            if(mes<10){
                textoMes+="0"+mes;
            }
            else{
                textoMes+=mes;
            }
            
            String año =this.vista.comboAño.getSelectedItem().toString();
            String fechaInicio=  año+"-"+textoMes+"-"+"01";
            String fechaFinal= año+"-"+textoMes+"-"+this.obtenerUltimoDíaDeMes(Integer.parseInt(año), mes);
            Sede sede = this.sedeDao.consultarPorNombre(this.vista.comboSedes.getSelectedItem().toString());
            double totalVentas = calcularVentas(sede);
            double totalMantenimientos = calcularMantenimientos(sede);
            this.facturaDao.listarPorFecha(fechaInicio, fechaFinal, sede);
            String estadoDeResultados="								BiciBikes S.A.S\n"
                    + "							  Estado de Resultados\n"
                    + "					Del 1 de "+this.vista.comboMes.getSelectedItem().toString()+" de "+año+" al "+obtenerUltimoDíaDeMes(Integer.parseInt(año),this.vista.comboMes.getSelectedIndex())+" de "+this.vista.comboMes.getSelectedItem().toString()+" de "+this.vista.comboAño.getSelectedItem().toString()+"\n"
                    + "\n"
                    + "\n"
                    + "Ingresos operacionales:\n"
                    + "	Total ventas en la sede:				$ \n"
                    + "	Mantenimientos: 					$ 9000000\n"
                    + "Total Ingresos operacionales								$ 9000000\n"
                    + "\n"
                    + "Gastos operacionales:\n"
                    + "	Salarios								$ 9000000\n"
                    + "	Mantenimientos							$ 9000000\n"
                    + "	Depreciación							$ 900000\n"
                    + "Total Gastos operacionales 									($ 9000000)\n"
                    + "Utilidad operativa:											 $ 300000\n"
                    + "Impuestos													($ 3000000)\n"
                    + "Utilidad neta:													$900000";
            this.vista.txtArea.setText(estadoDeResultados);
        } else if (e.getSource().equals(this.vista.btnRegresar)) {
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

    private void listarSedes() {
        LinkedList<Sede> sedes =this.sedeDao.listar();
        for(Sede sede: sedes){
            this.vista.comboSedes.addItem(sede.getNombreSede());
        }
    }

    private double calcularVentas(Sede sede) {
        LinkedList<Taller> talleres=sede.getTalleres();
        double totalVentas = 0;
        for(Taller taller: talleres){
            totalVentas+= taller.getTotalVentas();
        }
        return totalVentas;
    }

    private double calcularMantenimientos(Sede sede) {
       
       return 0;
    }

}

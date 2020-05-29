package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import modelo.dao.FacturaDao;
import modelo.dao.NominaDao;
import modelo.dao.SedeDao;
import modelo.dto.EquipoOficina;
import modelo.dto.FacturaVenta;
import modelo.dto.MantenimientoTaller;
import modelo.dto.Nomina;
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
        this.vista.btnConsultar.addActionListener(this);
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
            
            LinkedList<FacturaVenta>facturas=this.facturaDao.listarPorFecha(fechaInicio, fechaFinal, sede);
            double impuestos = ((this.calcularVentas(facturas)+this.calcularVentas(sede))-((this.calcularSalario()+this.calcularMantenimientos(sede))+this.calcularDepreciacion(sede)))*0.33;
            if(impuestos<0){
                impuestos=0;
            }
            String estadoDeResultados="			BiciBikes S.A.S\n"
                    + "             Estado de Resultados\n"
                    + "		Del 1 de "+this.vista.comboMes.getSelectedItem().toString()+" de "+año+" al "+obtenerUltimoDíaDeMes(Integer.parseInt(año),this.vista.comboMes.getSelectedIndex())+" de "+this.vista.comboMes.getSelectedItem().toString()+" de "+this.vista.comboAño.getSelectedItem().toString()+"\n"
                    + "\n"
                    + "\n"
                    + "Ingresos operacionales:\n"
                    + "	Total ventas en la sede:	$ "+this.calcularVentas(facturas)+"\n"
                    + "	Mantenimientos: 	$ "+this.calcularVentas(sede)+"\n"
                    + "Total Ingresos operacionales                                                                                          $ "+(this.calcularVentas(facturas)+this.calcularVentas(sede))+"\n"
                    + "\n"
                    + "Gastos operacionales:\n"
                    + "	Salarios		$ "+this.calcularSalario()+"\n"
                    + "	Mantenimientos	$ "+this.calcularMantenimientos(sede)+"\n"
                    + "	Depreciación		$ "+this.calcularDepreciacion(sede)+"\n"
                    + "Total Gastos operacionales                                                                                             ($ "+((this.calcularSalario()+this.calcularMantenimientos(sede))+this.calcularDepreciacion(sede))+")\n"
                    + "Utilidad operativa:				$ "+(((this.calcularVentas(facturas)+this.calcularVentas(sede))-((this.calcularSalario()+this.calcularMantenimientos(sede))+this.calcularDepreciacion(sede))))+"\n"
                    + "Impuestos					($ "+impuestos+")\n"
                    + "Utilidad neta:					$"+(((this.calcularVentas(facturas)+this.calcularVentas(sede))-((this.calcularSalario()+this.calcularMantenimientos(sede))+this.calcularDepreciacion(sede))-impuestos))+" ";
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
       double totalMantenimientos=0;
       LinkedList<Taller> talleres = sede.getTalleres();
        for (Taller taller: talleres) {
            LinkedList<MantenimientoTaller>mantenimientos=taller.getMantenimientos();
            for (MantenimientoTaller mantenimiento: mantenimientos) {
                totalMantenimientos+=mantenimiento.getCosto();
            }
        }
       return totalMantenimientos;
    }

    private double calcularDepreciacion(Sede sede){
        double totalDepreciacion=0;
        LinkedList<EquipoOficina> equipos=sede.getEquipoOficina();
        for(EquipoOficina equipo: equipos){
            totalDepreciacion+=equipo.getDepreciacion();
        }
        return totalDepreciacion;
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
}

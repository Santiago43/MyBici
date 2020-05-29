package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import modelo.dao.FacturaDao;
import modelo.dao.NominaDao;
import modelo.dao.SedeDao;
import vista.VistaContabilidad;
import vista.VistaER;
import vista.VistaNomina;
import vista.VistaPrincipal;

/**
 * Clase controlador de contabilidad. Se tiene acceso a los estados financieros de la empresa
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-24
 */
public class ControladorContabilidad implements ActionListener,MouseListener{

    private VistaPrincipal vistaAnterior;
    private VistaContabilidad vista;
    private SedeDao sedeDao;
    private FacturaDao facturaDao;
    private NominaDao nominaDao;

    public ControladorContabilidad(VistaPrincipal vistaAnterior, VistaContabilidad vista, SedeDao sedeDao,FacturaDao facturaDao, NominaDao nominaDao) {
        this.vistaAnterior = vistaAnterior;
        this.vista = vista;
        this.sedeDao = sedeDao;
        this.facturaDao= facturaDao;
        this.nominaDao = nominaDao;
        this.vistaAnterior.setVisible(false);
        this.vista.menuER.addMouseListener(this);
        this.vista.menuFC.addMouseListener(this);
        this.vista.menuFacturacion.addMouseListener(this);
        this.vista.menuNomina.addMouseListener(this);
        this.vista.btnRegresar.addActionListener(this);
        this.vista.setVisible(true);
    }
    
            
    @Override
    public void actionPerformed(ActionEvent e) {
        this.vista.dispose();
        this.vistaAnterior.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       if(e.getSource().equals(this.vista.menuFacturacion)){
            
        }else if(e.getSource().equals(this.vista.menuER)){
            ControladorEstadoResultados controladorEstadoResultados;
            controladorEstadoResultados=new ControladorEstadoResultados(this.vista,new VistaER(),this.sedeDao,this.facturaDao);
        }
        else if(e.getSource().equals(this.vista.menuNomina)){
            ControladorNomina ControladorNomina;
            ControladorNomina = new ControladorNomina(this.vista, new VistaNomina(), this.nominaDao);
        }
        else if(e.getSource().equals(this.vista.menuFC)){
            
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}

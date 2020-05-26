package modelo.dao;

import modelo.dto.EquipoOficina;
import modelo.dto.Inventario;
import modelo.dto.Mercancia;
import modelo.dto.Sede;
import modelo.dto.Taller;
import modelo.dto.Telefono;

/**
 * Interfaz de sede Dao
 * @author Santiago Pérez
 */
public interface ISedeDao extends IDao <Sede> {

    /**
     *
     * @param idSede
     * @param telefono
     * @return
     */
    boolean agregarTelefono(String idSede, Telefono telefono);

    /**
     *
     * @param inventario
     * @param mercancia
     * @return
     */
    boolean agregarAInventario(Inventario inventario,Mercancia mercancia );

    /**
     *
     * @param sede
     * @param equipoOficina
     * @return
     */
    boolean agregarEquipoOficina(Sede sede,EquipoOficina equipoOficina);
    
    /**
     *
     * @param taller
     * @return
     */
    boolean agregarTaller(Taller taller);
    
    Sede consultarPorNombre(String nombreSede);
}

package modelo.dao;

import java.util.LinkedList;
import modelo.dto.FacturaVenta;
import modelo.dto.Sede;

/**
 * Interface de facturas
 * @author Andrés Camilo López, Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public interface IFacturaDao extends IDao <FacturaVenta>{

    /**
     *
     * @param fechaInicio
     * @param fechaFinal
     * @param sede
     * @return
     */
    LinkedList<FacturaVenta> listarPorFecha(String fechaInicio,String fechaFinal, Sede sede);
}

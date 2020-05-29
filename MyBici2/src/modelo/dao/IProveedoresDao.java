package modelo.dao;

import java.util.LinkedList;
import modelo.dto.Proveedor;
import modelo.dto.Telefono;

/**
 * Interface del objeto de acceso a datos de los proveedores
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-23
 */
public interface IProveedoresDao extends IDao<Proveedor>{
    public boolean agregarTelefono(Proveedor proveedor, Telefono telefono);
    public boolean removerTelefono(Telefono telefono);
    public LinkedList<Telefono> listarTelefonos(Proveedor proveedor);
    public LinkedList<Object[]> listarVentaMayor();
    public LinkedList<Object[]> listarVentaMenor();
    public LinkedList<Object[]> listarFrecuentes();
}

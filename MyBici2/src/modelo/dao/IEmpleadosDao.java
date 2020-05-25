package modelo.dao;

import modelo.dto.Empleado;
import modelo.dto.Telefono;

/**
 *
 * @author Santiago Pérez
 */
public interface IEmpleadosDao extends IDao <Empleado>{
    boolean agregarTelefono(Empleado empleado, Telefono telefono);
}

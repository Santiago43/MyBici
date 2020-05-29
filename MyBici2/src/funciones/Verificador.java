package funciones;

import java.util.LinkedList;
import modelo.dto.Permiso;
import modelo.dto.Usuario;

/**
 * Clase que valida permisos
 * @author Santiago Pérez
 * @version 1.0
 * @since 2020-05-25
 */
public class Verificador {

    /**
     * Método que permite verificar el permiso de un usuario
     * @param usuario que es el usuario en sesión
     * @param permiso que es el permiso que se quiere verificar
     * @return verdadero si tiene el permiso y falso si no
     */
    public static boolean tienePermiso(Usuario usuario,String permiso){
        LinkedList<Permiso> permisosUsuario = usuario.getPermisos();
        for (int i = 0; i < permisosUsuario.size(); i++) {
            if(permisosUsuario.get(i).getNombrePermiso().equals(permiso)||permisosUsuario.get(i).getNombrePermiso().equals("*")){
                return true;
            }
        }
        LinkedList<Permiso> permisosRol = usuario.getRol().getPermisos();
        for (int i = 0; i < permisosRol.size(); i++) {
            if(permisosRol.get(i).getNombrePermiso().equals(permiso)||permisosRol.get(i).getNombrePermiso().equals("*")){
                return true;
            }
        }
        return false;
    }
}

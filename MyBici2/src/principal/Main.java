package principal;

import conexion.Conexion;
import controlador.ControladorLogin;
import java.sql.Connection;
import modelo.dao.UsuariosDao;
import vista.VistaLogin;

/**
 * Clase principal
 * @author Andrés Camilo López, Carlos Antonio Plaza, Santiago Pérez
 * @version 1.0
 * @since 2020-05-09
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Se inicia la conexión con la base de datos antes de iniciar el programa
        Connection conn = Conexion.conectado();
        ControladorLogin controlador = new ControladorLogin(new VistaLogin(),new UsuariosDao());
    }
    
}

package principal;

import controlador.ControladorLogin;
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
        ControladorLogin controlador = new ControladorLogin(new VistaLogin(),new UsuariosDao());
    }
    
}

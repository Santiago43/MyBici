package principal;

import controlador.ControladorLogin;
import modelo.dao.UsuariosDao;
import vista.VistaLogin;

/**
 *
 * @author Santiago Pérez
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ControladorLogin controlador = new ControladorLogin(new VistaLogin(),new UsuariosDao());
    }
    
}

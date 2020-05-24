package controlador;

import excepcion.MiExcepcion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.dao.UsuariosDao;
import modelo.dto.Usuario;
import vista.VistaLogin;
import vista.VistaPrincipal;

/**
 * Clase controlador
 *
 * @author Santiago Pérez
 * @version 1
 * @since 2020-05-11
 */
public class ControladorLogin implements ActionListener {

    VistaLogin vista;
    UsuariosDao usuariosDao;

    public ControladorLogin(VistaLogin vista, UsuariosDao usuariosDao) {
        this.vista = vista;
        this.usuariosDao = usuariosDao;
        this.vista.btnIngresar.addActionListener(this);
        this.vista.setVisible(true);
        this.vista.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource().equals(this.vista.btnIngresar)) {
                String user = MiExcepcion.capturaString(this.vista.txtUsuario);
                String contraseña = MiExcepcion.capturaString(this.vista.txtContraseña);
                Usuario usuario = this.usuariosDao.consultar(user);
                if(usuario==null){
                    throw new MiExcepcion("Ese usuario no existe");
                }else{
                    if(!usuario.getContraseña().equals(contraseña)){
                        throw new MiExcepcion("Contraseña inválida");
                    }
                    else{
                        ControladorPrincipal cPrincipal = new ControladorPrincipal(new VistaPrincipal(),this.vista,this.usuariosDao, usuario);
                    }
                }
            }
        }catch(MiExcepcion ex){
            JOptionPane.showMessageDialog(null, "error: "+ex.getMessage());
        }

    }

}

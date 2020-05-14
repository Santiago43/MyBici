package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    String bd = "mybici";
    private final String url = "jdbc:mysql://localhost:3306/" + bd + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static Connection con = null;

    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, "root", "12345");
            if (con != null) {
                System.out.println("Conexion a base de datos 'Compras' listo");
            }
        } catch (SQLException e) {
            System.out.println(e.toString() + " Error de conexión 1");
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString() + " Error de conexión 2");
        }
    }

    public static Connection conectado() {
        if (con == null) {
            Conexion conexion = new Conexion();
        }
        return con;
    }

    public void desconectar() {
        con = null;
        System.out.println("conexion terminada");
    }
}

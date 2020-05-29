package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    String bd = "mybici";
    private final String url = "jdbc:mysql://25.143.152.254:3306/" + bd + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    static Connection con = null;

    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, "andres", "");

            if (con != null) {
                System.out.println("Conexion a base de datos exitosa");
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

    public static void desconectar() {
        con = null;
        System.out.println("conexion terminada");
    }
}

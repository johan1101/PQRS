package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * Conexion
 * @author Johan- María
 */
public class Conexion {
    /**
     * Metodo para establecer la conexion con la base de datos
     * @return conn
     */
    public Connection establecerConexion() {
        String url = "jdbc:mysql://localhost:3306/pqrs?serverTimeZone=utc";
        String user = "root";
        String password = "admin";
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Conexion exitosa");
            }
        } catch (Exception e) {
            System.out.println("Error de conexion " + e.getMessage());
        }
        return conn;
    }
}

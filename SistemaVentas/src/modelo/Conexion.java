package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "proyecto_adb"; 
    private final String PASS = "oracle21c";
    
    private Connection con;

    public Connection conectar() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("¡Éxito! Conectado a Oracle.");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error fatal de conexión: " + e.getMessage());
        }
        return con;
    }
}
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    private final String URL = "(description= (retry_count=20)(retry_delay=3)(address=(protocol=tcps)(port=1521)(host=adb.mx-queretaro-1.oraclecloud.com))(connect_data=(service_name=gab58fcefb5ed06_sistemaventasdb_medium.adb.oraclecloud.com))(security=(ssl_server_dn_match=yes)))";
    private final String USER = "ADMIN"; 
    private final String PASS = "Sebastianpalomino123$";
    
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

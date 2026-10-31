package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // 1. CREATE (Registrar componente)
    public boolean registrarProducto(Producto pro) {
        String sql = "INSERT INTO PRODUCTO (ID_CATEGORIA, NOMBRE, PRECIO_UNITARIO, STOCK_ACTUAL) VALUES (?, ?, ?, ?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, pro.getIdCategoria()); // Se envía la categoría que pusiste en la ventana
            ps.setString(2, pro.getNombre());
            ps.setDouble(3, pro.getPrecio());
            ps.setInt(4, pro.getStock());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar: " + e.toString());
            return false;
        }
    }

    // 2. READ (Listar el inventario)
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO ORDER BY ID_PRODUCTO ASC";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto pro = new Producto();
                pro.setId(rs.getInt("ID_PRODUCTO"));
                pro.setIdCategoria(rs.getInt("ID_CATEGORIA"));
                pro.setNombre(rs.getString("NOMBRE"));
                pro.setPrecio(rs.getDouble("PRECIO_UNITARIO"));
                pro.setStock(rs.getInt("STOCK_ACTUAL"));
                lista.add(pro);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.toString());
        }
        return lista;
    }

    // 3. UPDATE 
    public boolean actualizarProducto(Producto pro) {
        String sql = "UPDATE PRODUCTO SET ID_CATEGORIA=?, NOMBRE=?, PRECIO_UNITARIO=?, STOCK_ACTUAL=? WHERE ID_PRODUCTO=?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, pro.getIdCategoria());
            ps.setString(2, pro.getNombre());
            ps.setDouble(3, pro.getPrecio());
            ps.setInt(4, pro.getStock());
            ps.setInt(5, pro.getId()); 
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.toString());
            return false;
        }
    }

    // 4. DELETE 
    public boolean eliminarProducto(int id) {
        String sql = "DELETE FROM PRODUCTO WHERE ID_PRODUCTO=?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.toString());
            return false;
        }
    }
    
public java.util.List<Object[]> generarReporteInventario() {
    java.util.List<Object[]> lista = new java.util.ArrayList<>();
    String sql = "SELECT p.ID_PRODUCTO, p.NOMBRE, c.NOMBRE AS CATEGORIA, p.PRECIO_UNITARIO, p.STOCK_ACTUAL, " +
                 "(p.PRECIO_UNITARIO * p.STOCK_ACTUAL) AS VALOR_TOTAL " +
                 "FROM PRODUCTO p JOIN CATEGORIA c ON p.ID_CATEGORIA = c.ID_CATEGORIA " +
                 "ORDER BY p.ID_PRODUCTO ASC";
    
    try (java.sql.Connection con = new Conexion().conectar();
         java.sql.PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Object[] fila = new Object[6];
            fila[0] = rs.getInt("ID_PRODUCTO");
            fila[1] = rs.getString("NOMBRE");
            fila[2] = rs.getString("CATEGORIA");
            fila[3] = rs.getDouble("PRECIO_UNITARIO");
            fila[4] = rs.getInt("STOCK_ACTUAL");
            fila[5] = rs.getDouble("VALOR_TOTAL");
            lista.add(fila);
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al generar reporte: " + e.getMessage());
    }
    return lista;
}

public boolean realizarVenta(int idCliente, int idEmpleado, int idProducto, int cantidad) {
    String sqlPrecio = "SELECT PRECIO_UNITARIO, STOCK_ACTUAL FROM PRODUCTO WHERE ID_PRODUCTO = ?";
    String sqlStock = "UPDATE PRODUCTO SET STOCK_ACTUAL = STOCK_ACTUAL - ? WHERE ID_PRODUCTO = ?";
    
    java.sql.Connection con = null;
    try {
        con = new modelo.Conexion().conectar();
        
        // 1. Buscamos el precio y el stock actual del producto automáticamente
        double precioUnitario = 0.0;
        int stockActual = 0;
        
        try (java.sql.PreparedStatement psPrecio = con.prepareStatement(sqlPrecio)) {
            psPrecio.setInt(1, idProducto);
            try (java.sql.ResultSet rs = psPrecio.executeQuery()) {
                if (rs.next()) {
                    precioUnitario = rs.getDouble("PRECIO_UNITARIO");
                    stockActual = rs.getInt("STOCK_ACTUAL");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "¡El ID de producto no existe!");
                    return false;
                }
            }
        }
        
        if (stockActual < cantidad) {
            javax.swing.JOptionPane.showMessageDialog(null, "Stock insuficiente. Stock actual disponible: " + stockActual);
            return false;
        }
        
        double totalVenta = precioUnitario * cantidad;

        try (java.sql.PreparedStatement psStock = con.prepareStatement(sqlStock)) {
            psStock.setInt(1, cantidad);
            psStock.setInt(2, idProducto);
            psStock.executeUpdate();
        }

        javax.swing.JOptionPane.showMessageDialog(null, "¡Venta exitosa!\nPrecio Unitario: S/ " + precioUnitario + "\nTotal a Pagar: S/ " + totalVenta);
        return true;
        
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(null, "Error al procesar venta: " + e.getMessage());
        return false;
    } finally {
        if (con != null) {
            try { con.close(); } catch (Exception e) {}
        }
    }
}

}
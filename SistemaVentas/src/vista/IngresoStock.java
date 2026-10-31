/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

/**
 *
 * @author richa
 */
public class IngresoStock extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(IngresoStock.class.getName());

    // --- PEGAR EN EL CONSTRUCTOR IngresoStock() ---
    public IngresoStock() {
        initComponents();
        
        // Formatear columnas de la tabla
        javax.swing.table.DefaultTableModel modeloInit = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID Producto", "Producto", "Cantidad", "Precio Compra", "Subtotal" }
        );
        tablaDetalle.setModel(modeloInit);

        // Cargar combos al iniciar
        cargarProveedores();
        cargarCategorias();
        cargarProductos();
    }

    // --- MÉTODOS DE APOYO (Poner debajo del constructor) ---
    private void cargarProveedores() {
        cboProveedor.removeAllItems();
        String sql = "SELECT id_proveedor, razon_social, ruc FROM proveedor ORDER BY id_proveedor";
        try (java.sql.Connection con = new modelo.Conexion().conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cboProveedor.addItem(rs.getInt("id_proveedor") + " - " + rs.getString("razon_social") + " (RUC: " + rs.getString("ruc") + ")");
            }
        } catch (Exception e) {
            System.out.println("Error al cargar proveedores: " + e.getMessage());
        }
    }

    private void cargarCategorias() {
        cboCategoria.removeAllItems();
        cboCategoria.addItem("0 - [TODAS LAS CATEGORÍAS]");
        String sql = "SELECT id_categoria, nombre FROM categoria ORDER BY id_categoria";
        try (java.sql.Connection con = new modelo.Conexion().conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cboCategoria.addItem(rs.getInt("id_categoria") + " - " + rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar categorías: " + e.getMessage());
        }
    }

    private void cargarProductos() {
        cboProducto.removeAllItems();
        if (cboCategoria.getSelectedItem() == null) return;

        try {
            String catSeleccionada = cboCategoria.getSelectedItem().toString();
            int idCat = Integer.parseInt(catSeleccionada.split(" - ")[0]);

            String sql;
            if (idCat == 0) {
                sql = "SELECT p.id_producto, c.nombre as categoria, p.nombre FROM producto p JOIN categoria c ON p.id_categoria = c.id_categoria ORDER BY c.nombre, p.nombre";
            } else {
                sql = "SELECT p.id_producto, c.nombre as categoria, p.nombre FROM producto p JOIN categoria c ON p.id_categoria = c.id_categoria WHERE p.id_categoria = " + idCat + " ORDER BY p.nombre";
            }

            try (java.sql.Connection con = new modelo.Conexion().conectar();
                 java.sql.PreparedStatement ps = con.prepareStatement(sql);
                 java.sql.ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cboProducto.addItem(rs.getInt("id_producto") + " - [" + rs.getString("categoria") + "] " + rs.getString("nombre"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
        }
    }

    private void actualizarTotal() {
        double total = 0.0;
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaDetalle.getModel();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total += Double.parseDouble(modelo.getValueAt(i, 4).toString());
        }
        lblTotal.setText(String.format("Total: S/ %.2f", total));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtCantidad = new javax.swing.JTextField();
        cboProducto = new javax.swing.JComboBox<>();
        txtPrecioCompra = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        cboCategoria = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnRegistrarCompra = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboProveedor = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        cboProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Categoría:");

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboCategoria.addActionListener(this::cboCategoriaActionPerformed);

        jLabel1.setText("Proveedor:");

        tablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaDetalle);

        lblTotal.setText("Total: S/ 0.00");

        btnRegistrarCompra.setText("Registrar Compra");
        btnRegistrarCompra.addActionListener(this::btnRegistrarCompraActionPerformed);

        jLabel3.setText("Producto:");

        jLabel4.setText("Cantidad:");

        cboProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboProveedor.addActionListener(this::cboProveedorActionPerformed);

        jLabel5.setText("Precio:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTotal)
                        .addGap(18, 18, 18)
                        .addComponent(btnRegistrarCompra)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPrecioCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregar))
                            .addComponent(txtCantidad)
                            .addComponent(cboProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboProveedor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecioCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(btnRegistrarCompra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
try {
        if (cboProducto.getSelectedItem() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un producto.");
            return;
        }
        
        String textoProd = cboProducto.getSelectedItem().toString();
        int idProducto = Integer.parseInt(textoProd.split(" - ")[0]);
        int cantidad = Integer.parseInt(txtCantidad.getText().trim());
        double precioCompra = Double.parseDouble(txtPrecioCompra.getText().trim());

        if (cantidad <= 0 || precioCompra <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "La cantidad y el precio deben ser mayores a 0.");
            return;
        }

        // Extraer el nombre limpio del producto
        String nombreProd = textoProd.split("] ")[1];
        double subtotal = precioCompra * cantidad;

        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaDetalle.getModel();
        modeloTabla.addRow(new Object[]{idProducto, nombreProd, cantidad, precioCompra, subtotal});

        actualizarTotal();

        // Limpiar controles de entrada
        if (cboProducto.getItemCount() > 0) cboProducto.setSelectedIndex(0);
        txtCantidad.setText("");
        txtPrecioCompra.setText("");

    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese números válidos en Cantidad y Precio de Compra.");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage());
    }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void cboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaActionPerformed
        cargarProductos();
    }//GEN-LAST:event_cboCategoriaActionPerformed

    private void btnRegistrarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarCompraActionPerformed
       try {
        if (cboProveedor.getSelectedItem() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un proveedor.");
            return;
        }
        
        String textoProveedor = cboProveedor.getSelectedItem().toString();
        int idProveedor = Integer.parseInt(textoProveedor.split(" - ")[0]);

        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaDetalle.getModel();
        int rowCount = modeloTabla.getRowCount();

        if (rowCount == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "La lista de compra está vacía. Agregue productos primero.");
            return;
        }

        double montoTotal = 0.0;
        for (int i = 0; i < rowCount; i++) {
            montoTotal += Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());
        }

        java.sql.Connection con = null;
        try {
            con = new modelo.Conexion().conectar();
            con.setAutoCommit(false); // Transacción segura

            // 1. Ejecutar Stored Procedure SP_REGISTRAR_COMPRA
            String sqlCompra = "{call SP_REGISTRAR_COMPRA(?, ?, ?)}";
            java.sql.CallableStatement csCompra = con.prepareCall(sqlCompra);
            csCompra.setInt(1, idProveedor);
            csCompra.setDouble(2, montoTotal);
            csCompra.registerOutParameter(3, java.sql.Types.INTEGER);
            csCompra.execute();

            int idCompraGenerado = csCompra.getInt(3);

            // 2. Ejecutar Stored Procedure SP_REGISTRAR_DETALLE_COMPRA por cada producto
            String sqlDetalle = "{call SP_REGISTRAR_DETALLE_COMPRA(?, ?, ?, ?, ?)}";
            java.sql.CallableStatement csDetalle = con.prepareCall(sqlDetalle);

            for (int i = 0; i < rowCount; i++) {
                int idProd = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
                int cant = Integer.parseInt(modeloTabla.getValueAt(i, 2).toString());
                double pCompra = Double.parseDouble(modeloTabla.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());

                csDetalle.setInt(1, idCompraGenerado);
                csDetalle.setInt(2, idProd);
                csDetalle.setInt(3, cant);
                csDetalle.setDouble(4, pCompra);
                csDetalle.setDouble(5, subtotal);
                
                // Ejecuta el SP: Oracle guarda el detalle y SUMA EL STOCK
                csDetalle.execute(); 
            }

            con.commit(); // Guardar cambios en Oracle
            javax.swing.JOptionPane.showMessageDialog(this, "¡Compra # " + idCompraGenerado + " registrada con éxito! Stock aumentado en Oracle.");

            // Limpiar la pantalla
            modeloTabla.setRowCount(0);
            if (cboProveedor.getItemCount() > 0) cboProveedor.setSelectedIndex(0);
            if (cboCategoria.getItemCount() > 0) cboCategoria.setSelectedIndex(0);
            actualizarTotal();

        } catch (Exception ex) {
            if (con != null) con.rollback(); // Si algo falla, deshace la operación
            javax.swing.JOptionPane.showMessageDialog(this, "Error en BD al registrar compra: " + ex.getMessage());
        } finally {
            if (con != null) con.close();
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error general: " + e.getMessage());
    }
    }//GEN-LAST:event_btnRegistrarCompraActionPerformed

    private void cboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboProveedorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new IngresoStock().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnRegistrarCompra;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JComboBox<String> cboProducto;
    private javax.swing.JComboBox<String> cboProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tablaDetalle;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtPrecioCompra;
    // End of variables declaration//GEN-END:variables
}

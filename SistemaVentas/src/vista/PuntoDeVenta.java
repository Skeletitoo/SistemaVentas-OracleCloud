package vista;

public class PuntoDeVenta extends javax.swing.JFrame {
    
    private int idClienteSeleccionado = -1; // Guardará el ID del cliente encontrado por DNI
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PuntoDeVenta.class.getName());

    public PuntoDeVenta() {
        initComponents();
        javax.swing.table.DefaultTableModel modeloInit = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "ID Producto", "Producto", "Cantidad", "P. Unitario", "Subtotal" }
        );
        tablaDetalle.setModel(modeloInit);
        
        cargarCategorias();
        cargarEmpleados();
        cargarProductos();
        actualizarTotal();
    } 

    private void cargarEmpleados() {
        cboEmpleado.removeAllItems();
        String sql = "SELECT id_empleado, nombre FROM empleado WHERE rol = 'CAJERO' ORDER BY id_empleado";
        try (java.sql.Connection con = new modelo.Conexion().conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cboEmpleado.addItem(rs.getInt("id_empleado") + " - " + rs.getString("nombre"));
            }
        } catch (Exception e) {
            System.out.println("Error al cargar empleados: " + e.getMessage());
        }
    }
    
    private void cargarProductos() {
        cboProducto.removeAllItems();
        // Si aún no hay categoría seleccionada, salimos para evitar errores
        if (cboCategoria.getSelectedItem() == null) return; 

        try {
            String catSeleccionada = cboCategoria.getSelectedItem().toString();
            int idCat = Integer.parseInt(catSeleccionada.split(" - ")[0]);

            String sql;
            if (idCat == 0) {
                // Muestra todos si elige "TODAS LAS CATEGORÍAS"
                sql = "SELECT p.id_producto, c.nombre as categoria, p.nombre FROM producto p JOIN categoria c ON p.id_categoria = c.id_categoria ORDER BY c.nombre, p.nombre";
            } else {
                // Filtra por la categoría específica
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
            System.out.println("Error al cargar productos filtrados: " + e.getMessage());
        }
    }
    
    private void cargarCategorias() {
        cboCategoria.removeAllItems();
        cboCategoria.addItem("0 - [TODAS LAS CATEGORÍAS]"); // Opción por defecto
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
    
    private void actualizarTotal() {
        double total = 0.0;
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaDetalle.getModel();
        
        // Sumamos toda la columna 4 (Subtotales)
        for (int i = 0; i < modelo.getRowCount(); i++) {
            total += Double.parseDouble(modelo.getValueAt(i, 4).toString());
        }
        
        // Actualizamos el texto del Label (asumiendo que le pusiste de nombre lblTotal)
        lblTotal.setText(String.format("Total: S/ %.2f", total));
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalle = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cboEmpleado = new javax.swing.JComboBox<>();
        cboProducto = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox<>();
        txtDniCliente = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        txtNombreCliente = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("DNI/RUC");

        jLabel2.setText("Empleado:");

        jLabel3.setText("ID Producto:");

        jLabel4.setText("Cantidad:");

        jLabel5.setText("Precio:");

        txtPrecio.setEditable(false);

        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);

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

        jButton1.setText("Cobrar y Registrar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        cboEmpleado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel6.setText("Categoría:");

        cboCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboCategoria.addActionListener(this::cboCategoriaActionPerformed);

        btnBuscarCliente.setText("Buscar");
        btnBuscarCliente.addActionListener(this::btnBuscarClienteActionPerformed);

        txtNombreCliente.setEditable(false);

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
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscarCliente)
                                .addGap(3, 3, 3))
                            .addComponent(txtNombreCliente)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPrecio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAgregar))
                            .addComponent(txtCantidad)
                            .addComponent(cboProducto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btnAgregar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotal)
                    .addComponent(jButton1))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
        // 1. Validar que se haya buscado y encontrado un cliente válido por DNI/RUC
        if (idClienteSeleccionado == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor ingrese el DNI/RUC y presione 'Buscar' para validar al cliente.");
            return;
        }
        int idCliente = idClienteSeleccionado;

        if (cboEmpleado.getSelectedItem() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Seleccione un empleado.");
            return;
        }
        String textoEmpleado = cboEmpleado.getSelectedItem().toString();
        int idEmpleado = Integer.parseInt(textoEmpleado.split(" - ")[0]);

        javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaDetalle.getModel();
        int rowCount = modeloTabla.getRowCount();

        if (rowCount == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "El carrito está vacío. Agregue productos primero.");
            return;
        }

        // 2. Calcular el monto total
        double montoTotal = 0.0;
        for (int i = 0; i < rowCount; i++) {
            montoTotal += Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());
        }

        java.sql.Connection con = null;
        try {
            con = new modelo.Conexion().conectar();
            con.setAutoCommit(false); // Transacción segura

            // 3. LLAMAR AL STORED PROCEDURE DE LA VENTA (PL/SQL)
            String sqlVenta = "{call SP_REGISTRAR_VENTA(?, ?, ?, ?)}";
            java.sql.CallableStatement csVenta = con.prepareCall(sqlVenta);
            csVenta.setInt(1, idCliente);
            csVenta.setInt(2, idEmpleado);
            csVenta.setDouble(3, montoTotal);
            csVenta.registerOutParameter(4, java.sql.Types.INTEGER);
            csVenta.execute();

            int idVentaGenerado = csVenta.getInt(4);

            // 4. LLAMAR AL STORED PROCEDURE DEL DETALLE (PL/SQL)
            String sqlDetalle = "{call SP_REGISTRAR_DETALLE_VENTA(?, ?, ?, ?)}";
            java.sql.CallableStatement csDetalle = con.prepareCall(sqlDetalle);

            for (int i = 0; i < rowCount; i++) {
                int idProd = Integer.parseInt(modeloTabla.getValueAt(i, 0).toString());
                int cant = Integer.parseInt(modeloTabla.getValueAt(i, 2).toString());
                double subtotal = Double.parseDouble(modeloTabla.getValueAt(i, 4).toString());

                csDetalle.setInt(1, idVentaGenerado);
                csDetalle.setInt(2, idProd);
                csDetalle.setInt(3, cant);
                csDetalle.setDouble(4, subtotal);
                
                // Oracle inserta el detalle y descuenta el stock automáticamente
                csDetalle.execute(); 
            }

            // 5. Confirmar transacción
            con.commit();
            javax.swing.JOptionPane.showMessageDialog(this, "¡Venta # " + idVentaGenerado + " registrada con éxito usando PL/SQL!");

            // 6. Limpiar la pantalla tras la venta
            modeloTabla.setRowCount(0);
            txtDniCliente.setText("");
            txtNombreCliente.setText("");
            idClienteSeleccionado = -1;
            
            if (cboEmpleado.getItemCount() > 0) cboEmpleado.setSelectedIndex(0);
            actualizarTotal(); // Reinicia el total a S/ 0.00
            
        } catch (Exception ex) {
            if (con != null) con.rollback(); // Si falla, deshace la venta completa
            javax.swing.JOptionPane.showMessageDialog(this, "Error en BD: " + ex.getMessage());
        } finally {
            if (con != null) con.close();
        }

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error general: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        
        try {
        String textoProd = cboProducto.getSelectedItem().toString();
        int idProducto = Integer.parseInt(textoProd.split(" - ")[0]);
        int cantidad = Integer.parseInt(txtCantidad.getText().trim());
        
        String sql = "SELECT NOMBRE, PRECIO_UNITARIO, STOCK_ACTUAL FROM PRODUCTO WHERE ID_PRODUCTO = ?";
        
        try (java.sql.Connection con = new modelo.Conexion().conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idProducto);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nombreProducto = rs.getString("NOMBRE");
                    double precioUnitario = rs.getDouble("PRECIO_UNITARIO");
                    int stockActual = rs.getInt("STOCK_ACTUAL");
                    
                    if (stockActual < cantidad) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Stock insuficiente. Stock disponible: " + stockActual);
                        return;
                    }
                    
                    txtPrecio.setText(String.valueOf(precioUnitario));
                    double subtotal = precioUnitario * cantidad;
                    
                    javax.swing.table.DefaultTableModel modeloTabla = (javax.swing.table.DefaultTableModel) tablaDetalle.getModel();
                    modeloTabla.addRow(new Object[]{idProducto, nombreProducto, cantidad, precioUnitario, subtotal});
                    actualizarTotal();
                    // --- CORRECCIÓN: Reiniciamos el ComboBox en lugar del txt eliminado ---
                    if (cboProducto.getItemCount() > 0) cboProducto.setSelectedIndex(0);
                    txtCantidad.setText("");
                    txtPrecio.setText("");
                    
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "¡El ID de producto no existe en Oracle!");
                }
            }
        }
    } catch (NumberFormatException e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Ingrese números válidos en ID y Cantidad.");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Error al agregar producto: " + e.getMessage());
    }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void cboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaActionPerformed
       cargarProductos();
    }//GEN-LAST:event_cboCategoriaActionPerformed

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        String dni = txtDniCliente.getText().trim();
        if (dni.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Ingrese el DNI o RUC del cliente.");
                return;
        }

        String sql = "SELECT id_cliente, nombre FROM cliente WHERE doc_identidad = ?";
        try (java.sql.Connection con = new modelo.Conexion().conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni);
                try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idClienteSeleccionado = rs.getInt("id_cliente");
                    txtNombreCliente.setText(rs.getString("nombre"));
                } else {
                    idClienteSeleccionado = -1;
                    txtNombreCliente.setText("");
                    javax.swing.JOptionPane.showMessageDialog(this, "Cliente no encontrado con el DNI/RUC: " + dni);
                }
            }
         } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al buscar cliente: " + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new PuntoDeVenta().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JComboBox<String> cboEmpleado;
    private javax.swing.JComboBox<String> cboProducto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tablaDetalle;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}

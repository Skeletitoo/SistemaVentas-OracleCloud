package vista;

public class ReporteBalance extends javax.swing.JFrame {

    public ReporteBalance() {
        initComponents();
        this.setLocationRelativeTo(null); // Centrar ventana
        
        // Configurar columnas de la tabla
        javax.swing.table.DefaultTableModel modeloInit = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] { "N° Transacción", "Tipo", "Cliente / Proveedor", "Fecha y Hora", "Monto Total (S/)" }
        );
        tablaBalance.setModel(modeloInit);

        // Cargar métricas y tabla al iniciar
        calcularResumenKPIs();
        cargarTablaBalance("TODOS");
    }
  // Método 1: Calcular Ingresos, Egresos y Ganancia Neta
    private void calcularResumenKPIs() {
        double totalIngresos = 0.0;
        double totalEgresos = 0.0;

        String sqlIngresos = "SELECT NVL(SUM(monto_total), 0) AS total FROM ventas";
        String sqlEgresos = "SELECT NVL(SUM(monto_total), 0) AS total FROM compra";

        try (java.sql.Connection con = new modelo.Conexion().conectar()) {
            // 1. Sumar Ventas
            try (java.sql.PreparedStatement ps = con.prepareStatement(sqlIngresos);
                 java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) totalIngresos = rs.getDouble("total");
            }
            // 2. Sumar Compras
            try (java.sql.PreparedStatement ps = con.prepareStatement(sqlEgresos);
                 java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) totalEgresos = rs.getDouble("total");
            }

            double gananciaNeta = totalIngresos - totalEgresos;

            // Mostrar en los Labels
            lblIngresos.setText(String.format("Ingresos (Ventas): S/ %.2f", totalIngresos));
            lblEgresos.setText(String.format("Egresos (Compras): S/ %.2f", totalEgresos));
            lblGanancia.setText(String.format("Ganancia Neta: S/ %.2f", gananciaNeta));

        } catch (Exception e) {
            System.out.println("Error al calcular balance KPI: " + e.getMessage());
        }
    }

    // Método 2: Cargar la tabla con filtro
    private void cargarTablaBalance(String filtro) {
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tablaBalance.getModel();
        modelo.setRowCount(0); // Limpiar tabla

        String sql = "";
        if (filtro.equals("VENTAS")) {
            sql = "SELECT v.id_venta AS id, 'VENTA' AS tipo, c.nombre AS tercero, TO_CHAR(v.fecha_hora, 'YYYY-MM-DD HH24:MI') AS fecha, v.monto_total " +
                  "FROM ventas v JOIN cliente c ON v.id_cliente = c.id_cliente ORDER BY v.fecha_hora DESC";
        } else if (filtro.equals("COMPRAS")) {
            sql = "SELECT co.id_compra AS id, 'COMPRA' AS tipo, p.razon_social AS tercero, TO_CHAR(co.fecha_hora, 'YYYY-MM-DD HH24:MI') AS fecha, co.monto_total " +
                  "FROM compra co JOIN proveedor p ON co.id_proveedor = p.id_proveedor ORDER BY co.fecha_hora DESC";
        } else { // TODOS (UNION ALL)
            sql = "SELECT v.id_venta AS id, 'VENTA' AS tipo, c.nombre AS tercero, TO_CHAR(v.fecha_hora, 'YYYY-MM-DD HH24:MI') AS fecha, v.monto_total " +
                  "FROM ventas v JOIN cliente c ON v.id_cliente = c.id_cliente " +
                  "UNION ALL " +
                  "SELECT co.id_compra AS id, 'COMPRA' AS tipo, p.razon_social AS tercero, TO_CHAR(co.fecha_hora, 'YYYY-MM-DD HH24:MI') AS fecha, co.monto_total " +
                  "FROM compra co JOIN proveedor p ON co.id_proveedor = p.id_proveedor " +
                  "ORDER BY fecha DESC";
        }

        try (java.sql.Connection con = new modelo.Conexion().conectar();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("tipo"),
                    rs.getString("tercero"),
                    rs.getString("fecha"),
                    String.format("S/ %.2f", rs.getDouble("monto_total"))
                });
            }
        } catch (Exception e) {
            System.out.println("Error al cargar tabla de balance: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        lblIngresos = new javax.swing.JLabel();
        lblEgresos = new javax.swing.JLabel();
        lblGanancia = new javax.swing.JLabel();
        rbTodos = new javax.swing.JButton();
        rbVentas = new javax.swing.JButton();
        rbCompras = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaBalance = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblIngresos.setText("Ingresos: S/ 0.00");

        lblEgresos.setText("Egresos: S/ 0.00");

        lblGanancia.setText("Ganancia Neta: S/ 0.00");

        rbTodos.setText("Ver Todo");
        rbTodos.addActionListener(this::rbTodosActionPerformed);

        rbVentas.setText("Ventas");
        rbVentas.addActionListener(this::rbVentasActionPerformed);

        rbCompras.setText("Compras");
        rbCompras.addActionListener(this::rbComprasActionPerformed);

        tablaBalance.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaBalance);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblIngresos)
                                .addGap(33, 33, 33))
                            .addComponent(lblEgresos)
                            .addComponent(lblGanancia)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(rbTodos)
                        .addGap(48, 48, 48)
                        .addComponent(rbVentas)
                        .addGap(40, 40, 40)
                        .addComponent(rbCompras))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIngresos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEgresos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblGanancia)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbTodos)
                    .addComponent(rbVentas)
                    .addComponent(rbCompras))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rbComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbComprasActionPerformed
        cargarTablaBalance("COMPRAS");
    }//GEN-LAST:event_rbComprasActionPerformed

    private void rbTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTodosActionPerformed
        cargarTablaBalance("TODOS");
    }//GEN-LAST:event_rbTodosActionPerformed

    private void rbVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbVentasActionPerformed
        cargarTablaBalance("VENTAS");
    }//GEN-LAST:event_rbVentasActionPerformed

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
            System.out.println("Error de apariencia: " + ex.getMessage());
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ReporteBalance().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEgresos;
    private javax.swing.JLabel lblGanancia;
    private javax.swing.JLabel lblIngresos;
    private javax.swing.JButton rbCompras;
    private javax.swing.JButton rbTodos;
    private javax.swing.JButton rbVentas;
    private javax.swing.JTable tablaBalance;
    // End of variables declaration//GEN-END:variables
}

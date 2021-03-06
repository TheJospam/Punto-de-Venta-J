/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package punto.de.venta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author usuario
 */
public class Ventas extends javax.swing.JFrame {

    /**
     * Creates new form Ventas
     */
    public Ventas() {

        initComponents();

        SimpleTimer.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    /*private String[][] productos = {
        {"1", "Burro Percherón", "100"},
        {"2", "Hot Dogs", "60"},
        {"3", "Ensalada", "130"},
        {"4", "Hamburguesa", "150"},
        {"5", "Sushi", "120"}
    };*/
    Conexion c = new Conexion();
    List<Producto> productos = null;
    List<Producto> vendidos = new ArrayList();
    SimpleDateFormat SimpleDay = new SimpleDateFormat("EEEE");
    SimpleDateFormat SimpleDate = new SimpleDateFormat("dd MMMM yyyy");
    SimpleDateFormat SimpleTime = new SimpleDateFormat("H:mm:ss");

    Timer SimpleTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            time.setText(SimpleDate.format(new Date()) + " " + SimpleTime.format(new Date()));
        }
    });

    private void buscarProductos() {

        if (inputtext.getText().indexOf('*') != -1) {

            String[] temparray = inputtext.getText().split(Pattern.quote("*"));

            for (int i = 0; i < productos.size(); i++) {

                try {

                    if (Integer.parseInt(temparray[1]) == productos.get(i).getCodigo()) {

                        if (Integer.parseInt(temparray[0]) > productos.get(i).getInventario()) {

                            JOptionPane.showMessageDialog(null, "Solamente hay " + productos.get(i).getInventario() + " en existencia.", "No hay existencias", JOptionPane.WARNING_MESSAGE);

                        } else {

                            Producto p = productos.get(i);
                            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                            model.addRow(new Object[]{
                                p.getNombre(), temparray[0], p.getPrecio(),
                                (float) p.getPrecio() * (float) p.getDescuento() / (float) 100,
                                Float.parseFloat(temparray[0]) * ((float) p.getPrecio() - (float) p.getPrecio() * (float) p.getDescuento() / (float) 100)
                            });
                            total();
                            c.Reduce(p.getCodigo(), p.getInventario() - Integer.parseInt(temparray[0]));
                            vendidos.add(p);

                        }

                    }
                } catch (Exception e) {

                    JOptionPane.showMessageDialog(null, "Ingrese una cantidad válida.", "Error", JOptionPane.WARNING_MESSAGE);
                    inputtext.setText("");

                }

            }
        } else {

            int code;

            try {
                code = Integer.parseInt(inputtext.getText());

            } catch (NumberFormatException e) {

                code = 0;
                JOptionPane.showMessageDialog(null, "Inserte un código válido.", "Error", JOptionPane.WARNING_MESSAGE);
                System.out.println(e.getMessage());
                inputtext.setText("");

            }
            for (int i = 0; i < productos.size(); i++) {

                if (code == productos.get(i).getCodigo()) {

                    if (productos.get(i).getInventario() == 0) {

                        JOptionPane.showMessageDialog(null, "No hay de ese producto en existencia.", "Error", JOptionPane.WARNING_MESSAGE);

                    } else {

                        Producto p = productos.get(i);
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        model.addRow(new Object[]{
                            p.getNombre(), "1", p.getPrecio(),
                            (float) p.getPrecio() * (float) p.getDescuento() / (float) 100,
                            (float) p.getPrecio() - (float) p.getPrecio() * (float) p.getDescuento() / (float) 100
                        });
                        total();
                        c.Reduce(p.getCodigo(), p.getInventario() - 1);
                        vendidos.add(p);

                    }

                }
            }
        }
        inputtext.requestFocus();
        productos = c.getProductos();
    }

    private float total() {

        float totalnum = 0.0f;
        for (int i = 0; i < jTable1.getRowCount(); i++) {

            totalnum += Float.parseFloat(jTable1.getModel().getValueAt(i, 4).toString());

        }

        total.setText("Total =  " + totalnum);
        inputtext.setText("");
        inputtext.requestFocus();
        return totalnum;

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Title = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        inputtext = new javax.swing.JTextField();
        total = new javax.swing.JLabel();
        time1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        Title.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 18)); // NOI18N
        Title.setText("SISTEMA DE PUNTO DE VENTA MAG v1.0");

        time.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        time.setText("00:00:00");

        jTable1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Precio Unitario", "Descuento Unitario", "Importe"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setEnabled(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        inputtext.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        inputtext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputtextKeyPressed(evt);
            }
        });

        total.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        total.setText("Total =  ");

        time1.setFont(new java.awt.Font("Microsoft Sans Serif", 0, 14)); // NOI18N
        time1.setText("Caja #1");

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Presione \"h\" para abrir el menú de ayuda.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Title)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(time)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(time1)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addComponent(inputtext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(46, 46, 46))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time)
                    .addComponent(time1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                .addGap(7, 7, 7)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputtext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputtextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputtextKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            productos = c.getProductos();
            buscarProductos();

            inputtext.setText("");

        }
        if (evt.getKeyCode() == KeyEvent.VK_H) {

            Help h = new Help();
            h.setVisible(true);
            inputtext.setText("");

        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {

            if (jTable1.getRowCount() >= 1) {

                Producto p = vendidos.get(vendidos.size() - 1);
                c.Increment(p.getCodigo(), p.getInventario());
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.removeRow(model.getRowCount() - 1);
                vendidos.remove(p);
                total();

            }

        }
        if (evt.getKeyCode() == KeyEvent.VK_P) {
            float subtotal = total() / (float) 1.16;
            float iva = total() - subtotal;
            if (vendidos.size() >= 1) {

                int dialogResult = JOptionPane.showConfirmDialog(null, "¿Listo para pagar?", "Pagar", JOptionPane.YES_NO_OPTION);

                if (dialogResult == JOptionPane.YES_OPTION) {

                    Input in = new Input(subtotal, iva, total());
                    in.setVisible(true);
                    DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
                    int rowCount = dm.getRowCount();
//Remove rows one by one from the end of the table
                    for (int i = rowCount - 1; i >= 0; i--) {

                        dm.removeRow(i);

                    }

                    total.setText("Total = ");

                } else {

                    inputtext.setText("");

                }
            } else {

                JOptionPane.showMessageDialog(null, "No puede pagar si no se ha vendido nada.", "Error", JOptionPane.WARNING_MESSAGE);

            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_S) {
            if (jTable1.getRowCount() > 0) {
                JOptionPane.showMessageDialog(null, "Termine o cancele su venta antes de salir", "Error", JOptionPane.WARNING_MESSAGE);
                inputtext.setText("");
            } else {

                int result = JOptionPane.showConfirmDialog(null, "¿Seguro que desea salir?", "Salir",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    inputtext.setText("");
                }

            }

        }
    }//GEN-LAST:event_inputtextKeyPressed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Title;
    private javax.swing.JTextField inputtext;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel time;
    private javax.swing.JLabel time1;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}

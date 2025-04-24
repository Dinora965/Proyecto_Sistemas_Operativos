package gui;

import algoritmos.FCFS;
import algoritmos.RoundRobin;
import algoritmos.SJF;
import java.awt.BorderLayout;
import java.io.File;
import java.io.PrintWriter;
import modelo.Proceso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VentanaSimulador extends javax.swing.JFrame {
    private DefaultTableModel modeloTabla;
    private List<Proceso> listaProcesos;
    private gui.PanelGantt panelGantt;

    private void cargarProcesosDesdeArchivo(File archivo) {
    try (Scanner scanner = new Scanner(archivo)) {
        listaProcesos.clear();
        modeloTabla.setRowCount(0);

        boolean primeraLinea = true;
        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine();
            if (primeraLinea) {
                primeraLinea = false;
                continue; // saltar encabezado
            }

            String[] datos = linea.split(",");
            if (datos.length >= 4) {
                String id = datos[0].trim();
                int llegada = Integer.parseInt(datos[1].trim());
                int rafaga = Integer.parseInt(datos[2].trim());
                int prioridad = Integer.parseInt(datos[3].trim());

                Proceso p = new Proceso(id, llegada, rafaga, prioridad);
                listaProcesos.add(p);
                modeloTabla.addRow(new Object[]{id, llegada, rafaga, prioridad, "", "", ""});
            }
        }

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage());
    }
}
    private void guardarResultadosCSV(List<Proceso> lista) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Guardar resultados");
    int seleccion = fileChooser.showSaveDialog(this);

    if (seleccion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        try (PrintWriter pw = new PrintWriter(archivo)) {
            pw.println("ID,Llegada,Rafaga,Prioridad,Espera,Respuesta,Retorno");
            for (Proceso p : lista) {
                pw.printf("%s,%d,%d,%d,%d,%d,%d%n",
                        p.getId(), p.getTiempoLlegada(), p.getRafaga(), p.getPrioridad(),
                        p.getTiempoEspera(), p.getTiempoRespuesta(), p.getTiempoRetorno());
            }
            JOptionPane.showMessageDialog(this, "Resultados guardados correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + e.getMessage());
        }
    }
}

public VentanaSimulador() {
    initComponents();
    setTitle("Simulador de Planificación de Procesos");
    setLocationRelativeTo(null);

    listaProcesos = new ArrayList<>();
    modeloTabla = new DefaultTableModel(new String[]{
            "ID", "Llegada", "Ráfaga", "Prioridad", "Espera", "Respuesta", "Retorno"
    }, 0);
    tablaProcesos.setModel(modeloTabla);
    
    // Configuración del ComboBox
    cbAlgoritmo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { 
        "Seleccione algoritmo", 
        "FCFS (First Come First Served)", 
        "SJF (Shortest Job First)", 
        "Round Robin" 
    }));
    
    // Configuración del panel Gantt
    panelGantt = new PanelGantt();
    JScrollPane scrollGantt = new JScrollPane(panelGantt);
    scrollGantt.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    
    jPanel1.removeAll();
    jPanel1.setLayout(new BorderLayout());
    jPanel1.add(scrollGantt, BorderLayout.CENTER);
    jPanel1.revalidate();
    jPanel1.repaint();
}
    private void simularFCFS() {
        List<Proceso> resultado = FCFS.ejecutar(listaProcesos);
        actualizarResultados(resultado);
        panelGantt.setSecuencia(FCFS.getSecuenciaEjecucion(), FCFS.getBloquesEjecucion());
    }

    private void simularSJF() {
        List<Proceso> resultado = SJF.ejecutar(listaProcesos);
        actualizarResultados(resultado);
        panelGantt.setSecuencia(SJF.getSecuenciaEjecucion(), SJF.getBloquesEjecucion());
    }

    private void simularRoundRobin() {
        int quantum = Integer.parseInt(txtQuantum.getText());
        List<Proceso> resultado = RoundRobin.ejecutar(listaProcesos, quantum);
        actualizarResultados(resultado);
        panelGantt.setSecuencia(RoundRobin.getSecuenciaEjecucion(), RoundRobin.getBloquesEjecucion());
    }

    private void actualizarResultados(List<Proceso> resultado) {
        for (int i = 0; i < resultado.size(); i++) {
            Proceso p = resultado.get(i);
            modeloTabla.setValueAt(p.getTiempoEspera(), i, 4);
            modeloTabla.setValueAt(p.getTiempoRespuesta(), i, 5);
            modeloTabla.setValueAt(p.getTiempoRetorno(), i, 6);
        }
    }
    private void limpiarCampos() {
        txtID.setText("");
        txtLlegada.setText("");
        txtRafaga.setText("");
        txtPrioridad.setText("");
        txtQuantum.setText("");
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        txtLlegada = new javax.swing.JTextField();
        txtRafaga = new javax.swing.JTextField();
        txtPrioridad = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProcesos = new javax.swing.JTable();
        txtQuantum = new javax.swing.JTextField();
        btnCargarArchivo = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cbAlgoritmo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(102, 0, 0));

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        txtLlegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLlegadaActionPerformed(evt);
            }
        });

        txtRafaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRafagaActionPerformed(evt);
            }
        });

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/library_add_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        btnAgregar.setText("Agregar Proceso");
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        tablaProcesos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tablaProcesos.setForeground(new java.awt.Color(51, 51, 51));
        tablaProcesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Llegada", "Rafaga", "Prioridad", "Espera", "Respuesta", "Retorno"
            }
        ));
        tablaProcesos.setGridColor(new java.awt.Color(204, 204, 204));
        tablaProcesos.setSelectionForeground(new java.awt.Color(102, 0, 0));
        jScrollPane1.setViewportView(tablaProcesos);

        txtQuantum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantumActionPerformed(evt);
            }
        });

        btnCargarArchivo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCargarArchivo.setForeground(new java.awt.Color(255, 255, 255));
        btnCargarArchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/drive_folder_upload_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        btnCargarArchivo.setText("Cargar Archivo");
        btnCargarArchivo.setBorderPainted(false);
        btnCargarArchivo.setContentAreaFilled(false);
        btnCargarArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarArchivoActionPerformed(evt);
            }
        });

        btnguardar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnguardar.setForeground(new java.awt.Color(255, 255, 255));
        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/save_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        btnguardar.setText("Guardar");
        btnguardar.setBorderPainted(false);
        btnguardar.setContentAreaFilled(false);
        btnguardar.setFocusPainted(false);
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tiempo de Llegada:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Rafaga:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Prioridad:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Quantum:");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        cbAlgoritmo.setBackground(new java.awt.Color(102, 0, 0));
        cbAlgoritmo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cbAlgoritmo.setForeground(new java.awt.Color(255, 255, 255));
        cbAlgoritmo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-seleccione un Algoritmo-", "FSC(first Come First Served)", "Round Robin", "SJF (Shortest Job Fist)" }));
        cbAlgoritmo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAlgoritmoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Lucida Sans Unicode", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Simulador de Planificación de Procesos");

        btnLimpiar.setBackground(new java.awt.Color(102, 0, 0));
        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/cleaning_services_24dp_FFFFFF_FILL0_wght400_GRAD0_opsz24.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setContentAreaFilled(false);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(95, 95, 95)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addGap(72, 72, 72)
                                        .addComponent(jLabel3)
                                        .addGap(153, 153, 153)
                                        .addComponent(jLabel4))
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(txtRafaga, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(100, 100, 100)
                                        .addComponent(txtPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(92, 92, 92)
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtQuantum, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnCargarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(panelPrincipalLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(btnLimpiar)))))))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPrincipalLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(143, 143, 143))
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrincipalLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRafaga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrioridad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btnCargarArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregar)
                        .addGap(18, 18, 18)
                        .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar))
                    .addGroup(panelPrincipalLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(cbAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
   try {
            String id = txtID.getText();
            int llegada = Integer.parseInt(txtLlegada.getText());
            int rafaga = Integer.parseInt(txtRafaga.getText());
            int prioridad = Integer.parseInt(txtPrioridad.getText());

            Proceso p = new Proceso(id, llegada, rafaga, prioridad);
            listaProcesos.add(p);

            modeloTabla.addRow(new Object[]{id, llegada, rafaga, prioridad, "", "", ""});
            limpiarCampos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos del proceso");
        }

    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLlegadaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLlegadaActionPerformed

    private void txtRafagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRafagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRafagaActionPerformed

    private void btnCargarArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarArchivoActionPerformed
    JFileChooser fileChooser = new JFileChooser();
    int opcion = fileChooser.showOpenDialog(this);
    if (opcion == JFileChooser.APPROVE_OPTION) {
        File archivo = fileChooser.getSelectedFile();
        cargarProcesosDesdeArchivo(archivo);
    }
    }//GEN-LAST:event_btnCargarArchivoActionPerformed

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
  guardarResultadosCSV(listaProcesos);        // TODO add your handling code here:
    }//GEN-LAST:event_btnguardarActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void cbAlgoritmoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAlgoritmoActionPerformed
            String algoritmoSeleccionado = (String) cbAlgoritmo.getSelectedItem();
    
    if (algoritmoSeleccionado == null || algoritmoSeleccionado.equals("Seleccione algoritmo")) {
        return;
    }
    
    if (listaProcesos.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay procesos para simular", 
                                    "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        switch(algoritmoSeleccionado) {
            case "FCFS (First Come First Served)" -> simularFCFS();
            case "SJF (Shortest Job First)" -> simularSJF();
            case "Round Robin" -> {
                // Verificar si se ingresó quantum
                if (txtQuantum.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese el valor de Quantum para Round Robin",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                simularRoundRobin();
                    }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al simular: " + ex.getMessage(), 
                                    "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_cbAlgoritmoActionPerformed

    private void txtQuantumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantumActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
        DefaultTableModel model = (DefaultTableModel) tablaProcesos.getModel();
    model.setRowCount(0);
    panelGantt.setSecuencia(null); 
    }//GEN-LAST:event_btnLimpiarActionPerformed


  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(() -> new VentanaSimulador().setVisible(true));
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCargarArchivo;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JComboBox<String> cbAlgoritmo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable tablaProcesos;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtLlegada;
    private javax.swing.JTextField txtPrioridad;
    private javax.swing.JTextField txtQuantum;
    private javax.swing.JTextField txtRafaga;
    // End of variables declaration//GEN-END:variables
        }
      

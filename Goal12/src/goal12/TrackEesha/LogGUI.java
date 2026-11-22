/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package goal12.TrackEesha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author appsd
 */
public class LogGUI extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(LogGUI.class.getName());
    /**
     * Creates new form LogGUI
     */
    private final TrackManager manager;
    private DefaultTableModel tableModel;
    
    public LogGUI() {
        this(TrackManager.getShared());
    }
    
    public LogGUI(TrackManager manager) {
        this.manager = manager;
        initComponents();
        setSize(900, 700);
        logNavBTN.setEnabled(false);
        setLocationRelativeTo(null);
        setUpForm();
        showEntries(manager.getEntries());
    }
    private void setUpForm() {
        categoryCB.setModel(new DefaultComboBoxModel<>(TrackData.CATEGORIES));
        updateItemTypes();

        buttonGroup1.add(newRB);
        buttonGroup1.add(secondHandRB);
        buttonGroup1.add(discardedRB);
        newRB.setSelected(true);

        dateTF.setText(LocalDate.now().toString());

        // table cannot be edited by typing in the cells
        tableModel = new DefaultTableModel(
                new String[]{"id", "category", "item type", "status", "date", "weight (kg)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        logTBL.setModel(tableModel);
        logTBL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        addBTN.addActionListener(e -> enterEntry());
        searchBTN.addActionListener(e -> searchEntries());
        showAllBTN.addActionListener(e -> displayEntries());
    }

    /** Item type list depends on the chosen category. */
    private void updateItemTypes() {
        String category = (String) categoryCB.getSelectedItem();
        itemTypeCB.setModel(new DefaultComboBoxModel<>(TrackData.getItemTypes(category)));
    }

    /** Checks the weight and date fields; shows a message and returns false if wrong. */
    private boolean validateInput() {
        try {
            double weight = Double.parseDouble(weightTF.getText().trim());
            if (weight <= 0 || weight > 10000) {
                showError("Weight must be more than 0 and no more than 10000 kg.", weightTF);
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Please enter the weight as a number, e.g. 1.5", weightTF);
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(dateTF.getText().trim());
            if (date.isAfter(LocalDate.now())) {
                showError("The date cannot be in the future.", dateTF);
                return false;
            }
        } catch (DateTimeParseException e) {
            showError("Please enter the date as yyyy-mm-dd, e.g. 2026-11-20", dateTF);
            return false;
        }
        return true;
    }

    private void showError(String message, javax.swing.JComponent field) {
        JOptionPane.showMessageDialog(this, message, "Check your input", JOptionPane.WARNING_MESSAGE);
        field.requestFocus();
    }

    /** Add button: builds the right type of entry and stores it. */
    private void enterEntry() {
        if (!validateInput()) {
            return;
        }
        String category = (String) categoryCB.getSelectedItem();
        String itemType = (String) itemTypeCB.getSelectedItem();
        double weight = Double.parseDouble(weightTF.getText().trim());
        LocalDate date = LocalDate.parse(dateTF.getText().trim());
        int id = manager.getNextId();

        LogEntry entry;
        if (newRB.isSelected()) {
            entry = new NewPurchaseEntry(id, category, itemType, weight, date,
                    TrackData.isSingleUse(itemType));
        } else if (secondHandRB.isSelected()) {
            int repaired = JOptionPane.showConfirmDialog(this, "Was this item repaired?",
                    "Second-hand / repaired", JOptionPane.YES_NO_OPTION);
            entry = new SecondHandEntry(id, category, itemType, weight, date,
                    repaired == JOptionPane.YES_OPTION);
        } else {
            String reason = (String) JOptionPane.showInputDialog(this, "Why was it discarded?",
                    "Discarded", JOptionPane.QUESTION_MESSAGE, null,
                    TrackData.WASTE_REASONS, TrackData.WASTE_REASONS[0]);
            if (reason == null) {
                return;   // user cancelled
            }
            entry = new DiscardedEntry(id, category, itemType, weight, date, reason);
        }

        manager.addEntry(entry);
        if (!manager.saveToFile()) {
            JOptionPane.showMessageDialog(this, "The entry was added but could not be saved to file.",
                    "Save problem", JOptionPane.WARNING_MESSAGE);
        }
        weightTF.setText("");
        showEntries(manager.getEntries());
    }

    /** Show all button. */
    private void displayEntries() {
        searchTF.setText("");
        showEntries(manager.getEntries());
    }

    /** Fills the table with the given entries and refreshes the totals. */
    private void showEntries(ArrayList<LogEntry> list) {
        tableModel.setRowCount(0);
        for (LogEntry entry : list) {
            tableModel.addRow(new Object[]{entry.getId(), entry.getCategory(), entry.getItemType(),
                entry.getStatus(), entry.getDate(), String.format("%.2f", entry.getWeightKg())});
        }
        totalsJL.setText(String.format("Footprint: %.1f kg | Waste: %.1f kg | Circularity: %.0f%%",
                manager.getTotalFootprintKg(), manager.getTotalWasteKg(), manager.getCircularityPercent()));
    }

    /** Search by category text, or by one date / a range ("2026-11-01 to 2026-11-20"). */
    private void searchEntries() {
        String text = searchTF.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Type something to search for first.");
            return;
        }
        ArrayList<LogEntry> found;
        if ("category".equals(searchByCB.getSelectedItem())) {
            found = manager.searchByCategory(text);
        } else {
            try {
                String[] parts = text.split("\\s*(to|,)\\s*");
                LocalDate from = LocalDate.parse(parts[0].trim());
                LocalDate to = parts.length > 1 ? LocalDate.parse(parts[1].trim()) : from;
                if (parts.length > 2 || from.isAfter(to)) {
                    throw new DateTimeParseException("bad range", text, 0);
                }
                found = manager.searchByDateRange(from, to);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this,
                        "Enter one date (yyyy-mm-dd) or a range like 2026-11-01 to 2026-11-20.",
                        "Check your input", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
        showEntries(found);
        if (found.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No entries found.");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        topPNL = new javax.swing.JPanel();
        navbar1 = new goal12.Navbar();
        headPNL = new javax.swing.JPanel();
        titleJL = new javax.swing.JLabel();
        headBTNPNL = new javax.swing.JPanel();
        logNavBTN = new javax.swing.JButton();
        benchmarkNavBTN = new javax.swing.JButton();
        bodyPNL = new javax.swing.JPanel();
        formPNL = new javax.swing.JPanel();
        categoryJL = new javax.swing.JLabel();
        categoryCB = new javax.swing.JComboBox<>();
        itemTypeJL = new javax.swing.JLabel();
        itemTypeCB = new javax.swing.JComboBox<>();
        statusJL = new javax.swing.JLabel();
        statusPNL = new javax.swing.JPanel();
        newRB = new javax.swing.JRadioButton();
        secondHandRB = new javax.swing.JRadioButton();
        discardedRB = new javax.swing.JRadioButton();
        weightLB = new javax.swing.JLabel();
        weightTF = new javax.swing.JTextField();
        dateLB = new javax.swing.JLabel();
        dateTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        addBTN = new javax.swing.JButton();
        tablePNL = new javax.swing.JPanel();
        searchPNL = new javax.swing.JPanel();
        searchLB = new javax.swing.JLabel();
        searchByCB = new javax.swing.JComboBox<>();
        searchTF = new javax.swing.JTextField();
        searchBTN = new javax.swing.JButton();
        showAllBTN = new javax.swing.JButton();
        scrollPN = new javax.swing.JScrollPane();
        logTBL = new javax.swing.JTable();
        bottomPNL = new javax.swing.JPanel();
        totalsJL = new javax.swing.JLabel();
        deleteBTN = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        topPNL.setLayout(new java.awt.BorderLayout());
        topPNL.add(navbar1, java.awt.BorderLayout.NORTH);

        headPNL.setBackground(new java.awt.Color(0, 102, 0));
        headPNL.setLayout(new java.awt.BorderLayout());

        titleJL.setBackground(new java.awt.Color(255, 255, 255));
        titleJL.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        titleJL.setForeground(new java.awt.Color(255, 255, 255));
        titleJL.setText("TRACK | LOG ITEMS");
        headPNL.add(titleJL, java.awt.BorderLayout.WEST);

        headBTNPNL.setBackground(new java.awt.Color(0, 102, 0));
        headBTNPNL.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        logNavBTN.setText("log items");
        logNavBTN.addActionListener(this::logNavBTNActionPerformed);
        headBTNPNL.add(logNavBTN);

        benchmarkNavBTN.setText("household benchmark");
        benchmarkNavBTN.addActionListener(this::benchmarkNavBTNActionPerformed);
        headBTNPNL.add(benchmarkNavBTN);

        headPNL.add(headBTNPNL, java.awt.BorderLayout.EAST);

        topPNL.add(headPNL, java.awt.BorderLayout.CENTER);

        getContentPane().add(topPNL, java.awt.BorderLayout.NORTH);

        bodyPNL.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 15, 15));
        bodyPNL.setLayout(new java.awt.BorderLayout());

        formPNL.setLayout(new java.awt.GridLayout(6, 2, 10, 8));

        categoryJL.setText("category");
        formPNL.add(categoryJL);

        categoryCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "food", "electronics", "clothing", "household" }));
        categoryCB.addActionListener(this::categoryCBActionPerformed);
        formPNL.add(categoryCB);

        itemTypeJL.setText("item type");
        formPNL.add(itemTypeJL);

        itemTypeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "produce", "meat/dairy", "bakery", "packaged goods" }));
        formPNL.add(itemTypeCB);

        statusJL.setText("status");
        formPNL.add(statusJL);

        newRB.setText("new");
        newRB.addActionListener(this::newRBActionPerformed);
        statusPNL.add(newRB);

        secondHandRB.setText("second hand");
        secondHandRB.addActionListener(this::secondHandRBActionPerformed);
        statusPNL.add(secondHandRB);

        discardedRB.setText("discarded");
        statusPNL.add(discardedRB);

        formPNL.add(statusPNL);

        weightLB.setText("weight");
        formPNL.add(weightLB);

        weightTF.addActionListener(this::weightTFActionPerformed);
        formPNL.add(weightTF);

        dateLB.setText("date");
        formPNL.add(dateLB);

        dateTF.addActionListener(this::dateTFActionPerformed);
        formPNL.add(dateTF);
        formPNL.add(jLabel1);

        addBTN.setText("add");
        formPNL.add(addBTN);

        bodyPNL.add(formPNL, java.awt.BorderLayout.NORTH);

        tablePNL.setLayout(new java.awt.BorderLayout());

        searchLB.setText("search");
        searchPNL.add(searchLB);

        searchByCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "category", "date" }));
        searchPNL.add(searchByCB);

        searchTF.setColumns(15);
        searchPNL.add(searchTF);

        searchBTN.setText("search");
        searchPNL.add(searchBTN);

        showAllBTN.setText("show all");
        searchPNL.add(showAllBTN);

        tablePNL.add(searchPNL, java.awt.BorderLayout.NORTH);

        logTBL.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "category", "item type", "status", "date", "weight"
            }
        ));
        scrollPN.setViewportView(logTBL);

        tablePNL.add(scrollPN, java.awt.BorderLayout.CENTER);

        bottomPNL.setLayout(new java.awt.BorderLayout());

        totalsJL.setText("Footprint: 0.0 kg | Waste: 0.0 kg | Circularity: 0%");
        bottomPNL.add(totalsJL, java.awt.BorderLayout.WEST);

        deleteBTN.setText("delete");
        deleteBTN.addActionListener(this::deleteBTNActionPerformed);
        bottomPNL.add(deleteBTN, java.awt.BorderLayout.EAST);

        tablePNL.add(bottomPNL, java.awt.BorderLayout.SOUTH);

        bodyPNL.add(tablePNL, java.awt.BorderLayout.CENTER);

        getContentPane().add(bodyPNL, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void weightTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weightTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weightTFActionPerformed

    private void categoryCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryCBActionPerformed

    private void newRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newRBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newRBActionPerformed

    private void secondHandRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_secondHandRBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_secondHandRBActionPerformed

    private void deleteBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBTNActionPerformed
        // TODO add your handling code here:
        int row = logTBL.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table to delete.");
            return;
        }
        int id = (Integer) tableModel.getValueAt(row, 0);
        int sure = JOptionPane.showConfirmDialog(this, "Delete entry " + id + "?",
                "Confirm delete", JOptionPane.YES_NO_OPTION);
        if (sure == JOptionPane.YES_OPTION) {
            manager.deleteEntry(id);
            manager.saveToFile();
            showEntries(manager.getEntries());
        }
    }//GEN-LAST:event_deleteBTNActionPerformed

    private void dateTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTFActionPerformed

    private void logNavBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logNavBTNActionPerformed
        // TODO add your handling code here:
        new LogGUI().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_logNavBTNActionPerformed

    private void benchmarkNavBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_benchmarkNavBTNActionPerformed
        // TODO add your handling code here:
        new BenchmarkGUI().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_benchmarkNavBTNActionPerformed

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
        //Create and display the form 
        java.awt.EventQueue.invokeLater(() -> new LogGUI().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBTN;
    private javax.swing.JButton benchmarkNavBTN;
    private javax.swing.JPanel bodyPNL;
    private javax.swing.JPanel bottomPNL;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> categoryCB;
    private javax.swing.JLabel categoryJL;
    private javax.swing.JLabel dateLB;
    private javax.swing.JTextField dateTF;
    private javax.swing.JButton deleteBTN;
    private javax.swing.JRadioButton discardedRB;
    private javax.swing.JPanel formPNL;
    private javax.swing.JPanel headBTNPNL;
    private javax.swing.JPanel headPNL;
    private javax.swing.JComboBox<String> itemTypeCB;
    private javax.swing.JLabel itemTypeJL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton logNavBTN;
    private javax.swing.JTable logTBL;
    private goal12.Navbar navbar1;
    private javax.swing.JRadioButton newRB;
    private javax.swing.JScrollPane scrollPN;
    private javax.swing.JButton searchBTN;
    private javax.swing.JComboBox<String> searchByCB;
    private javax.swing.JLabel searchLB;
    private javax.swing.JPanel searchPNL;
    private javax.swing.JTextField searchTF;
    private javax.swing.JRadioButton secondHandRB;
    private javax.swing.JButton showAllBTN;
    private javax.swing.JLabel statusJL;
    private javax.swing.JPanel statusPNL;
    private javax.swing.JPanel tablePNL;
    private javax.swing.JLabel titleJL;
    private javax.swing.JPanel topPNL;
    private javax.swing.JLabel totalsJL;
    private javax.swing.JLabel weightLB;
    private javax.swing.JTextField weightTF;
    // End of variables declaration//GEN-END:variables
}
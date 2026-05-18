# TASK 5 — InvoiceManagement.java Complete Logic
# Project: SmeTradeCreditFinancingSystemClient28028
# Package: rw.rab.view
# NEVER touch initComponents()
# Component names: searchInvoiceTxt, searchInvoiceBtn, statusCombo,
#                  verifyBtn, markAsFundedBtn, markAsRepaidBtn, deleteBtn, invoiceTable

========================================================
STEP 1: Add these fields at the top of the class
========================================================

private rw.rab.service.InvoiceService invoiceService;

========================================================
STEP 2: Replace existing constructor with this
========================================================

public InvoiceManagement() {
    initComponents();
    setLocationRelativeTo(null);
    connectToServer();
    loadAllInvoices();
}

========================================================
STEP 3: Add these methods outside initComponents()
========================================================

private void connectToServer() {
    try {
        java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry("127.0.0.1", 3000);
        invoiceService = (rw.rab.service.InvoiceService) registry.lookup("invoice");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Cannot connect to server: " + e.getMessage(),
            "Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void loadAllInvoices() {
    try {
        java.util.List<rw.rab.model.Invoice> invoices = invoiceService.getAllInvoices();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "SME", "Amount", "Issue Date", "Due Date", "Status"}, 0
        );
        for (int i = 0; i < invoices.size(); i++) {
            rw.rab.model.Invoice inv = invoices.get(i);
            model.addRow(new Object[]{
                inv.getInvoiceId(),
                inv.getInvoiceNumber(),
                inv.getSme() != null ? inv.getSme().getBusinessName() : "",
                inv.getAmount(),
                inv.getIssueDate(),
                inv.getDueDate(),
                inv.getStatus()
            });
        }
        invoiceTable.setModel(model);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error loading invoices: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

========================================================
STEP 4: Add button actionPerformed methods
========================================================

// verifyBtn actionPerformed:
private void verifyBtnActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        int selectedRow = invoiceTable.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select an invoice from the table first",
                "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentStatus = invoiceTable.getValueAt(selectedRow, 6).toString();

        // Business rule: only SUBMITTED invoices can be verified
        if (!currentStatus.equals("SUBMITTED")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Only SUBMITTED invoices can be verified. Current status: " + currentStatus,
                "Business Rule Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        int invoiceId = (int) invoiceTable.getValueAt(selectedRow, 0);
        rw.rab.model.Invoice invoice = new rw.rab.model.Invoice();
        invoice.setInvoiceId(invoiceId);
        invoice.setStatus("VERIFIED");

        invoiceService.updateInvoice(invoice);
        javax.swing.JOptionPane.showMessageDialog(this,
            "Invoice verified successfully",
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        loadAllInvoices();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// markAsFundedBtn actionPerformed:
private void markAsFundedBtnActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        int selectedRow = invoiceTable.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select an invoice from the table first",
                "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentStatus = invoiceTable.getValueAt(selectedRow, 6).toString();

        // Business rule: only VERIFIED invoices can be marked as funded
        if (!currentStatus.equals("VERIFIED")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Only VERIFIED invoices can be marked as funded. Current status: " + currentStatus,
                "Business Rule Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        int invoiceId = (int) invoiceTable.getValueAt(selectedRow, 0);
        rw.rab.model.Invoice invoice = new rw.rab.model.Invoice();
        invoice.setInvoiceId(invoiceId);
        invoice.setStatus("FUNDED");

        invoiceService.updateInvoice(invoice);
        javax.swing.JOptionPane.showMessageDialog(this,
            "Invoice marked as funded successfully",
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        loadAllInvoices();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// markAsRepaidBtn actionPerformed:
private void markAsRepaidBtnActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        int selectedRow = invoiceTable.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select an invoice from the table first",
                "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        String currentStatus = invoiceTable.getValueAt(selectedRow, 6).toString();

        // Business rule: only FUNDED invoices can be marked as repaid
        if (!currentStatus.equals("FUNDED")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Only FUNDED invoices can be marked as repaid. Current status: " + currentStatus,
                "Business Rule Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        int invoiceId = (int) invoiceTable.getValueAt(selectedRow, 0);
        rw.rab.model.Invoice invoice = new rw.rab.model.Invoice();
        invoice.setInvoiceId(invoiceId);
        invoice.setStatus("REPAID");

        invoiceService.updateInvoice(invoice);
        javax.swing.JOptionPane.showMessageDialog(this,
            "Invoice marked as repaid successfully",
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        loadAllInvoices();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// deleteBtn actionPerformed:
private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        int selectedRow = invoiceTable.getSelectedRow();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select an invoice to delete",
                "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this invoice?",
            "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

        int invoiceId = (int) invoiceTable.getValueAt(selectedRow, 0);
        rw.rab.model.Invoice invoice = new rw.rab.model.Invoice();
        invoice.setInvoiceId(invoiceId);

        invoiceService.deleteInvoice(invoice);
        javax.swing.JOptionPane.showMessageDialog(this,
            "Invoice deleted successfully",
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        loadAllInvoices();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// searchInvoiceBtn actionPerformed:
private void searchInvoiceBtnActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        String searchTerm = searchInvoiceTxt.getText().trim().toLowerCase();
        if (searchTerm.isEmpty()) {
            loadAllInvoices();
            return;
        }

        javax.swing.table.DefaultTableModel currentModel =
            (javax.swing.table.DefaultTableModel) invoiceTable.getModel();
        javax.swing.table.DefaultTableModel filteredModel = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "SME", "Amount", "Issue Date", "Due Date", "Status"}, 0
        );

        for (int i = 0; i < currentModel.getRowCount(); i++) {
            String invoiceNum = currentModel.getValueAt(i, 1).toString().toLowerCase();
            if (invoiceNum.contains(searchTerm)) {
                filteredModel.addRow(new Object[]{
                    currentModel.getValueAt(i, 0),
                    currentModel.getValueAt(i, 1),
                    currentModel.getValueAt(i, 2),
                    currentModel.getValueAt(i, 3),
                    currentModel.getValueAt(i, 4),
                    currentModel.getValueAt(i, 5),
                    currentModel.getValueAt(i, 6)
                });
            }
        }
        invoiceTable.setModel(filteredModel);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// statusCombo actionPerformed:
private void statusComboActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        String selected = statusCombo.getSelectedItem().toString();
        if (selected.equals("All Status")) {
            loadAllInvoices();
            return;
        }

        java.util.List<rw.rab.model.Invoice> invoices = invoiceService.getAllInvoices();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "SME", "Amount", "Issue Date", "Due Date", "Status"}, 0
        );
        for (int i = 0; i < invoices.size(); i++) {
            rw.rab.model.Invoice inv = invoices.get(i);
            if (inv.getStatus().equals(selected)) {
                model.addRow(new Object[]{
                    inv.getInvoiceId(),
                    inv.getInvoiceNumber(),
                    inv.getSme() != null ? inv.getSme().getBusinessName() : "",
                    inv.getAmount(),
                    inv.getIssueDate(),
                    inv.getDueDate(),
                    inv.getStatus()
                });
            }
        }
        invoiceTable.setModel(model);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

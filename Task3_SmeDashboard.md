# TASK 3 — SmeDashboard.java Complete Logic
# Project: SmeTradeCreditFinancingSystemClient28028
# Package: rw.rab.view
# NEVER touch initComponents()

========================================================
STEP 1: Add these fields at the top of the class
========================================================

private rw.rab.model.User loggedInUser;
private rw.rab.service.InvoiceService invoiceService;
private rw.rab.service.SmeService smeService;

========================================================
STEP 2: Replace existing constructor with this
========================================================

public SmeDashboard(rw.rab.model.User user) {
    initComponents();
    this.loggedInUser = user;
    setLocationRelativeTo(null);
    connectToServer();
    loadDashboardStats();
}

========================================================
STEP 3: Add these methods outside initComponents()
========================================================

private void connectToServer() {
    try {
        java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry("127.0.0.1", 3000);
        invoiceService = (rw.rab.service.InvoiceService) registry.lookup("invoice");
        smeService = (rw.rab.service.SmeService) registry.lookup("sme");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Cannot connect to server: " + e.getMessage(),
            "Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void loadDashboardStats() {
    try {
        java.util.List<rw.rab.model.Invoice> allInvoices = invoiceService.getAllInvoices();

        // Filter invoices belonging to this SME user
        java.util.List<rw.rab.model.Invoice> myInvoices = new java.util.ArrayList<rw.rab.model.Invoice>();
        for (int i = 0; i < allInvoices.size(); i++) {
            rw.rab.model.Invoice inv = allInvoices.get(i);
            if (inv.getSme() != null &&
                inv.getSme().getUser() != null &&
                inv.getSme().getUser().getUserId() == loggedInUser.getUserId()) {
                myInvoices.add(inv);
            }
        }

        // Count by status
        int submitted = 0, verified = 0, funded = 0, repaid = 0;
        for (int i = 0; i < myInvoices.size(); i++) {
            String status = myInvoices.get(i).getStatus();
            if (status.equals("SUBMITTED")) submitted++;
            else if (status.equals("VERIFIED")) verified++;
            else if (status.equals("FUNDED")) funded++;
            else if (status.equals("REPAID")) repaid++;
        }

        // Set stat labels
        submittedCount.setText(String.valueOf(submitted));
        verifiedCount.setText(String.valueOf(verified));
        fundedCount.setText(String.valueOf(funded));
        repaidCount.setText(String.valueOf(repaid));

        // Load invoices into table
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "Amount", "Due Date", "Status"}, 0
        );
        for (int i = 0; i < myInvoices.size(); i++) {
            rw.rab.model.Invoice inv = myInvoices.get(i);
            model.addRow(new Object[]{
                inv.getInvoiceId(),
                inv.getInvoiceNumber(),
                inv.getAmount(),
                inv.getDueDate(),
                inv.getStatus()
            });
        }
        invoicesTable.setModel(model);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error loading stats: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

========================================================
STEP 4: Add sidebar button actionPerformed methods
========================================================

// dashboardBtn actionPerformed:
private void dashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {
    loadDashboardStats();
}

// myInvoiceBtn actionPerformed:
private void myInvoiceBtnActionPerformed(java.awt.event.ActionEvent evt) {
    new MyInvoices(loggedInUser).setVisible(true);
}

// exportReportBtn actionPerformed:
private void exportReportBtnActionPerformed(java.awt.event.ActionEvent evt) {
    new ReportsPage(loggedInUser).setVisible(true);
}

========================================================
STEP 5: Fix main() method
========================================================

new SmeDashboard(new rw.rab.model.User()).setVisible(true);

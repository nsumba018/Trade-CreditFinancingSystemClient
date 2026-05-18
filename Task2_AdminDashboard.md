# TASK 2 — AdminDashboard.java Complete Logic
# Project: SmeTradeCreditFinancingSystemClient28028
# Package: rw.rab.view
# NEVER touch initComponents()
# Paste these methods into AdminDashboard.java OUTSIDE initComponents()

========================================================
STEP 1: Add these fields at the top of the class
(right after: public class AdminDashboard extends javax.swing.JFrame {)
========================================================

private rw.rab.model.User loggedInUser;
private rw.rab.service.SmeService smeService;
private rw.rab.service.InvestorService investorService;
private rw.rab.service.InvoiceService invoiceService;
private rw.rab.service.FundingService fundingService;

========================================================
STEP 2: Replace the existing constructor with this
========================================================

public AdminDashboard(rw.rab.model.User user) {
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
        smeService = (rw.rab.service.SmeService) registry.lookup("sme");
        investorService = (rw.rab.service.InvestorService) registry.lookup("investor");
        invoiceService = (rw.rab.service.InvoiceService) registry.lookup("invoice");
        fundingService = (rw.rab.service.FundingService) registry.lookup("funding");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Cannot connect to server: " + e.getMessage(),
            "Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void loadDashboardStats() {
    try {
        // Load SME count
        java.util.List<rw.rab.model.Sme> smes = smeService.getAllSmes();
        smeCount.setText(String.valueOf(smes.size()));

        // Load Investor count
        java.util.List<rw.rab.model.Investor> investors = investorService.getAllInvestors();
        investorsCount.setText(String.valueOf(investors.size()));

        // Load Invoice count
        java.util.List<rw.rab.model.Invoice> invoices = invoiceService.getAllInvoices();
        invoicesCount.setText(String.valueOf(invoices.size()));

        // Load Funding count
        java.util.List<rw.rab.model.Funding> fundings = fundingService.getAllFundings();
        fundingCount.setText(String.valueOf(fundings.size()));

        // Load recent invoices into table
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "SME", "Amount", "Status"}, 0
        );
        for (int i = 0; i < invoices.size(); i++) {
            rw.rab.model.Invoice inv = invoices.get(i);
            model.addRow(new Object[]{
                inv.getInvoiceId(),
                inv.getInvoiceNumber(),
                inv.getSme() != null ? inv.getSme().getBusinessName() : "",
                inv.getAmount(),
                inv.getStatus()
            });
        }
        recentInvoicesTable.setModel(model);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error loading stats: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

========================================================
STEP 4: Add these inside each sidebar button actionPerformed
========================================================

// dashboardBtn actionPerformed:
private void dashboardBtnActionPerformed(java.awt.event.ActionEvent evt) {
    loadDashboardStats();
}

// manageUsers actionPerformed:
private void manageUsersActionPerformed(java.awt.event.ActionEvent evt) {
    new UserManagement().setVisible(true);
}

// manageInvoiceBtn actionPerformed:
private void manageInvoiceBtnActionPerformed(java.awt.event.ActionEvent evt) {
    new InvoiceManagement().setVisible(true);
}

// generateReportBtn actionPerformed:
private void generateReportBtnActionPerformed(java.awt.event.ActionEvent evt) {
    new ReportsPage(loggedInUser).setVisible(true);
}

========================================================
STEP 5: Fix the main() method — replace the auto-generated line with:
========================================================

new AdminDashboard(new rw.rab.model.User()).setVisible(true);

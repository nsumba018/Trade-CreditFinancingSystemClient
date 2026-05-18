# TASK 4 — InvestorDashboard.java Complete Logic
# Project: SmeTradeCreditFinancingSystemClient28028
# Package: rw.rab.view
# NEVER touch initComponents()

========================================================
STEP 1: Add these fields at the top of the class
========================================================

private rw.rab.model.User loggedInUser;
private rw.rab.service.InvoiceService invoiceService;
private rw.rab.service.InvestorService investorService;
private rw.rab.service.FundingService fundingService;

========================================================
STEP 2: Replace existing constructor with this
========================================================

public InvestorDashboard(rw.rab.model.User user) {
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
        investorService = (rw.rab.service.InvestorService) registry.lookup("investor");
        fundingService = (rw.rab.service.FundingService) registry.lookup("funding");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Cannot connect to server: " + e.getMessage(),
            "Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void loadDashboardStats() {
    try {
        // Get investor profile
        rw.rab.model.Investor investor = investorService.getInvestorByUserId(loggedInUser);

        if (investor != null) {
            // Available balance
            balanceCount.setText("RWF " + investor.getAvailableBalance());

            // Get fundings by this investor
            java.util.List<rw.rab.model.Funding> fundings = fundingService.getFundingsByInvestorId(investor);

            // Calculate total invested
            double totalInvested = 0;
            for (int i = 0; i < fundings.size(); i++) {
                totalInvested += fundings.get(i).getFundedAmount();
            }
            investedCount.setText("RWF " + totalInvested);

            // Active fundings count
            activeFunding.setText(String.valueOf(fundings.size()));
        }

        // Load VERIFIED invoices into available invoices table
        java.util.List<rw.rab.model.Invoice> allInvoices = invoiceService.getAllInvoices();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "SME", "Amount", "Due Date"}, 0
        );
        for (int i = 0; i < allInvoices.size(); i++) {
            rw.rab.model.Invoice inv = allInvoices.get(i);
            if (inv.getStatus().equals("VERIFIED")) {
                model.addRow(new Object[]{
                    inv.getInvoiceId(),
                    inv.getInvoiceNumber(),
                    inv.getSme() != null ? inv.getSme().getBusinessName() : "",
                    inv.getAmount(),
                    inv.getDueDate()
                });
            }
        }
        availableInvoiceTable.setModel(model);

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

// invoiceBtn actionPerformed:
private void invoiceBtnActionPerformed(java.awt.event.ActionEvent evt) {
    new FundInvoice(loggedInUser).setVisible(true);
}

// exportInvoicesBtn actionPerformed:
private void exportInvoicesBtnActionPerformed(java.awt.event.ActionEvent evt) {
    new ReportsPage(loggedInUser).setVisible(true);
}

========================================================
STEP 5: Fix main() method
========================================================

new InvestorDashboard(new rw.rab.model.User()).setVisible(true);

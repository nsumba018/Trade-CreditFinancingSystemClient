# TASK 6 — FundInvoice.java Complete Logic
# Project: SmeTradeCreditFinancingSystemClient28028
# Package: rw.rab.view
# NEVER touch initComponents()
# Component names: availableInvoiceTable, selectedInvoiceField, amountField,
#                  confirmFundingBtn, myHistroyTable

========================================================
STEP 1: Add these fields at the top of the class
========================================================

private rw.rab.model.User loggedInUser;
private rw.rab.service.InvoiceService invoiceService;
private rw.rab.service.InvestorService investorService;
private rw.rab.service.FundingService fundingService;
private int selectedInvoiceId = -1;

========================================================
STEP 2: Replace existing constructor with this
========================================================

public FundInvoice(rw.rab.model.User user) {
    initComponents();
    this.loggedInUser = user;
    setLocationRelativeTo(null);
    connectToServer();
    loadVerifiedInvoices();
    loadMyFundings();
    setupTableClickListener();
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

private void loadVerifiedInvoices() {
    try {
        java.util.List<rw.rab.model.Invoice> allInvoices = invoiceService.getAllInvoices();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "SME", "Amount", "Due Date", "Status"}, 0
        );
        for (int i = 0; i < allInvoices.size(); i++) {
            rw.rab.model.Invoice inv = allInvoices.get(i);
            if (inv.getStatus().equals("VERIFIED")) {
                model.addRow(new Object[]{
                    inv.getInvoiceId(),
                    inv.getInvoiceNumber(),
                    inv.getSme() != null ? inv.getSme().getBusinessName() : "",
                    inv.getAmount(),
                    inv.getDueDate(),
                    inv.getStatus()
                });
            }
        }
        availableInvoiceTable.setModel(model);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error loading invoices: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void loadMyFundings() {
    try {
        rw.rab.model.Investor investor = investorService.getInvestorByUserId(loggedInUser);
        if (investor == null) return;

        java.util.List<rw.rab.model.Funding> fundings = fundingService.getFundingsByInvestorId(investor);
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"ID", "Invoice #", "Amount Funded", "Date"}, 0
        );
        for (int i = 0; i < fundings.size(); i++) {
            rw.rab.model.Funding f = fundings.get(i);
            model.addRow(new Object[]{
                f.getFundingId(),
                f.getInvoice() != null ? f.getInvoice().getInvoiceNumber() : "",
                f.getFundedAmount(),
                f.getFundedDate()
            });
        }
        myHistroyTable.setModel(model);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error loading fundings: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void setupTableClickListener() {
    availableInvoiceTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = availableInvoiceTable.getSelectedRow();
            if (row != -1) {
                selectedInvoiceId = (int) availableInvoiceTable.getValueAt(row, 0);
                selectedInvoiceField.setText(
                    availableInvoiceTable.getValueAt(row, 1).toString()
                );
            }
        }
    });
}

========================================================
STEP 4: Add confirmFundingBtn actionPerformed
========================================================

// confirmFundingBtn actionPerformed:
private void confirmFundingBtnActionPerformed(java.awt.event.ActionEvent evt) {
    try {
        // Technical rule 1: must select an invoice
        if (selectedInvoiceId == -1 || selectedInvoiceField.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please select an invoice from the table first",
                "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Technical rule 2: amount field not empty
        if (amountField.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Please enter the amount to fund",
                "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Technical rule 3: amount must be numeric
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Amount must be a valid number",
                "Validation Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Business rule 1: amount must be positive
        if (amount <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Funding amount must be greater than zero",
                "Business Rule Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get investor
        rw.rab.model.Investor investor = investorService.getInvestorByUserId(loggedInUser);
        if (investor == null) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "No investor profile found for this account. Contact admin.",
                "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Business rule 2: amount must not exceed available balance
        if (amount > investor.getAvailableBalance()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Funding amount exceeds your available balance of RWF " + investor.getAvailableBalance(),
                "Business Rule Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get selected invoice
        rw.rab.model.Invoice invoice = new rw.rab.model.Invoice();
        invoice.setInvoiceId(selectedInvoiceId);
        rw.rab.model.Invoice fullInvoice = invoiceService.getInvoiceById(invoice);

        // Business rule 3: invoice must still be VERIFIED
        if (!fullInvoice.getStatus().equals("VERIFIED")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "This invoice is no longer available for funding. Status: " + fullInvoice.getStatus(),
                "Business Rule Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create funding record
        rw.rab.model.Funding funding = new rw.rab.model.Funding();
        funding.setFundedAmount(amount);
        funding.setFundedDate(new java.util.Date());
        funding.setInvoice(fullInvoice);
        funding.setInvestor(investor);

        String result = fundingService.createFunding(funding);

        // Update investor available balance
        investor.setAvailableBalance(investor.getAvailableBalance() - amount);
        investorService.updateInvestor(investor);

        javax.swing.JOptionPane.showMessageDialog(this,
            "Funding confirmed successfully! RWF " + amount + " invested.",
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Reset fields
        selectedInvoiceField.setText("");
        amountField.setText("");
        selectedInvoiceId = -1;

        // Reload both tables
        loadVerifiedInvoices();
        loadMyFundings();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

========================================================
STEP 5: Fix main() method
========================================================

new FundInvoice(new rw.rab.model.User()).setVisible(true);

========================================================
ALSO: Make sure client Funding.java has getInvoice() and getInvestor() methods.
Make sure InvestorService on client has updateInvestor method.
========================================================

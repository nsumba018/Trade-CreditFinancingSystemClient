# TASK 7 — ReportsPage.java Complete Logic
# Project: SmeTradeCreditFinancingSystemClient28028
# Package: rw.rab.view
# NEVER touch initComponents()
# Required JAR: itextpdf-5.5.13.jar (add to both projects)
# Component names: invoiceExportPdfBtn, invoiceExportCsvBtn,
#                  fundingExportPdfBtn, fundingExportCsvBtn,
#                  usersExportPdfBtn, usersExportCsvBtn, invoiceTable

========================================================
STEP 1: Add these fields at the top of the class
========================================================

private rw.rab.model.User loggedInUser;
private rw.rab.service.InvoiceService invoiceService;
private rw.rab.service.FundingService fundingService;
private rw.rab.service.UserService userService;

========================================================
STEP 2: Replace existing constructor with this
========================================================

public ReportsPage(rw.rab.model.User user) {
    initComponents();
    this.loggedInUser = user;
    setLocationRelativeTo(null);
    connectToServer();
    loadInvoicePreview();
}

========================================================
STEP 3: Add these methods outside initComponents()
========================================================

private void connectToServer() {
    try {
        java.rmi.registry.Registry registry = java.rmi.registry.LocateRegistry.getRegistry("127.0.0.1", 3000);
        invoiceService = (rw.rab.service.InvoiceService) registry.lookup("invoice");
        fundingService = (rw.rab.service.FundingService) registry.lookup("funding");
        userService = (rw.rab.service.UserService) registry.lookup("user");
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Cannot connect to server: " + e.getMessage(),
            "Connection Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

private void loadInvoicePreview() {
    try {
        java.util.List<rw.rab.model.Invoice> invoices = invoiceService.getAllInvoices();
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(
            new String[]{"Invoice #", "SME", "Amount", "Issue Date", "Status"}, 0
        );
        for (int i = 0; i < invoices.size(); i++) {
            rw.rab.model.Invoice inv = invoices.get(i);
            model.addRow(new Object[]{
                inv.getInvoiceNumber(),
                inv.getSme() != null ? inv.getSme().getBusinessName() : "",
                inv.getAmount(),
                inv.getIssueDate(),
                inv.getStatus()
            });
        }
        invoiceTable.setModel(model);
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error loading preview: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// ===== INVOICE PDF EXPORT =====
private void exportInvoicesToPdf() {
    try {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("InvoiceReport.pdf"));
        int result = fileChooser.showSaveDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".pdf")) path += ".pdf";

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(
            document, new java.io.FileOutputStream(path));
        document.open();

        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 16,
            com.itextpdf.text.Font.BOLD);
        document.add(new com.itextpdf.text.Paragraph(
            "SME Trade Credit Financing System", titleFont));
        document.add(new com.itextpdf.text.Paragraph("Invoice Report"));
        document.add(new com.itextpdf.text.Paragraph(
            "Generated: " + new java.util.Date().toString()));
        document.add(com.itextpdf.text.Chunk.NEWLINE);

        com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("Invoice #");
        table.addCell("SME");
        table.addCell("Amount");
        table.addCell("Issue Date");
        table.addCell("Status");

        java.util.List<rw.rab.model.Invoice> invoices = invoiceService.getAllInvoices();
        for (int i = 0; i < invoices.size(); i++) {
            rw.rab.model.Invoice inv = invoices.get(i);
            table.addCell(inv.getInvoiceNumber() != null ? inv.getInvoiceNumber() : "");
            table.addCell(inv.getSme() != null ? inv.getSme().getBusinessName() : "");
            table.addCell(String.valueOf(inv.getAmount()));
            table.addCell(inv.getIssueDate() != null ? inv.getIssueDate().toString() : "");
            table.addCell(inv.getStatus() != null ? inv.getStatus() : "");
        }

        document.add(table);
        document.close();

        javax.swing.JOptionPane.showMessageDialog(this,
            "Invoice PDF exported successfully to:\n" + path,
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error exporting PDF: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// ===== INVOICE CSV EXPORT =====
private void exportInvoicesToCsv() {
    try {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("InvoiceReport.csv"));
        int result = fileChooser.showSaveDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".csv")) path += ".csv";

        java.util.List<rw.rab.model.Invoice> invoices = invoiceService.getAllInvoices();
        java.io.FileWriter fw = new java.io.FileWriter(path);
        fw.write("Invoice#,SME,Amount,Issue Date,Due Date,Status\n");

        for (int i = 0; i < invoices.size(); i++) {
            rw.rab.model.Invoice inv = invoices.get(i);
            fw.write(
                (inv.getInvoiceNumber() != null ? inv.getInvoiceNumber() : "") + "," +
                (inv.getSme() != null ? inv.getSme().getBusinessName() : "") + "," +
                inv.getAmount() + "," +
                (inv.getIssueDate() != null ? inv.getIssueDate().toString() : "") + "," +
                (inv.getDueDate() != null ? inv.getDueDate().toString() : "") + "," +
                (inv.getStatus() != null ? inv.getStatus() : "") + "\n"
            );
        }
        fw.close();

        javax.swing.JOptionPane.showMessageDialog(this,
            "Invoice CSV exported successfully to:\n" + path,
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error exporting CSV: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// ===== FUNDING PDF EXPORT =====
private void exportFundingsToPdf() {
    try {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("FundingReport.pdf"));
        int result = fileChooser.showSaveDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".pdf")) path += ".pdf";

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(
            document, new java.io.FileOutputStream(path));
        document.open();

        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 16,
            com.itextpdf.text.Font.BOLD);
        document.add(new com.itextpdf.text.Paragraph(
            "SME Trade Credit Financing System", titleFont));
        document.add(new com.itextpdf.text.Paragraph("Funding Report"));
        document.add(new com.itextpdf.text.Paragraph(
            "Generated: " + new java.util.Date().toString()));
        document.add(com.itextpdf.text.Chunk.NEWLINE);

        com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell("Funding ID");
        table.addCell("Invoice #");
        table.addCell("Amount Funded");
        table.addCell("Date");

        java.util.List<rw.rab.model.Funding> fundings = fundingService.getAllFundings();
        for (int i = 0; i < fundings.size(); i++) {
            rw.rab.model.Funding f = fundings.get(i);
            table.addCell(String.valueOf(f.getFundingId()));
            table.addCell(f.getInvoice() != null ? f.getInvoice().getInvoiceNumber() : "");
            table.addCell(String.valueOf(f.getFundedAmount()));
            table.addCell(f.getFundedDate() != null ? f.getFundedDate().toString() : "");
        }

        document.add(table);
        document.close();

        javax.swing.JOptionPane.showMessageDialog(this,
            "Funding PDF exported successfully to:\n" + path,
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error exporting PDF: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// ===== FUNDING CSV EXPORT =====
private void exportFundingsToCsv() {
    try {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("FundingReport.csv"));
        int result = fileChooser.showSaveDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".csv")) path += ".csv";

        java.util.List<rw.rab.model.Funding> fundings = fundingService.getAllFundings();
        java.io.FileWriter fw = new java.io.FileWriter(path);
        fw.write("Funding ID,Invoice #,Amount Funded,Date\n");

        for (int i = 0; i < fundings.size(); i++) {
            rw.rab.model.Funding f = fundings.get(i);
            fw.write(
                f.getFundingId() + "," +
                (f.getInvoice() != null ? f.getInvoice().getInvoiceNumber() : "") + "," +
                f.getFundedAmount() + "," +
                (f.getFundedDate() != null ? f.getFundedDate().toString() : "") + "\n"
            );
        }
        fw.close();

        javax.swing.JOptionPane.showMessageDialog(this,
            "Funding CSV exported successfully to:\n" + path,
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error exporting CSV: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// ===== USERS PDF EXPORT =====
private void exportUsersToPdf() {
    try {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("UsersReport.pdf"));
        int result = fileChooser.showSaveDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".pdf")) path += ".pdf";

        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        com.itextpdf.text.pdf.PdfWriter.getInstance(
            document, new java.io.FileOutputStream(path));
        document.open();

        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(
            com.itextpdf.text.Font.FontFamily.HELVETICA, 16,
            com.itextpdf.text.Font.BOLD);
        document.add(new com.itextpdf.text.Paragraph(
            "SME Trade Credit Financing System", titleFont));
        document.add(new com.itextpdf.text.Paragraph("Users Report"));
        document.add(new com.itextpdf.text.Paragraph(
            "Generated: " + new java.util.Date().toString()));
        document.add(com.itextpdf.text.Chunk.NEWLINE);

        com.itextpdf.text.pdf.PdfPTable table = new com.itextpdf.text.pdf.PdfPTable(4);
        table.setWidthPercentage(100);
        table.addCell("ID");
        table.addCell("Username");
        table.addCell("Email");
        table.addCell("Role");

        java.util.List<rw.rab.model.User> users = userService.getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            rw.rab.model.User u = users.get(i);
            table.addCell(String.valueOf(u.getUserId()));
            table.addCell(u.getUsername() != null ? u.getUsername() : "");
            table.addCell(u.getEmail() != null ? u.getEmail() : "");
            table.addCell(u.getRole() != null ? u.getRole() : "");
        }

        document.add(table);
        document.close();

        javax.swing.JOptionPane.showMessageDialog(this,
            "Users PDF exported successfully to:\n" + path,
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error exporting PDF: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

// ===== USERS CSV EXPORT =====
private void exportUsersToCsv() {
    try {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("UsersReport.csv"));
        int result = fileChooser.showSaveDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        if (!path.endsWith(".csv")) path += ".csv";

        java.util.List<rw.rab.model.User> users = userService.getAllUsers();
        java.io.FileWriter fw = new java.io.FileWriter(path);
        fw.write("ID,Username,Email,Role\n");

        for (int i = 0; i < users.size(); i++) {
            rw.rab.model.User u = users.get(i);
            fw.write(
                u.getUserId() + "," +
                (u.getUsername() != null ? u.getUsername() : "") + "," +
                (u.getEmail() != null ? u.getEmail() : "") + "," +
                (u.getRole() != null ? u.getRole() : "") + "\n"
            );
        }
        fw.close();

        javax.swing.JOptionPane.showMessageDialog(this,
            "Users CSV exported successfully to:\n" + path,
            "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error exporting CSV: " + e.getMessage(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

========================================================
STEP 4: Add button actionPerformed methods
========================================================

// invoiceExportPdfBtn actionPerformed:
private void invoiceExportPdfBtnActionPerformed(java.awt.event.ActionEvent evt) {
    exportInvoicesToPdf();
}

// invoiceExportCsvBtn actionPerformed:
private void invoiceExportCsvBtnActionPerformed(java.awt.event.ActionEvent evt) {
    exportInvoicesToCsv();
}

// fundingExportPdfBtn actionPerformed:
private void fundingExportPdfBtnActionPerformed(java.awt.event.ActionEvent evt) {
    exportFundingsToPdf();
}

// fundingExportCsvBtn actionPerformed:
private void fundingExportCsvBtnActionPerformed(java.awt.event.ActionEvent evt) {
    exportFundingsToCsv();
}

// usersExportPdfBtn actionPerformed:
private void usersExportPdfBtnActionPerformed(java.awt.event.ActionEvent evt) {
    exportUsersToPdf();
}

// usersExportCsvBtn actionPerformed:
private void usersExportCsvBtnActionPerformed(java.awt.event.ActionEvent evt) {
    exportUsersToCsv();
}

========================================================
STEP 5: Fix main() method
========================================================

new ReportsPage(new rw.rab.model.User()).setVisible(true);

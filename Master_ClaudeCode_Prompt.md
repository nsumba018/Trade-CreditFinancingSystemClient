# MASTER CLAUDE CODE PROMPT
# SME Trade Credit Financing System — Student ID: 28028
# Course: INSY 7312 Java Programming | AUCA

=======================================================
HOW TO USE THESE FILES WITH CLAUDE CODE
=======================================================

You have 8 task files. Use them in this order:

STEP 1: Task1_ServerDAOMethods.md   → Server DAO + Service fixes
STEP 2: Task2_AdminDashboard.md     → Admin dashboard stats + buttons
STEP 3: Task3_SmeDashboard.md       → SME dashboard stats + buttons
STEP 4: Task4_InvestorDashboard.md  → Investor dashboard stats + buttons
STEP 5: Task5_InvoiceManagement.md  → Admin invoice management buttons
STEP 6: Task6_FundInvoice.md        → Investor funding page
STEP 7: Task7_ReportsPage.md        → PDF + CSV export
STEP 8: Task8_ActiveMQ_JavaMail.md  → OTP email sending

=======================================================
PROMPT TO GIVE CLAUDE CODE FOR EACH TASK
=======================================================

Copy and paste this prompt for EACH task, replacing [TASK NUMBER] and [TASK CONTENT]:

---

I am working on a Java NetBeans 8.2 desktop application.

STRICT RULES — follow these or the project breaks:
1. NEVER modify initComponents() in any file
2. NEVER create new JFrame classes
3. NEVER change component variable names
4. Write SIMPLE beginner Java code — no lambdas, no streams, no advanced Java
5. Use simple for loops: for (int i = 0; i < list.size(); i++)
6. Use simple try/catch blocks
7. Do not add new packages — use existing ones only
8. All new methods go OUTSIDE initComponents() and OUTSIDE the variables declaration block

Project structure:
- Server package: rw.rab.dao, rw.rab.service, rw.rab.service.implementation, rw.rab.controller
- Client package: rw.rab.model, rw.rab.service, rw.rab.view

Here is the task to complete:
[PASTE THE FULL CONTENT OF THE TASK FILE HERE]

Here is my current [ClassName].java file:
[PASTE YOUR CURRENT FILE CONTENT HERE]

Write only the code I need to add. Show me exactly where to put each piece of code.
Do not rewrite the whole file — just show the additions.

---

=======================================================
AFTER EACH TASK — DO THIS BEFORE MOVING TO NEXT
=======================================================

1. Paste the code into NetBeans
2. Click Clean and Build on BOTH projects
3. Restart the server
4. Run the client and test the feature
5. Only move to next task if it works

=======================================================
FINAL CHECKLIST — ALL TEACHER REQUIREMENTS
=======================================================

Before submission verify ALL of these:

[ ] Server project named: SmeTradeCreditFinancingSystemsServer28028
[ ] Client project named: SmeTradeCreditFinancingSystemClient28028
[ ] Database named: sme_trade_credit_financing_system_db
[ ] RMI port 3000 (within required 3000-6000 range)
[ ] 6 entities: User, Sme, Investor, Invoice, Funding, Sector
[ ] Hibernate configured with hbm2ddl.auto=update
[ ] DAO classes for all 6 entities
[ ] Service interfaces for all 6 entities
[ ] ServiceImpl classes for all 6 entities
[ ] One-to-One relationship: User-Sme, User-Investor
[ ] One-to-Many relationship: Sme-Invoice, Invoice-Funding, Investor-Funding
[ ] Many-to-Many relationship: Investor-Sector (investor_sector join table)
[ ] CRUD operations working on all main entities
[ ] JTable in every CRUD page
[ ] JOptionPane for success, error, validation messages
[ ] No direct database access from client
[ ] Login page with username + password
[ ] OTP verification page
[ ] OTP sent via JavaMail to real email
[ ] ActiveMQ message broker used for OTP events
[ ] Admin dashboard with stats
[ ] SME dashboard with invoice stats
[ ] Investor dashboard with portfolio stats
[ ] User management page (Admin)
[ ] Invoice management page (Admin — verify/fund/repaid)
[ ] My Invoices page (SME — submit/update/delete)
[ ] Fund Invoice page (Investor — fund verified invoices)
[ ] Reports page with PDF export
[ ] Reports page with CSV export
[ ] 5 business validation rules visible in code
[ ] 5 technical validation rules visible in code
[ ] Back to dashboard buttons on all pages
[ ] Good GUI design — colors match mockups

=======================================================
VALIDATION RULES SUMMARY (for viva defense)
=======================================================

BUSINESS RULES (domain logic):
1. Invoice amount must be greater than zero (MyInvoices.java — registerBtn)
2. Due date must be after issue date (MyInvoices.java — registerBtn)
3. Cannot verify invoice if status is not SUBMITTED (InvoiceManagement.java — verifyBtn)
4. Cannot mark invoice as funded if status is not VERIFIED (InvoiceManagement.java — markAsFundedBtn)
5. Funding amount cannot exceed investor available balance (FundInvoice.java — confirmFundingBtn)

TECHNICAL RULES (input validation):
1. Required fields cannot be empty — all registration forms
2. Email must contain @ and . — UserManagement.java registerBtn
3. Password must be at least 6 characters — UserManagement.java registerBtn
4. Amount must be a valid number (NumberFormatException check) — MyInvoices + FundInvoice
5. OTP must be exactly 6 digits — OtpVerificationPage.java verifyBtn

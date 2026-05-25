/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rw.rab.view;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.JOptionPane;
import rw.rab.model.Investor;
import rw.rab.model.User;
import rw.rab.service.InvestorService;

/**
 *
 * @author nsumba
 */
public class MyBalance extends javax.swing.JFrame {

    private User loggedInUser;
    private InvestorService investorService;

    public MyBalance(User user) {
        initComponents();
        this.loggedInUser = user;
        setLocationRelativeTo(null);
        connectToServer();
        addPlaceholder(amountField, "Enter amount");
        loadAvailableBalance();
    }

    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        avAmountTxt = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        amountField = new javax.swing.JTextField();
        depositAmountBtn = new javax.swing.JButton();
        reloadBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(24, 95, 165));

        jLabel1.setFont(new java.awt.Font("DejaVu Sans", 0, 18));
        jLabel1.setText("My Balance");

        backBtn.setFont(new java.awt.Font("DejaVu Sans", 0, 15));
        backBtn.setText("Back to Dashboard");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 330, Short.MAX_VALUE)
                .addComponent(backBtn)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(backBtn))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("DejaVu Sans", 0, 15));
        jLabel2.setText("Available Balance");

        avAmountTxt.setFont(new java.awt.Font("DejaVu Sans", 1, 24));
        avAmountTxt.setText("RWF 0.0");

        jLabel3.setFont(new java.awt.Font("DejaVu Sans", 0, 15));
        jLabel3.setText("Deposit Amount (RWF)");

        amountField.setForeground(new java.awt.Color(100, 100, 100));
        amountField.setText("Enter amount");

        depositAmountBtn.setBackground(new java.awt.Color(29, 95, 165));
        depositAmountBtn.setText("Deposit");
        depositAmountBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositAmountBtnActionPerformed(evt);
            }
        });

        reloadBtn.setText("Reload");
        reloadBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(avAmountTxt)
                    .addComponent(jLabel3)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(depositAmountBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(reloadBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(avAmountTxt)
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(depositAmountBtn)
                    .addComponent(reloadBtn))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void connectToServer() {
        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 3000);
            investorService = (InvestorService) registry.lookup("investor");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Cannot connect to server: " + e.getMessage(),
                "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAvailableBalance() {
        try {
            Investor investor = getOrCreateInvestorProfile();
            if (investor == null) {
                avAmountTxt.setText("RWF 0.0");
                return;
            }
            avAmountTxt.setText("RWF " + investor.getAvailableBalance());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading balance: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void depositAmountBtnActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            if (isPlaceholder(amountField, "Enter amount") || amountField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please enter amount to deposit",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double amount;
            try {
                amount = Double.parseDouble(amountField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Amount must be a valid number",
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (amount <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Deposit amount must be greater than zero",
                    "Business Rule Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Investor investor = getOrCreateInvestorProfile();
            if (investor == null) {
                JOptionPane.showMessageDialog(this,
                    "No investor profile found for this account",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            investor.setAvailableBalance(investor.getAvailableBalance() + amount);
            String result = investorService.updateInvestor(investor);

            JOptionPane.showMessageDialog(this,
                result, "Success", JOptionPane.INFORMATION_MESSAGE);
            amountField.setText("Enter amount");
            amountField.setForeground(new java.awt.Color(100, 100, 100));
            loadAvailableBalance();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error depositing amount: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reloadBtnActionPerformed(java.awt.event.ActionEvent evt) {
        loadAvailableBalance();
    }

    private Investor getOrCreateInvestorProfile() {
        try {
            Investor investor = investorService.getInvestorByUserId(loggedInUser);
            if (investor != null) {
                return investor;
            }

            Investor newInvestor = new Investor();
            newInvestor.setFullName(loggedInUser.getUsername());
            newInvestor.setPhone("");
            newInvestor.setAvailableBalance(0);
            newInvestor.setUser(loggedInUser);
            investorService.createInvestor(newInvestor);

            return investorService.getInvestorByUserId(loggedInUser);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error preparing investor profile: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private void addPlaceholder(final javax.swing.JTextField field, final String placeholder) {
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(new java.awt.Color(0, 0, 0));
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new java.awt.Color(100, 100, 100));
                }
            }
        });
    }

    private boolean isPlaceholder(javax.swing.JTextField field, String placeholder) {
        return field.getText().trim().equals(placeholder);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyBalance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyBalance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyBalance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyBalance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyBalance(new User()).setVisible(true);
            }
        });
    }

    private javax.swing.JLabel avAmountTxt;
    private javax.swing.JButton backBtn;
    private javax.swing.JButton depositAmountBtn;
    private javax.swing.JTextField amountField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton reloadBtn;
}

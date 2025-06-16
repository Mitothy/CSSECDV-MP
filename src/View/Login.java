package View;

import Model.User;
import javax.swing.JOptionPane;
import java.util.regex.Pattern;

public class Login extends javax.swing.JPanel {

    public Frame frame;
    private int failedAttempts = 0;
    private static final int MAX_ATTEMPTS = 3;
    private long lockoutTime = 0;
    private static final long LOCKOUT_DURATION = 5 * 60 * 1000; // 5 minutes

    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        usernameFld = new javax.swing.JTextField();
        passwordFld = new javax.swing.JPasswordField(); // Changed to JPasswordField
        registerBtn = new javax.swing.JButton();
        loginBtn = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SECURITY Svcs");
        jLabel1.setToolTipText("");

        usernameFld.setBackground(new java.awt.Color(240, 240, 240));
        usernameFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        usernameFld.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "USERNAME",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        passwordFld.setBackground(new java.awt.Color(240, 240, 240));
        passwordFld.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        passwordFld.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordFld.setBorder(javax.swing.BorderFactory.createTitledBorder(
                new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "PASSWORD",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        registerBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        registerBtn.setText("REGISTER");
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });

        loginBtn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        loginBtn.setText("LOGIN");
        loginBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(200, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(registerBtn, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 178,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(usernameFld)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(passwordFld, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap(200, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap(88, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(usernameFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(passwordFld, javax.swing.GroupLayout.PREFERRED_SIZE, 59,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(registerBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(loginBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 52,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(126, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    // Basic input validation
    private boolean validateInput(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your username!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (password == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your password!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Basic username format check
        if (!Pattern.matches("^[a-zA-Z0-9_]{3,20}$", username.trim())) {
            JOptionPane.showMessageDialog(this, "Invalid username format!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Check if account is locked
    private boolean isLocked() {
        if (failedAttempts >= MAX_ATTEMPTS) {
            long timeLeft = LOCKOUT_DURATION - (System.currentTimeMillis() - lockoutTime);
            if (timeLeft > 0) {
                int minutesLeft = (int) (timeLeft / 60000) + 1;
                JOptionPane.showMessageDialog(this,
                        "Too many failed attempts! Please try again in " + minutesLeft + " minute(s).",
                        "Account Locked", JOptionPane.WARNING_MESSAGE);
                return true;
            } else {
                // Reset after lockout period
                failedAttempts = 0;
            }
        }
        return false;
    }

    private void loginBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loginBtnActionPerformed
        String username = usernameFld.getText().trim();
        String password = new String(passwordFld.getPassword());

        // Check lockout
        if (isLocked()) {
            return;
        }

        // Validate input
        if (!validateInput(username, password)) {
            passwordFld.setText(""); // Clear password for security
            return;
        }

        // Authenticate user
        User user = frame.main.sqlite.authenticateUser(username, password);

        if (user != null) {
            // Check if user account is locked
            if (user.getLocked() == 1) {
                JOptionPane.showMessageDialog(this, "Your account has been disabled. Please contact an administrator.",
                        "Account Disabled", JOptionPane.WARNING_MESSAGE);
                passwordFld.setText("");
                return;
            }

            // Successful login
            failedAttempts = 0; // Reset failed attempts
            passwordFld.setText(""); // Clear password
            JOptionPane.showMessageDialog(this, "Login successful! Welcome " + username, "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            frame.mainNav();
        } else {
            // Failed login
            failedAttempts++;
            passwordFld.setText(""); // Clear password

            if (failedAttempts >= MAX_ATTEMPTS) {
                lockoutTime = System.currentTimeMillis();
                JOptionPane.showMessageDialog(this, "Too many failed attempts! Account locked for 5 minutes.",
                        "Account Locked", JOptionPane.WARNING_MESSAGE);
            } else {
                int remaining = MAX_ATTEMPTS - failedAttempts;
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password! " + remaining + " attempt(s) remaining.", "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }// GEN-LAST:event_loginBtnActionPerformed

    private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_registerBtnActionPerformed
        // Clear fields before going to register
        usernameFld.setText("");
        passwordFld.setText("");
        frame.registerNav();
    }// GEN-LAST:event_registerBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginBtn;
    private javax.swing.JPasswordField passwordFld;
    private javax.swing.JButton registerBtn;
    private javax.swing.JTextField usernameFld;
    // End of variables declaration//GEN-END:variables
}
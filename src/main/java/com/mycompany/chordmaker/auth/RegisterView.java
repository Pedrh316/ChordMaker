package com.mycompany.chordmaker.auth;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton submitButton;
    private JButton loginButton;

    public RegisterView() {
        super("Register");
        
        initUI();
        setSize(400, 200);
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    private void initUI() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        submitButton = new JButton("Registrar");
        emailField = new JTextField(20);
        loginButton = new JButton("Login");

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);


        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; 
        panel.add(usernameField, gbc);


        gbc.gridx = 0; 
        gbc.gridy = 1; 
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; 
        panel.add(passwordField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);


        gbc.gridx = 0; 
        gbc.gridy = 3; 
        panel.add(submitButton, gbc);
        
        gbc.gridx = 1; 
        panel.add(loginButton, gbc);


        add(panel);
    }


    public String getUsername() {
        return usernameField.getText().trim();
    }


    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }
    
    public String getEmail() {
        return emailField.getText().trim();
    }

    public void setSubmitAction(ActionListener al) {
        submitButton.addActionListener(al);
    }
    
    public void setLoginAction(ActionListener al) { 
        loginButton.addActionListener(al); 
    }
}

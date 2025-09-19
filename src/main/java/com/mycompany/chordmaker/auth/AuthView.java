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

public class AuthView extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;


    public AuthView() {
        super("Login");
        
        initUI();
        
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    private void initUI() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        registerButton = new JButton("Registrar");


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);


        gbc.gridx = 0;
        gbc.gridy = 0; 
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; 
        panel.add(usernameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);
        
        gbc.gridx = 1;
        panel.add(registerButton, gbc);

        add(panel);
    }


    public String getUsername() {
        return usernameField.getText().trim();
    }


    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }


    public void setLoginAction(ActionListener al) {
        loginButton.addActionListener(al);
    }


    public void setRegisterAction(ActionListener al) {
        registerButton.addActionListener(al);
    }
}

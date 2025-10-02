package View;


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

public class LoginView extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private JButton botaoRegistrar;


    public LoginView() {
        super("Login");
        
        construirInterface();
        
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    private void construirInterface() {
        campoUsuario = new JTextField(20);
        campoSenha = new JPasswordField(20);
        botaoLogin = new JButton("Login");
        botaoRegistrar = new JButton("Registrar");


        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);


        gbc.gridx = 0;
        gbc.gridy = 0; 
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1; 
        panel.add(campoUsuario, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        panel.add(campoSenha, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(botaoLogin, gbc);
        
        gbc.gridx = 1;
        panel.add(botaoRegistrar, gbc);

        add(panel);
    }


    public String getUsuario() {
        return campoUsuario.getText().trim();
    }


    public String getSenha() {
        return String.valueOf(campoSenha.getPassword());
    }


    public void setAcaoLogin(ActionListener al) {
        botaoLogin.addActionListener(al);
    }


    public void setAcaoRegistrar(ActionListener al) {
        botaoRegistrar.addActionListener(al);
    }
}
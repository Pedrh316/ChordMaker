package View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistrarView extends JFrame {

    private JTextField campoNome;
    private JPasswordField campoSenha;
    private JTextField campoEmail;
    private JButton botaoRegistrar;
    private JButton botaoLogin;
    private JCheckBox artistaCheckbox;

    public RegistrarView() {
        super("Register");

        construirInterface();
        setSize(400, 200);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void construirInterface() {
        campoNome = new JTextField(20);
        campoSenha = new JPasswordField(20);
        botaoRegistrar = new JButton("Registrar");
        campoEmail = new JTextField(20);
        botaoLogin = new JButton("Login");
        artistaCheckbox = new JCheckBox();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        panel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        panel.add(campoSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(campoEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Artista?"), gbc);
        gbc.gridx = 1;
        panel.add(artistaCheckbox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(botaoLogin, gbc);

        gbc.gridx = 1;
        panel.add(botaoRegistrar, gbc);

        add(panel);
    }

    public String getNome() {
        return campoNome.getText().trim();
    }

    public String getSenha() {
        return String.valueOf(campoSenha.getPassword());
    }

    public String getEmail() {
        return campoEmail.getText().trim();
    }
    
    public boolean getArtista() {
        return artistaCheckbox.isSelected();
    }

    public void setAcaoRegistrar(ActionListener al) {
        botaoRegistrar.addActionListener(al);
    }

    public void setAcaoLogin(ActionListener al) {
        botaoLogin.addActionListener(al);
    }
}

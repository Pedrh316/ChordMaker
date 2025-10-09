package Model;

import ChordMaker.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth {
    /* Dados obtidos do formulário de login ou registro */
    private String email;
    private String senha;
    private String nome;
    
    /* 
     *   Getters e Setters 
    */
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
    public Usuario login() {
        // TODO: não armazenar senha em plain text
        // String sql = "SELECT password_hash, salt FROM users WHERE username = ?";
        String sql = "SELECT id, nome, email, senha, biografia, genero, is_artista "
                + "FROM usuario "
                + "WHERE email = ? AND senha = ?";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u;
                    
                    if (rs.getBoolean("is_artista")) {
                        u = new Artista(
                                rs.getString("biografia"),
                                rs.getString("genero"),
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("senha")
                        );
                    } else {
                        u = new Usuario(
                                rs.getString("nome"),
                                rs.getString("email"),
                                rs.getString("senha")
                        );
                    }
                    
                    

                    return u;
                }
            }
        } catch (SQLException ex) {
            System.getLogger(Usuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return null;
    }

    public Usuario registrar() {
        String sql = "INSERT INTO usuario (nome, senha, email) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, senha);
            ps.setString(3, email);

            ps.executeUpdate();
            
            return new Usuario(nome, email, senha);
            
        } catch (SQLException ex) {
            System.getLogger(Usuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        return null;
    }
}

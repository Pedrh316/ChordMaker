package Model;

import ChordMaker.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private final List<Playlist> playlists = new ArrayList();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public void criarPlaylist(String nomePlaylist, List<Musica> musicas) {
        var playlist = new Playlist(nomePlaylist);
        
        playlist.addMusicaPlaylist(musicas);
        
        playlists.add(playlist);
    }
    
    public void criarPlaylist(String nomePlaylist) {
        var playlist = new Playlist(nomePlaylist);
        
        playlists.add(playlist);
    }
    
    public void criarPlaylist(String nomePlaylist, Musica musica) {
        var playlist = new Playlist(nomePlaylist);
        
        playlist.addMusicaPlaylist(musica);
        
        playlists.add(playlist);
    }
    
    public boolean login(String usuario, String senha) {
        // TODO: não armazenar senha em plain text
        // String sql = "SELECT password_hash, salt FROM users WHERE username = ?";
        String sql = "SELECT password FROM users WHERE username = ?";
    
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String pwd = rs.getString("password");
                    
                    return pwd.equals(senha);
                }
            }
        } catch (SQLException ex) {
            System.getLogger(Usuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
        return false;   
    }
    
    public boolean registrar(String usuario, String senha, String email) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, senha);
            ps.setString(3, email);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Usuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } 
        
        return false;
    }
}

package Model;

import ChordMaker.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private final List<Playlist> playlists = new ArrayList<>();

    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome) {
        this.nome = nome;
    }

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public static void criarTable() {
        var sql = "CREATE TABLE IF NOT EXISTS usuario("
                + "id SERIAL PRIMARY KEY, "
                + "nome TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "senha TEXT NOT NULL, "
                + "is_artista BOOLEAN NOT NULL DEFAULT FALSE, "
                + "biografia TEXT, "
                + "genero TEXT"
                + ")";

        try {
            var connection = DBUtil.getConnection();
            var st = connection.createStatement();
            
            st.execute(sql);
        } catch (SQLException ex) {
            System.getLogger(Usuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}

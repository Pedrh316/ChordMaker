package Model;

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
}

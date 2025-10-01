package Model;

import java.util.LinkedList;
import java.util.List;

public class Playlist {

    private String titulo;
    private String arquivoCapa;
    private final List<Musica> musicasPlaylist = new LinkedList();

    public Playlist(String titulo) {
        this.titulo = titulo;
    }

    public List<Musica> getMusicasPlaylist() {
        return musicasPlaylist;
    }
    
    public void addMusicaPlaylist(Musica musica) {
        musicasPlaylist.add(musica);
    }
    
    public void addMusicaPlaylist(List<Musica> musicas) {
        musicasPlaylist.addAll(musicas);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArquivoCapa() {
        return arquivoCapa;
    }

    public void setArquivoCapa(String arquivoCapa) {
        this.arquivoCapa = arquivoCapa;
    }
}

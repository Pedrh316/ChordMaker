package Model;

import java.util.ArrayList;
import java.util.List;

public class Artista extends Usuario {

    private String biografia;
    private String genero;
    private final List<Musica> musicasArtista = new ArrayList();
    private final List<Album> albumsArtista = new ArrayList();

    public Artista(String biografia, String genero, int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.biografia = biografia;
        this.genero = genero;
    }

    public Artista(String nome) {
        super(nome);
    }
    

    public List<Musica> listarMusica() {
        return musicasArtista;
    }
    
    public void addMusica(Musica musica) {
        musicasArtista.add(musica);
    }
    

    public List<Album> listarAlbum() {
        return albumsArtista;
    }
    
    public void addAlbum(Album album) {
        albumsArtista.add(album);
    }
    
    

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void criarMusica() {
        // chord maker
    }

    public void editarMusica() {
        // chord maker
    }
}

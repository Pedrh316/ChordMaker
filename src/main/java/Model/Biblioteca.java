package Model;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {

    private Usuario usuario;
    private List<Musica> biblioteca;

    public Biblioteca(Usuario usuario) {
        this.usuario = usuario;
        this.biblioteca = new ArrayList<>();
    }

    public Biblioteca(Usuario usuario, List<Musica> biblioteca) {
        this.usuario = usuario;
        this.biblioteca = biblioteca;
    }

    public List<Musica> getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(List<Musica> biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void addBiblioteca(Musica m) {
        this.biblioteca.add(m);
    }

    public void removeBiblioteca(Musica m) {
        this.biblioteca.remove(m);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

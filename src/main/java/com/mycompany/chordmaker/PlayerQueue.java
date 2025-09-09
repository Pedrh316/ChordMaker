package com.mycompany.chordmaker;

import java.util.LinkedList;
import java.util.List;

public class PlayerQueue {

    private final List<Musica> listaMusica = new LinkedList();

    public List<Musica> getListaMusica() {
        return listaMusica;
    }

    public void adicionarFila(Musica musica) {
        listaMusica.add(musica);
    }

    public void removerFila(Musica musica) {
        listaMusica.remove(musica);
    }

    public void adicionarFilaProximo(Musica musica) {
        listaMusica.add(0, musica);
    }
}

package com.mycompany.chordmaker;

import java.util.ArrayList;
import java.util.List;

public class Album {

    private String titulo;
    private int dataLancamento;
    private String arquivoCapa;
    private String descricao;
    private final List<Musica> musicasAlbum = new ArrayList();

    public void addMusicaAlbum(Musica musica) {
        musicasAlbum.add(musica);
    }

    public List<Musica> getMusicasAlbum() {
        return musicasAlbum;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(int dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getArquivoCapa() {
        return arquivoCapa;
    }

    public void setArquivoCapa(String arquivoCapa) {
        this.arquivoCapa = arquivoCapa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

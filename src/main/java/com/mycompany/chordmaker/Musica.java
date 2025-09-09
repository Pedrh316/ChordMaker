
package com.mycompany.chordmaker;

public class Musica {
    private String titulo;
    private int duracao;
    private String genero;
    private int dataLancamento;
    private String arquivoAudio;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getDuracao() { return duracao; }
    public void setDuracao(int duracao) { this.duracao = duracao; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(int dataLancamento) { this.dataLancamento = dataLancamento; }

    public String getArquivoAudio() { return arquivoAudio; }
    public void setArquivoAudio(String arquivoAudio) { this.arquivoAudio = arquivoAudio; }

    public void play() {}
    public void pause() {}
    public void stop() {}
}
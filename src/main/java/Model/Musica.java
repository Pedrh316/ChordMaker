package Model;

import java.util.Date;
import javax.sound.midi.Sequence;

public class Musica {

    private Sequence faixa;
    private String titulo;
    private long duracao;
    private String genero;
    private Date dataLancamento;
    private Album album;
    private Artista artista;

    public Musica(Artista artista, Sequence faixa) {
        this.artista = artista;
        this.faixa = faixa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getDuracao() {
        return duracao;
    }

    public void setDuracao(long duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(Date dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Sequence getFaixa() {
        return faixa;
    }

    public void setFaixa(Sequence faixa) {
        this.faixa = faixa;
    }
    
    public void salvar() {
        
    }

}

package Model;

import ChordMaker.Util.DBUtil;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

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
    
    public void carregarBiblioteca() {
        try {
            var sql = "SELECT id, titulo, genero, data_lancamento, faixa, artista_id "
                    + "FROM musica "
                    + "ORDER BY titulo";
            
            var musicas = new ArrayList<Musica>();
            
            var conn = DBUtil.getConnection();
            var stmt = conn.prepareStatement(sql);
            
            var rs = stmt.executeQuery();
            
            biblioteca.clear();
            
            while (rs.next()) {
                var id = rs.getInt("id");
                var titulo = rs.getString("titulo");
                var genero = rs.getString("genero");
                java.sql.Date dataSql = rs.getDate("data_lancamento");
                byte[] faixaBytes = rs.getBytes("faixa");
                int artistaId = rs.getInt("artista_id");
                
                var artista = Artista.buscarArtistaPorId(artistaId);
                
                if (artista == null) {
                    // pula música órfã (sem artista)
                    continue;
                }
                
                var musica = new Musica(id, artista, titulo, null);
                musica.setGenero(genero);
                
                if (dataSql != null)
                    musica.setDataLancamento(new java.util.Date(dataSql.getTime()));
                
                if (faixaBytes != null)
                    musica.setFaixa(faixaBytes);
                else {
                    musica.setFaixa(new Sequence(Sequence.PPQ, 480));
                    musica.getFaixa().createTrack();
                }
                
                biblioteca.add(musica);
            }
        } catch (SQLException | InvalidMidiDataException | IOException ex) {
            System.getLogger(Biblioteca.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }

}

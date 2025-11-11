package Model;

import ChordMaker.Util.DBUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

public class Musica {

    private int id;
    private Sequence faixa;
    private String titulo;
    private String genero;
    private Date dataLancamento;
    private Artista artista;

    public Musica(int id, Artista artista, Sequence faixa) {
        this.id = id;
        this.artista = artista;
        this.faixa = faixa;
    }

    public Musica(int id, Artista artista, String titulo, Sequence faixa) {
        this.id = id;
        this.artista = artista;
        this.titulo = titulo;
        this.faixa = faixa;
    }

    public Musica(Artista artista) {
        try {
            this.id = -1;
            this.artista = artista;
            this.faixa = new Sequence(Sequence.PPQ, 480);
            faixa.createTrack();
        } catch (InvalidMidiDataException ex) {
            System.getLogger(Musica.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Sequence getFaixa() {
        return faixa;
    }

    public byte[] getFaixaBytes() throws IOException {
        if (faixa == null) {
            return null;
        }

        var byteArrayStream = new ByteArrayOutputStream();
        MidiSystem.write(faixa, 1, byteArrayStream);

        return byteArrayStream.toByteArray();
    }

    public void setFaixa(Sequence faixa) {
        this.faixa = faixa;
    }
    
    public void setFaixa(byte[] faixa) throws InvalidMidiDataException, IOException {
        var byteArrayStream = new ByteArrayInputStream(faixa);
        this.faixa = MidiSystem.getSequence(byteArrayStream);
    }

    public int getId() {
        return id;
    }

    public void salvar() {
        if (id <= -1) {
            try {
                String sql = "INSERT INTO musica (titulo, genero, data_lancamento, faixa, artista_id) "
                        + "VALUES (?, ?, ?, ?, ?) "
                        + "RETURNING id";

                var conn = DBUtil.getConnection();
                var stmt = conn.prepareStatement(sql);

                stmt.setString(1, titulo);
                stmt.setString(2, genero);
                stmt.setDate(3, dataLancamento != null
                        ? new java.sql.Date(dataLancamento.getTime())
                        : null);

                stmt.setBytes(4, getFaixaBytes());
                stmt.setInt(5, artista.getId());

                var rs = stmt.executeQuery();

                if (rs.next()) {
                    id = rs.getInt("id");
                }
            } catch (SQLException | IOException ex) {
                System.getLogger(Musica.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

        } else {
            try {
                String sql = "UPDATE musica SET "
                        + "titulo = ?, "
                        + "genero = ?, "
                        + "data_lancamento = ?, "
                        + "faixa = ?, "
                        + "artista_id = ? "
                        + "WHERE id = ?";

                var conn = DBUtil.getConnection();
                var stmt = conn.prepareStatement(sql);

                stmt.setString(1, titulo);
                stmt.setString(2, genero);
                stmt.setDate(3, dataLancamento != null
                        ? new java.sql.Date(dataLancamento.getTime())
                        : null);

                stmt.setBytes(4, getFaixaBytes());
                stmt.setInt(5, artista.getId());
                stmt.setInt(6, id);

                stmt.executeUpdate();
            } catch (SQLException | IOException ex) {
                System.getLogger(Musica.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }

    }

    public static void criarTable() {
        var sql = "CREATE TABLE IF NOT EXISTS musica("
                + "id SERIAL PRIMARY KEY, "
                + "titulo TEXT NOT NULL, "
                + "genero TEXT, "
                + "data_lancamento DATE, "
                + "faixa BYTEA, "
                + "artista_id INT NOT NULL, "
                + "FOREIGN KEY (artista_id) REFERENCES usuario(id) ON DELETE CASCADE"
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

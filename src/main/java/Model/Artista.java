package Model;

import ChordMaker.Util.DBUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Artista extends Usuario {

    private String biografia;
    private String genero;
    private final List<Musica> musicasArtista = new ArrayList();

    public Artista(String biografia, String genero, int id, String nome, String email, String senha) {
        super(id, nome, email, senha);
        this.biografia = biografia;
        this.genero = genero;
    }

    public Artista(int id, String nome) {
        super(id, nome);
    }

    public List<Musica> listarMusica() {
        return musicasArtista;
    }

    public void addMusica(Musica musica) {
        musicasArtista.add(musica);
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

    public static Artista buscarArtistaPorId(int id) {
        String sql = "SELECT id, nome, email FROM usuario WHERE id = ?";

        try {
            var conn = DBUtil.getConnection();
            var stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    var artista = new Artista(
                            rs.getInt("id"),
                            rs.getString("nome")
                    );

                    artista.setEmail(rs.getString("email"));
                    return artista;
                }
            }
        } catch (SQLException e) {
            System.getLogger(Biblioteca.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, e);
        }
        return null;

    }

}

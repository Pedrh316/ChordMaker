package Model;

import ChordMaker.Util.DBUtil;
import java.sql.SQLException;
import java.util.Objects;

public class Usuario {

    private final int id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(int id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public static void criarTable() {
        var sql = "CREATE TABLE IF NOT EXISTS usuario("
                + "id SERIAL PRIMARY KEY, "
                + "nome TEXT NOT NULL, "
                + "email TEXT NOT NULL, "
                + "senha TEXT NOT NULL, "
                + "is_artista BOOLEAN NOT NULL DEFAULT FALSE, "
                + "biografia TEXT, "
                + "genero TEXT"
                + ")";

        try {
            var connection = DBUtil.getConnection();
            var st = connection.createStatement();

            st.execute(sql);
        } catch (SQLException ex) {
            System.getLogger(Usuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (obj instanceof Usuario) {
            final Usuario other = (Usuario) obj;
            
            return this.id == other.id;
        }
        
        
        return false;
    }
    
    
}

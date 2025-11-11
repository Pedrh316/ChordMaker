package ChordMaker;

import ChordMaker.Core.Navegador;
import Model.Musica;
import Model.Usuario;

public class Main {

    public static void main(String[] args) {
        // Criar tabelas
        Usuario.criarTable();
        Musica.criarTable();
        
        Navegador.getNavegador().abrirLogin();
    }
}

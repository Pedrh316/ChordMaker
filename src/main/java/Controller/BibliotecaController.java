package Controller;

import Model.Biblioteca;
import View.BibliotecaView;

public class BibliotecaController {
    private final Biblioteca model;
    private final BibliotecaView view;

    public BibliotecaController(Biblioteca biblioteca, BibliotecaView bView) {
        this.model = biblioteca;
        this.view = bView;
        
        this.view.setNome(model);
        this.view.atualizarLista(model);
        
        this.view.setVisible(true);
    }
    
    
    
}

package View;

import ChordMaker.NotaUtil;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.Sequence;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditorMusica extends JFrame {

    private List<JTextArea> jTextArea_tracks = new ArrayList<>();
    private JFileChooser jFileChooser_importar = new JFileChooser();
    private JFileChooser jFileChooser_exportar = new JFileChooser();

    public EditorMusica() {
        initComponents();
        
        jFileChooser_importar.setDialogTitle("Importar arquivo MIDI");
        jFileChooser_importar.setFileFilter(new FileNameExtensionFilter(
                "Arquivos MIDI (*.mid, *.midi)", "mid", "midi"
        ));
        
        jFileChooser_exportar.setDialogTitle("Exportar arquivo MIDI");
        jFileChooser_exportar.setFileFilter(new FileNameExtensionFilter(
                "Arquivos MIDI (*.mid, *.midi)", "mid", "midi"
        ));

    }

    public void setMusicaNome(String musicaNome) {
        jTextField_nomeMusica.setText(musicaNome);
    }

    public void setMusicaNomeDocumentListener(DocumentListener a) {
        jTextField_nomeMusica.getDocument().addDocumentListener(a);
    }

    public void setArtistaNome(String artistaNome) {
        jLabel_nomeArtista.setText(artistaNome);
    }

    public void setBotaoPlay(ActionListener a) {
        jButton_play.addActionListener(a);
    }

    public void setBotaoStop(ActionListener a) {
        jButton_stop.addActionListener(a);
    }

    public void setBotaoSalvar(ActionListener a) {
        jButton_salvar.addActionListener(a);
    }

    public void setAbaTrack(DocumentListener a) {
        for (var trackArea : jTextArea_tracks) {
            trackArea.getDocument().addDocumentListener(a);
        }
    }
    
    public int getAbasContagem() {
        return jTabbedPane_chords.getTabCount();
    }

    private JTextArea getAreaTexto(int i) {
        var scroll = (JScrollPane) jTabbedPane_chords.getComponentAt(i);

        return (JTextArea) scroll.getViewport().getView();
    }

    public String getTextoTrack(int i) {
        var areaTexto = getAreaTexto(i);

        return areaTexto.getText();
    }

    public void setTextoTrack(int i, String texto) {
        var areaTexto = getAreaTexto(i);

        areaTexto.setText(texto);
    }

    public void setSequence(Sequence sequence) {
        // Limpar tabs antigas
        jTabbedPane_chords.removeAll();
        jTextArea_tracks.clear();

        var tracks = sequence.getTracks();

        for (int i = 0; i < tracks.length; i++) {
            var track = tracks[i];
            var textArea = new JTextArea();
            textArea.setEditable(true);

            var stringB = new StringBuilder();

            for (int j = 0; j < track.size(); j++) {
                var event = track.get(j);
                var msg = event.getMessage();
                var desc = NotaUtil.descricaoMensagem(msg);

                if (desc == null) {
                    continue;
                }

                stringB.append(String.format("Tick: %-6d | %s\n", event.getTick(), desc));
            }

            textArea.setText(stringB.toString());
            jTextArea_tracks.add(textArea);

            var scroll = new JScrollPane(textArea);
            jTabbedPane_chords.add("Track #" + i, scroll);
        }
    }

    public void setTrackCor(int i, Color cor) {
        var areaTexto = getAreaTexto(i);
        areaTexto.setBackground(cor);
    }
    
    public void setBotaoRemoverTrack(ActionListener l) {
        jButton_removerTrack.addActionListener(l);
    }
    
    public void setDesativarBotaoRemoverTrack() {
        jButton_removerTrack.setEnabled(false);
    }
    
    public void setAtivarBotaoRemoverTrack() {
        jButton_removerTrack.setEnabled(true);
    }
    
    public void setBotaoAdicionarTrack(ActionListener l) {
        jButton_adicionarTrack.addActionListener(l);
    }
    
    public int getAbaSelecionada() {
        return jTabbedPane_chords.getSelectedIndex();
    }
    
    public void setAbaSelecionada(int i) {
        jTabbedPane_chords.setSelectedIndex(i);
    }
    
    public void setBotaoAdicionarNota(ActionListener l) {
        jButton_adicionarNota.addActionListener(l);
    }
    
    public int getCanal() {
        return (int) jSpinner_canal.getValue();
    }
    
    public int getVelocidade() {
        return (int) jSpinner_velocidade.getValue();
    }
    
    public int getDuracao() {
        return (int) jSpinner_duracao.getValue();
    }
    
    public int getOitava() {
        return (int) jSpinner_oitava.getValue();
    }
    
    public String getNota() {
        return (String) jComboBox_listaNotas.getSelectedItem();
    }
    
    public int getTick() {
        return (int) jSpinner_tick.getValue();
    }
    
    public void setBotaoImportar(ActionListener l) {
        jButton_importar.addActionListener(l);
    }
    
    public void setBotaoExportar(ActionListener l) {
        jButton_exportar.addActionListener(l);
    }
    
    public JFileChooser getImportarFilePicker() {
        return jFileChooser_importar;
    }
    
    public JFileChooser getExportarFilePicker() {
        return jFileChooser_exportar;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane_chords = new javax.swing.JTabbedPane();
        jButton_play = new javax.swing.JButton();
        jButton_stop = new javax.swing.JButton();
        jButton_salvar = new javax.swing.JButton();
        jPanel_adicionarNota = new javax.swing.JPanel();
        jLabel_nota = new javax.swing.JLabel();
        jLabel_oitava = new javax.swing.JLabel();
        jLabel_velocidade = new javax.swing.JLabel();
        jLabel_canal = new javax.swing.JLabel();
        jLabel_duracao = new javax.swing.JLabel();
        jSpinner_velocidade = new javax.swing.JSpinner();
        jSpinner_oitava = new javax.swing.JSpinner();
        jComboBox_listaNotas = new javax.swing.JComboBox<>();
        jSpinner_canal = new javax.swing.JSpinner();
        jSpinner_duracao = new javax.swing.JSpinner();
        jButton_adicionarNota = new javax.swing.JButton();
        jLabel_adicionarNota = new javax.swing.JLabel();
        jLabel_tick = new javax.swing.JLabel();
        jSpinner_tick = new javax.swing.JSpinner();
        jPanel1 = new javax.swing.JPanel();
        jButton_importar = new javax.swing.JButton();
        jButton_exportar = new javax.swing.JButton();
        jButton_adicionarTrack = new javax.swing.JButton();
        jButton_removerTrack = new javax.swing.JButton();
        jLabel_editando = new javax.swing.JLabel();
        jPanel_titulo = new javax.swing.JPanel();
        jTextField_nomeMusica = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel_nomeArtista = new javax.swing.JLabel();
        jLabel_titulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButton_play.setText("▶");

        jButton_stop.setText("⏹");

        jButton_salvar.setText("💾");

        jPanel_adicionarNota.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel_nota.setText("Nota:");

        jLabel_oitava.setText("Oitava:");

        jLabel_velocidade.setText("Velocidade:");

        jLabel_canal.setText("Canal:");

        jLabel_duracao.setText("Duração:");

        jSpinner_velocidade.setModel(new javax.swing.SpinnerNumberModel(0, 0, 127, 1));

        jSpinner_oitava.setModel(new javax.swing.SpinnerNumberModel(0, -1, 9, 1));

        jComboBox_listaNotas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" }));

        jSpinner_canal.setModel(new javax.swing.SpinnerNumberModel(0, 0, 15, 1));

        jSpinner_duracao.setModel(new javax.swing.SpinnerNumberModel(480, 0, null, 1));

        jButton_adicionarNota.setText("Adicionar nota");

        jLabel_adicionarNota.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel_adicionarNota.setText("Adicionar Nota");

        jLabel_tick.setText("Tick:");

        jSpinner_tick.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        javax.swing.GroupLayout jPanel_adicionarNotaLayout = new javax.swing.GroupLayout(jPanel_adicionarNota);
        jPanel_adicionarNota.setLayout(jPanel_adicionarNotaLayout);
        jPanel_adicionarNotaLayout.setHorizontalGroup(
            jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_adicionarNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_adicionarNota)
                    .addComponent(jLabel_adicionarNota)
                    .addGroup(jPanel_adicionarNotaLayout.createSequentialGroup()
                        .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel_velocidade, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addComponent(jLabel_oitava, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_nota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_canal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel_duracao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel_tick, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSpinner_velocidade, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_listaNotas, javax.swing.GroupLayout.Alignment.LEADING, 0, 100, Short.MAX_VALUE)
                            .addComponent(jSpinner_oitava, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinner_canal)
                            .addComponent(jSpinner_duracao)
                            .addComponent(jSpinner_tick, javax.swing.GroupLayout.Alignment.LEADING))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_adicionarNotaLayout.setVerticalGroup(
            jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_adicionarNotaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_adicionarNota)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_tick, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSpinner_tick, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_nota)
                    .addComponent(jComboBox_listaNotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_oitava)
                    .addComponent(jSpinner_oitava, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_velocidade)
                    .addComponent(jSpinner_velocidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner_canal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_canal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_adicionarNotaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_duracao)
                    .addComponent(jSpinner_duracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton_adicionarNota)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButton_importar.setText("Importar .MID");

        jButton_exportar.setText("Exportar .MID");

        jButton_adicionarTrack.setText("Adicionar Track");

        jButton_removerTrack.setText("Remover Track");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_importar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton_exportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton_removerTrack, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(jButton_adicionarTrack, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_importar)
                    .addComponent(jButton_adicionarTrack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_removerTrack)
                    .addComponent(jButton_exportar))
                .addContainerGap())
        );

        jLabel_editando.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel_editando.setText("Editando");

        jPanel_titulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField_nomeMusica.setText("Titulo");

        jLabel1.setText("Artista:");

        jLabel_nomeArtista.setText("Artista");

        jLabel_titulo.setText("Título:");

        javax.swing.GroupLayout jPanel_tituloLayout = new javax.swing.GroupLayout(jPanel_titulo);
        jPanel_titulo.setLayout(jPanel_tituloLayout);
        jPanel_tituloLayout.setHorizontalGroup(
            jPanel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_tituloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_nomeArtista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField_nomeMusica))
                .addContainerGap())
        );
        jPanel_tituloLayout.setVerticalGroup(
            jPanel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_tituloLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_nomeMusica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel_titulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel_tituloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel_nomeArtista))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel_editando)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_adicionarNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel_titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(415, 415, 415)
                                .addComponent(jButton_stop, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_play, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTabbedPane_chords, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel_editando, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel_titulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel_adicionarNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane_chords, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton_play, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_stop, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditorMusica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditorMusica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditorMusica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditorMusica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditorMusica().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_adicionarNota;
    private javax.swing.JButton jButton_adicionarTrack;
    private javax.swing.JButton jButton_exportar;
    private javax.swing.JButton jButton_importar;
    private javax.swing.JButton jButton_play;
    private javax.swing.JButton jButton_removerTrack;
    private javax.swing.JButton jButton_salvar;
    private javax.swing.JButton jButton_stop;
    private javax.swing.JComboBox<String> jComboBox_listaNotas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_adicionarNota;
    private javax.swing.JLabel jLabel_canal;
    private javax.swing.JLabel jLabel_duracao;
    private javax.swing.JLabel jLabel_editando;
    private javax.swing.JLabel jLabel_nomeArtista;
    private javax.swing.JLabel jLabel_nota;
    private javax.swing.JLabel jLabel_oitava;
    private javax.swing.JLabel jLabel_tick;
    private javax.swing.JLabel jLabel_titulo;
    private javax.swing.JLabel jLabel_velocidade;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_adicionarNota;
    private javax.swing.JPanel jPanel_titulo;
    private javax.swing.JSpinner jSpinner_canal;
    private javax.swing.JSpinner jSpinner_duracao;
    private javax.swing.JSpinner jSpinner_oitava;
    private javax.swing.JSpinner jSpinner_tick;
    private javax.swing.JSpinner jSpinner_velocidade;
    private javax.swing.JTabbedPane jTabbedPane_chords;
    private javax.swing.JTextField jTextField_nomeMusica;
    // End of variables declaration//GEN-END:variables
}

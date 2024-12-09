package es.studium.Musica;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MusicApp extends JFrame {
    private DefaultListModel<String> listModel; // Modelo para la lista de archivos
    private JList<String> fileList;            // Lista de archivos mostrada en la interfaz
    private MusicPlayer musicPlayer;           // Reproductor de música
    private ArrayList<File> musicFiles;        // Lista de archivos encontrados

    /**
     * Constructor de la aplicación de música.
     * Configura la interfaz gráfica y realiza la búsqueda de archivos.
     */
    public MusicApp() {
        setTitle("Music Player");           // Título de la ventana
        setSize(500, 400);                  // Tamaño de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cierra la app al cerrar la ventana

        // Configura la lista y botones
        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        JButton playButton = new JButton("Reproducir");
        JButton stopButton = new JButton("Parar");

        // Asigna eventos a los botones
        playButton.addActionListener(e -> playSelectedFiles());
        stopButton.addActionListener(e -> stopSelectedFiles());

        // Configura el panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(fileList), BorderLayout.CENTER);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Agrega el panel principal a la ventana
        add(panel);

        // Inicializa el reproductor y realiza la búsqueda de archivos
        musicPlayer = new MusicPlayer();
        searchMusic();
    }

    /**
     * Realiza la búsqueda de archivos de música en el directorio C:\
     * Solo busca archivos con extensiones mp3 y wav.
     */
    private void searchMusic() {
        ArrayList<String> extensions = new ArrayList<>();
        extensions.add("mp3");
        extensions.add("wav");

        FileSearcher fileSearcher = new FileSearcher(extensions);
        musicFiles = fileSearcher.searchMusicFiles(new File("C:\\"));

        // Agrega los archivos encontrados a la lista de la interfaz
        for (File file : musicFiles) {
            listModel.addElement(file.getName());
        }
    }

    /**
     * Reproduce los archivos seleccionados en la lista.
     * Muestra un mensaje si no hay archivos seleccionados.
     */
    private void playSelectedFiles() {
        int[] selectedIndices = fileList.getSelectedIndices();
        if (selectedIndices.length == 0) {
            System.out.println("No se ha seleccionado ningún archivo para reproducir.");
            return;
        }

        // Reproduce cada archivo seleccionado
        for (int index : selectedIndices) {
            File selectedFile = musicFiles.get(index);
            musicPlayer.play(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Detiene los archivos seleccionados en la lista.
     * Muestra un mensaje si no hay archivos seleccionados.
     */
    private void stopSelectedFiles() {
        int[] selectedIndices = fileList.getSelectedIndices();
        if (selectedIndices.length == 0) {
            System.out.println("No se ha seleccionado ningún archivo para detener.");
            return;
        }

        // Detiene cada archivo seleccionado
        for (int index : selectedIndices) {
            File selectedFile = musicFiles.get(index);
            musicPlayer.stop(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Método principal que inicia la aplicación.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicApp().setVisible(true));
    }
}

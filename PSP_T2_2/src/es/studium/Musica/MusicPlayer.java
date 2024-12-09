package es.studium.Musica;

import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class MusicPlayer {

    // Mapa que asocia cada archivo con su hilo de reproducción
    private final Map<String, PlayerThread> activePlayers = new HashMap<>();

    /**
     * Método para reproducir un archivo de música.
     * Si ya se está reproduciendo, muestra un mensaje y no lo reproduce de nuevo.
     */
    public void play(String filePath) {
        if (activePlayers.containsKey(filePath)) {
            System.out.println("El archivo ya se está reproduciendo: " + filePath);
            return;
        }

        try {
            // Crea un objeto Player y un hilo para reproducir la música
            Player player = new Player(new FileInputStream(filePath));
            PlayerThread playerThread = new PlayerThread(filePath, player);

            // Almacena el hilo en el mapa y comienza su ejecución
            activePlayers.put(filePath, playerThread);
            System.out.println("Reproduciendo: " + filePath);
            playerThread.start();
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de errores
        }
    }

    /**
     * Método para detener un archivo específico.
     * Si no se está reproduciendo, muestra un mensaje de advertencia.
     */
    public void stop(String filePath) {
        PlayerThread playerThread = activePlayers.remove(filePath);
        if (playerThread != null) {
            playerThread.stopPlayback(); // Detiene la reproducción
            System.out.println("Archivo detenido: " + filePath);
        } else {
            System.out.println("El archivo no se está reproduciendo: " + filePath);
        }
    }

    /**
     * Clase interna que maneja la reproducción de archivos en hilos separados.
     */
    private static class PlayerThread extends Thread {
        private final String filePath; // Ruta del archivo que se reproduce
        private final Player player;   // Objeto que reproduce el archivo
        private volatile boolean isStopped = false; // Indica si se debe detener la reproducción

        // Constructor que recibe la ruta y el reproductor
        public PlayerThread(String filePath, Player player) {
            this.filePath = filePath;
            this.player = player;
        }

        @Override
        public void run() {
            try {
                // Reproduce el archivo hasta que se indique detenerlo
                while (!isStopped) {
                    player.play(); 
                }
            } catch (Exception e) {
                if (!isStopped) {
                    e.printStackTrace(); // Muestra errores solo si no se ha detenido manualmente
                }
            }
        }

        /**
         * Detiene la reproducción cerrando el reproductor.
         */
        public void stopPlayback() {
            isStopped = true;  // Indica que debe detenerse
            player.close();    // Cierra el reproductor
        }
    }
}

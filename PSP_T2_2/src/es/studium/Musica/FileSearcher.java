package es.studium.Musica;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de buscar archivos de música en un directorio.
 */
public class FileSearcher {

    // Lista de extensiones permitidas para identificar archivos de música
    private List<String> extensions;

    /**
     * Constructor de la clase FileSearcher.
     * @param extensions Lista de extensiones de archivos permitidas (por ejemplo, "mp3", "wav").
     */
    public FileSearcher(List<String> extensions) {
        this.extensions = extensions;
    }

    /**
     * Método principal para buscar archivos de música dentro de un directorio.
     * Realiza una búsqueda recursiva en todas las subcarpetas.
     * 
     * @param directory Directorio inicial donde se buscarán los archivos.
     * @return Lista de archivos de música encontrados.
     */
    public ArrayList<File> searchMusicFiles(File directory) {
        ArrayList<File> musicFiles = new ArrayList<>(); // Lista que almacenará los archivos encontrados.

        // Verifica que el directorio sea válido y exista.
        if (directory != null && directory.exists() && directory.isDirectory()) {

            // Obtiene la lista de archivos y carpetas dentro del directorio actual
            File[] files = directory.listFiles(); 
            
            if (files != null) { 
                // Recorre cada archivo/carpeta encontrado
                for (File file : files) {
                    
                    if (file.isDirectory()) { 
                        // Si es una carpeta, realiza una búsqueda recursiva en ella
                        musicFiles.addAll(searchMusicFiles(file));
                    } else {
                        // Si es un archivo, verifica si es un archivo de música
                        if (isMusicFile(file)) {
                            musicFiles.add(file); // Agrega el archivo a la lista
                        }
                    }
                }
            }
        }
        return musicFiles; // Devuelve la lista completa de archivos encontrados
    }

    /**
     * Método privado que verifica si un archivo es de música según su extensión.
     * 
     * @param file Archivo a comprobar.
     * @return true si el archivo tiene una extensión válida; false en caso contrario.
     */
    private boolean isMusicFile(File file) {
        // Obtiene el nombre del archivo en minúsculas para evitar problemas con mayúsculas/minúsculas
        String fileName = file.getName().toLowerCase();

        // Verifica si el archivo termina con alguna de las extensiones permitidas
        for (String ext : extensions) {
            if (fileName.endsWith("." + ext)) {
                return true; // Archivo válido
            }
        }
        return false; // Archivo no válido
    }
}

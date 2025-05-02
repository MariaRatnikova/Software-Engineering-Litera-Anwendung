import java.util.*;


/**
 * Interface für die Anwendung
 * Definiert die grundlegenden Methoden für die Interaktion mit dem Buchverwaltungssystem
 */
public interface ApplicationInterface {
    /**
     * Sucht nach Büchern mit dem angegebenen Titel
     * @param titel Der zu suchende Titel
     * @return Liste der gefundenen Bücher
     */
    List<Buch> buchsuche(String titel);
    
    /**
     * Sucht nach Büchern eines bestimmten Autors
     * @param autorName Der Name des Autors
     * @return Liste der gefundenen Bücher
     */
    List<Buch> sucheNachAutor(String autorName);
    
    /**
     * Sucht nach Büchern eines bestimmten Genres
     * @param genreName Der Name des Genres
     * @return Liste der gefundenen Bücher
     */
    List<Buch> sucheNachGenre(String genreName);
    
    /**
     * Sucht nach Büchern eines bestimmten Verlags
     * @param verlagName Der Name des Verlags
     * @return Liste der gefundenen Bücher
     */
    List<Buch> sucheNachVerlag(String verlagName);
    
    /**
     * Gibt die Details eines Buches anhand seiner ID zurück
     * @param id Die ID des Buches
     * @return Das gefundene Buch oder null, wenn kein Buch mit dieser ID existiert
     */
    Buch buchdetails(int id);
    
    /**
     * Gibt alle Rezensionen eines Buches zurück
     * @param buchId Die ID des Buches
     * @return Liste der Rezensionen
     */
    List<Rezension> showRezensionen(int buchId);
    
    /**
     * Fügt eine Rezension zu einem Buch hinzu
     * @param id Die ID des Buches
     * @param r Die hinzuzufügende Rezension
     */
    void reviewHinzufuegen(int id, Rezension r);
}


import java.util.*;

public class Buchkatalog {
    private final List<Buch> buecher;
    private final StorageService storageService;

    public Buchkatalog(StorageService storageService) {
        this.storageService = storageService;
        this.buecher = storageService.ladeBuecher();
    }

    /**
     * Sucht nach Büchern mit dem angegebenen Titel
     * @param titel Der zu suchende Titel (Teilstring)
     * @return Liste der gefundenen Bücher
     */
    public List<Buch> buchsuche(String titel) {
        if (titel == null || titel.trim().isEmpty()) {
            return new ArrayList<>(buecher); // Alle Bücher zurückgeben, wenn kein Titel angegeben
        }
        
        List<Buch> ergebnisse = new ArrayList<>();
        String suchTitel = titel.toLowerCase().trim();
        
        for (Buch buch : buecher) {
            if (buch.getTitel().toLowerCase().contains(suchTitel)) {
                ergebnisse.add(buch);
            }
        }
        
        return ergebnisse;
    }
    
    /**
     * Sucht nach Büchern eines bestimmten Autors
     * @param autorName Der Name des Autors (Teilstring)
     * @return Liste der gefundenen Bücher
     */
    public List<Buch> sucheNachAutor(String autorName) {
        if (autorName == null || autorName.trim().isEmpty()) {
            return new ArrayList<>(); // Leere Liste, wenn kein Autor angegeben
        }
        
        List<Buch> ergebnisse = new ArrayList<>();
        String suchAutor = autorName.toLowerCase().trim();
        
        for (Buch buch : buecher) {
            if (buch.getAutor() != null && buch.getAutor().getName().toLowerCase().contains(suchAutor)) {
                ergebnisse.add(buch);
            }
        }
        
        return ergebnisse;
    }
    
    /**
     * Sucht nach Büchern eines bestimmten Genres
     * @param genreName Der Name des Genres (Teilstring)
     * @return Liste der gefundenen Bücher
     */
    public List<Buch> sucheNachGenre(String genreName) {
        if (genreName == null || genreName.trim().isEmpty()) {
            return new ArrayList<>(); // Leere Liste, wenn kein Genre angegeben
        }
        
        List<Buch> ergebnisse = new ArrayList<>();
        String suchGenre = genreName.toLowerCase().trim();
        
        for (Buch buch : buecher) {
            if (buch.getGenres() != null) {
                for (Genre genre : buch.getGenres()) {
                    if (genre.getName().toLowerCase().contains(suchGenre)) {
                        ergebnisse.add(buch);
                        break; // Buch nur einmal hinzufügen, auch wenn mehrere Genres passen
                    }
                }
            }
        }
        
        return ergebnisse;
    }
    
    /**
     * Sucht nach Büchern eines bestimmten Verlags
     * @param verlagName Der Name des Verlags (Teilstring)
     * @return Liste der gefundenen Bücher
     */
    public List<Buch> sucheNachVerlag(String verlagName) {
        if (verlagName == null || verlagName.trim().isEmpty()) {
            return new ArrayList<>(); // Leere Liste, wenn kein Verlag angegeben
        }
        
        List<Buch> ergebnisse = new ArrayList<>();
        String suchVerlag = verlagName.toLowerCase().trim();
        
        for (Buch buch : buecher) {
            if (buch.getVerlag() != null && buch.getVerlag().getName().toLowerCase().contains(suchVerlag)) {
                ergebnisse.add(buch);
            }
        }
        
        return ergebnisse;
    }

    /**
     * Gibt die Details eines Buches anhand seiner ID zurück
     * @param id Die ID des Buches
     * @return Das gefundene Buch oder null, wenn kein Buch mit dieser ID existiert
     */
    public Buch buchDetails(int id) {
        if (id < 0 || id >= buecher.size()) {
            return null; // Ungültige ID
        }
        
        return buecher.get(id);
    }

    /**
     * Fügt eine Rezension zu einem Buch hinzu
     * @param buchId Die ID des Buches
     * @param rezension Die hinzuzufügende Rezension
     * @return true, wenn die Rezension erfolgreich hinzugefügt wurde, sonst false
     */
    public boolean reviewHinzufuegen(int buchId, Rezension rezension) {
        if (buchId < 0 || buchId >= buecher.size() || rezension == null) {
            return false; // Ungültige ID oder Rezension
        }
        
        Buch buch = buecher.get(buchId);
        buch.getRezensionen().add(rezension);
        
        // Rezension im StorageService speichern
        storageService.speichereRezension(rezension);
        
        return true;
    }

    /**
     * Gibt alle Rezensionen eines Buches zurück
     * @param buchId Die ID des Buches
     * @return Liste der Rezensionen oder leere Liste, wenn keine Rezensionen vorhanden oder ID ungültig
     */
    public List<Rezension> showRezensionen(int buchId) {
        if (buchId < 0 || buchId >= buecher.size()) {
            return new ArrayList<>(); // Ungültige ID
        }
        
        Buch buch = buecher.get(buchId);
        return buch.getRezensionen();
    }
    
    /**
     * Fügt ein neues Buch zum Katalog hinzu
     * @param buch Das hinzuzufügende Buch
     * @return true, wenn das Buch erfolgreich hinzugefügt wurde, sonst false
     */
    public boolean buchHinzufuegen(Buch buch) {
        if (buch == null) {
            return false;
        }
        
        buecher.add(buch);
        return true;
    }
    
    /**
     * Gibt die Anzahl der Bücher im Katalog zurück
     * @return Anzahl der Bücher
     */
    public int getBuecherAnzahl() {
        return buecher.size();
    }
    
    /**
     * Gibt alle Bücher im Katalog zurück
     * @return Liste aller Bücher
     */
    public List<Buch> getAlleBuecher() {
        return new ArrayList<>(buecher);
    }
}

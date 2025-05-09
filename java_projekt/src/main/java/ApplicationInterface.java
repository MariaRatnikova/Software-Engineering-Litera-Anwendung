/*
 * ApplicationInterface.java
 * ------------------------------------------------------------------
 * Zentrale Schnittstelle der Geschäftslogik.
 */

import java.util.List;

/**
 * Definiert alle Operationen, die die Präsentationsschicht mit der Geschäftslogik ausführt.
 * <p>
 * Buchrecherche → Details → Rezensionen hinzufügen bzw. anzeigen.
 */
public interface ApplicationInterface {

    /* ------------------------------------------------------------------
     * Buch­suche
     * ---------------------------------------------------------------- */

    /**
     * Sucht Bücher, deren Titel den übergebenen String enthalten.
     *
     * @param titel Suchbegriff für den Titel (Teilstring)
     * @return Liste passender {@link Buch}-Objekte (nie {@code null})
     */
    List<Buch> buchsuche(String titel);

    /**
     * Sucht Bücher nach Autor(en)-Name.
     *
     * @param autorName Teil- oder Vollname des Autors
     * @return Liste der gefundenen Bücher
     */
    List<Buch> sucheNachAutor(String autorName);

    /**
     * Sucht Bücher nach Genre.
     *
     * @param genreName Name des Genres
     * @return Liste der gefundenen Bücher
     */
    List<Buch> sucheNachGenre(String genreName);

    /**
     * Sucht Bücher nach Verlag.
     *
     * @param verlagName Name des Verlags
     * @return Liste der gefundenen Bücher
     */
    List<Buch> sucheNachVerlag(String verlagName);

    /* ------------------------------------------------------------------
     * Details & Rezensionen
     * ---------------------------------------------------------------- */

    /**
     * Liefert die vollständigen Details zu einem Buch.
     *
     * @param id Buch-ID
     * @return {@link Buch}-Objekt oder {@code null}, falls nicht gefunden
     */
    Buch buchdetails(String id);

    /**
     * Gibt alle Rezensionen zu einem Buch zurück.
     *
     * @param id Buch-ID
     * @return Liste der vorhandenen Rezensionen (nie {@code null})
     */
    List<Rezension> showRezensionen(String id);

    /**
     * Fügt einer Buch­entität eine neue Rezension hinzu und speichert sie.
     *
     * @param id        Buch-ID
     * @param rezension Neues {@link Rezension}-Objekt
     */
    void reviewHinzufuegen(String id, Rezension rezension);
}

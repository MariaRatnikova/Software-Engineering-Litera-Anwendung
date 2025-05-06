import java.util.List;

public interface ApplicationInterface {
    List<Buch> buchsuche(String titel);
    List<Buch> sucheNachAutor(String autorName);
    List<Buch> sucheNachGenre(String genreName);
    List<Buch> sucheNachVerlag(String verlagName);

    Buch buchdetails(String id);
    List<Rezension> showRezensionen(String id);
    void reviewHinzufuegen(String id, Rezension rezension);
}

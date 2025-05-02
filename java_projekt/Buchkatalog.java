import java.util.List;
import java.util.stream.Collectors;

/**
 * Buchkatalog für Suche, Details und Rezensionen.
 */
public class Buchkatalog {
    private final List<Buch> buecher;
    private final StorageService storageService;

    public Buchkatalog(StorageService storageService) {
        this.storageService = storageService;
        this.buecher = storageService.ladeBuecher();
    }

    public List<Buch> buchsuche(String titel) {
        if (titel == null || titel.isBlank()) {
            return List.copyOf(buecher);
        }
        String such = titel.toLowerCase().trim();
        return buecher.stream()
            .filter(b -> b.getTitle().toLowerCase().contains(such))
            .collect(Collectors.toList());
    }

    public List<Buch> sucheNachAutor(String autorName) {
        if (autorName == null || autorName.isBlank()) {
            return List.of();
        }
        String such = autorName.toLowerCase().trim();
        return buecher.stream()
            .filter(b -> b.getAuthor() != null
                      && b.getAuthor().toLowerCase().contains(such))
            .collect(Collectors.toList());
    }

    public List<Buch> sucheNachGenre(String genreName) {
        if (genreName == null || genreName.isBlank()) {
            return List.of();
        }
        String such = genreName.toLowerCase().trim();
        return buecher.stream()
            .filter(b -> {
                for (String g : b.getGenres()) {
                    if (g.toLowerCase().contains(such)) {
                        return true;
                    }
                }
                return false;
            })
            .collect(Collectors.toList());
    }

    public List<Buch> sucheNachVerlag(String verlagName) {
        if (verlagName == null || verlagName.isBlank()) {
            return List.of();
        }
        String such = verlagName.toLowerCase().trim();
        return buecher.stream()
            .filter(b -> b.getPublisher() != null
                      && b.getPublisher().toLowerCase().contains(such))
            .collect(Collectors.toList());
    }

    public Buch buchDetails(String bookId) {
        if (bookId == null) return null;
        return buecher.stream()
            .filter(b -> bookId.equals(b.getBookId()))
            .findFirst()
            .orElse(null);
    }

    public boolean reviewHinzufuegen(String bookId, Rezension rezension) {
        Buch buch = buchDetails(bookId);
        if (buch == null || rezension == null) {
            return false;
        }
        rezension.setBookId(bookId);
        storageService.speichereRezension(rezension);
        return true;
    }

    public List<Rezension> showRezensionen(String bookId) {
        return storageService.ladeRezensionen().stream()
            .filter(r -> bookId.equals(r.getBookId()))
            .collect(Collectors.toList());
    }
}

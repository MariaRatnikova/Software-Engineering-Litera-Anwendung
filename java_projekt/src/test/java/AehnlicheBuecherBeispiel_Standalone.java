import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Standalone example application that demonstrates the "Similar Books" functionality
 * with hardcoded test data. This file contains all necessary classes and requires
 * no external dependencies or JSON files.
 */
public class AehnlicheBuecherBeispiel_Standalone {

    public static void main(String[] args) {
        // Create test data and initialize catalog
        TestStorageService storageService = new TestStorageService();
        Buchkatalog katalog = new Buchkatalog(storageService);
        
        // Get all books from catalog
        List<Buch> alleBuecher = katalog.buchsuche("");
        
        // Select reference book (first book from the catalog)
        Buch referenzBuch = alleBuecher.get(0);
        
        // Display information about the reference book
        System.out.println("Reference book:");
        System.out.println("ID: " + referenzBuch.getBookId());
        System.out.println("Title: " + referenzBuch.getTitle());
        System.out.println("Author: " + referenzBuch.getAuthor());
        System.out.println("Genres: " + String.join(", ", referenzBuch.getGenres()));
        System.out.println();
        
        // Find similar books using the aehnlich() method
        List<Buch> aehnlicheBuecher = katalog.aehnlich(referenzBuch);
        
        // Display results
        System.out.println("Similar books found: " + aehnlicheBuecher.size());
        System.out.println("-------------------------------------");
        
        for (Buch buch : aehnlicheBuecher) {
            System.out.println("ID: " + buch.getBookId());
            System.out.println("Title: " + buch.getTitle());
            System.out.println("Author: " + buch.getAuthor());
            System.out.println("Genres: " + String.join(", ", buch.getGenres()));
            
            // Count and display matching genres
            long matchingGenres = buch.getGenres().stream()
                    .map(String::toLowerCase)
                    .filter(genre -> 
                        referenzBuch.getGenres().stream()
                            .map(String::toLowerCase)
                            .collect(Collectors.toList())
                            .contains(genre))
                    .count();
            
            System.out.println("Matching genres: " + matchingGenres);
            System.out.println("-------------------------------------");
        }
    }
    
    /**
     * Book class - simplified version for the example
     */
    public static class Buch {
        private String bookId;
        private String title;
        private String author;
        private String publisher;
        private List<String> genres;
        private String description;
        
        // Getters and setters
        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        
        public String getPublisher() { return publisher; }
        public void setPublisher(String publisher) { this.publisher = publisher; }
        
        public List<String> getGenres() { return genres; }
        public void setGenres(List<String> genres) { this.genres = genres; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    /**
     * Review class - simplified version for the example
     */
    public static class Rezension {
        private String bookId;
        private String text;
        private int rating;
        
        // Getters and setters
        public String getBookId() { return bookId; }
        public void setBookId(String bookId) { this.bookId = bookId; }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        
        public int getRating() { return rating; }
        public void setRating(int rating) { this.rating = rating; }
    }
    
    /**
     * Storage service interface - simplified version for the example
     */
    public interface StorageService {
        List<Buch> ladeBuecher();
        List<Rezension> ladeRezensionen();
        void speichereRezension(Rezension rezension);
    }
    
    /**
     * Test implementation of StorageService with hardcoded test data
     */
    public static class TestStorageService implements StorageService {
        private final List<Buch> testBuecher;
        private final List<Rezension> testRezensionen;
        
        public TestStorageService() {
            // Create test books with various genres
            testBuecher = Arrays.asList(
                createBuch("1", "The Hunger Games", "Suzanne Collins", 
                        Arrays.asList("Fantasy", "Young Adult", "Fiction", "Adventure", "Dystopia")),
                createBuch("2", "Harry Potter and the Order of the Phoenix", "J.K. Rowling", 
                        Arrays.asList("Fantasy", "Young Adult", "Fiction", "Adventure", "Magic")),
                createBuch("3", "To Kill a Mockingbird", "Harper Lee", 
                        Arrays.asList("Classics", "Fiction", "Historical", "Literature")),
                createBuch("4", "Pride and Prejudice", "Jane Austen", 
                        Arrays.asList("Classics", "Fiction", "Romance", "Literature")),
                createBuch("5", "Twilight", "Stephenie Meyer", 
                        Arrays.asList("Fantasy", "Young Adult", "Fiction", "Romance", "Vampires")),
                createBuch("6", "The Book Thief", "Markus Zusak", 
                        Arrays.asList("Historical", "Fiction", "Young Adult", "War")),
                createBuch("7", "Animal Farm", "George Orwell", 
                        Arrays.asList("Classics", "Fiction", "Fantasy", "Politics")),
                createBuch("8", "The Chronicles of Narnia", "C.S. Lewis", 
                        Arrays.asList("Fantasy", "Classics", "Fiction", "Adventure", "Children")),
                createBuch("9", "J.R.R. Tolkien 4-Book Boxed Set", "J.R.R. Tolkien", 
                        Arrays.asList("Fantasy", "Classics", "Fiction", "Adventure", "Epic"))
            );
            
            // Create some test reviews
            testRezensionen = new ArrayList<>();
        }
        
        private Buch createBuch(String id, String title, String author, List<String> genres) {
            Buch buch = new Buch();
            buch.setBookId(id);
            buch.setTitle(title);
            buch.setAuthor(author);
            buch.setGenres(genres);
            return buch;
        }
        
        @Override
        public List<Buch> ladeBuecher() {
            return testBuecher;
        }
        
        @Override
        public List<Rezension> ladeRezensionen() {
            return testRezensionen;
        }
        
        @Override
        public void speichereRezension(Rezension rezension) {
            testRezensionen.add(rezension);
        }
    }
    
    /**
     * Book catalog class - contains the business logic including the similar books functionality
     */
    public static class Buchkatalog {
        private final List<Buch> buecher;
        private final StorageService storage;
        
        public Buchkatalog(StorageService storageService) {
            this.storage = storageService;
            this.buecher = storageService.ladeBuecher();
        }
        
        /**
         * Searches for books by title.
         *
         * @param query Search text (case-insensitive)
         * @return List of books whose titles contain the search text
         */
        public List<Buch> buchsuche(String query) {
            String q = query == null ? "" : query.trim().toLowerCase();
            if (q.isEmpty()) {
                return buecher;
            }
            
            return buecher.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q))
                .collect(Collectors.toList());
        }
        
        /**
         * Returns a single book by its ID.
         *
         * @param id ID of the book
         * @return Book object or null if no book was found with that ID
         */
        public Buch buchDetails(String id) {
            return buecher.stream()
                .filter(b -> id != null && id.equals(b.getBookId()))
                .findFirst()
                .orElse(null);
        }
        
        /**
         * Finds similar books based on genre matches.
         * A book is considered similar if it shares at least 3 genres with the given book.
         *
         * @param buch The reference book to find similar books for
         * @return List of books that are considered similar (at least 3 matching genres)
         */
        public List<Buch> aehnlich(Buch buch) {
            // Check if the given book is valid
            if (buch == null || buch.getGenres() == null || buch.getGenres().isEmpty()) {
                return new ArrayList<>();
            }
            
            // Genres of the given book (lowercase for case-insensitive comparison)
            List<String> buchGenres = buch.getGenres().stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            
            // Minimum number of matching genres for "similarity"
            final int MIN_MATCHING_GENRES = 3;
            
            // Search for similar books in the catalog
            return buecher.stream()
                    // Exclude the reference book itself
                    .filter(b -> !b.getBookId().equals(buch.getBookId()))
                    // Only consider books with genres
                    .filter(b -> b.getGenres() != null && !b.getGenres().isEmpty())
                    // Calculate number of matching genres
                    .filter(b -> {
                        // Genres of the book to compare (lowercase)
                        List<String> otherGenres = b.getGenres().stream()
                                .map(String::toLowerCase)
                                .collect(Collectors.toList());
                        
                        // Count matching genres
                        long matchingGenres = buchGenres.stream()
                                .filter(otherGenres::contains)
                                .count();
                        
                        // Book is similar if at least MIN_MATCHING_GENRES match
                        return matchingGenres >= MIN_MATCHING_GENRES;
                    })
                    .collect(Collectors.toList());
        }
    }
}

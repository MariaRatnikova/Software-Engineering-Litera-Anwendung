

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BuchTest {

    @Test
    public void testSettersAndGetters() {
        Buch buch = new Buch();
        buch.setBookId("123");
        buch.setTitle("Test Title");
        buch.setAuthor("Test Author");
        buch.setPublisher("Test Publisher");
        buch.setGenres(List.of("Fantasy", "Drama"));
        buch.setDescription("Beschreibung");

        assertEquals("123", buch.getBookId());
        assertEquals("Test Title", buch.getTitle());
        assertEquals("Test Author", buch.getAuthor());
        assertEquals("Test Publisher", buch.getPublisher());
        assertEquals(List.of("Fantasy", "Drama"), buch.getGenres());
        assertEquals("Beschreibung", buch.getDescription());
    }
}

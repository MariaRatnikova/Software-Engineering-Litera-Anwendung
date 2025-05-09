

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class RezensionTest {

    @Test
    public void testToStringNotNull() {
        Rezension r = new Rezension("1", 4, "Nice book", new Date());
        assertNotNull(r.toString());
    }

    @Test
    public void testSetters() {
        Rezension r = new Rezension();
        r.setBookId("42");
        r.setBewertung(5);
        r.setKommentar("Excellent");
        r.setDatum(new Date());

        assertEquals("42", r.getBookId());
        assertEquals(5, r.getBewertung());
        assertEquals("Excellent", r.getKommentar());
        assertNotNull(r.getDatum());
    }
}

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class StorageServiceTest {

    @Test
    public void testLadeBuecherWithMockJson() throws Exception {
        String json = "[{\"bookId\": \"1\", \"title\": \"Testbuch\", \"author\": \"Autor X\"}]";
        File file = new File("books_short.json");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        }

        StorageService service = new StorageService();
        List<Buch> buecher = service.ladeBuecher();

        assertEquals(1, buecher.size());
        assertEquals("1", buecher.get(0).getBookId());
        assertEquals("Testbuch", buecher.get(0).getTitle());

        file.delete(); 
    }
}

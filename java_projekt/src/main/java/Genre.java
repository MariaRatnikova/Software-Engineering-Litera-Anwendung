/**
 * Represents a genre of a book, e.g., "Fantasy" or "Crime",
 * along with a short description.
 */
public class Genre {

    // Name of the genre (e.g., "Fantasy")
    private String name;

    // Description of the genre (e.g., "Magical worlds and epic adventures")
    private String description;

    /**
     * No-argument constructor – required by frameworks like Jackson
     * that create objects automatically from JSON data.
     */
    public Genre() {
    }

    /**
     * Constructor with parameters for directly setting name and description.
     *
     * @param name        Name of the genre
     * @param description Description of the genre
     */
    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the genre.
     *
     * @return Name of the genre
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the genre.
     *
     * @param name New name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the genre.
     *
     * @return Description of the genre
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the genre.
     *
     * @param description New description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns a textual representation of the genre,
     * e.g., "Fantasy: Magical worlds and epic adventures".
     *
     * @return String representation of the genre
     */
    @Override
    public String toString() {
        return name + ": " + description;
    }
}

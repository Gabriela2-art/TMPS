package solid.srp;

import java.math.BigDecimal;

public class Book {
    private final String title;
    private final String author;
    private final BigDecimal price;
    private final String category;

    public Book(String title, String author, BigDecimal price, String category) {
        this.title = requireText(title, "Title");
        this.author = requireText(author, "Author");
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price must be non-negative.");
        }
        this.price = price;
        this.category = requireText(category, "Category");
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return value.trim();
    }
}

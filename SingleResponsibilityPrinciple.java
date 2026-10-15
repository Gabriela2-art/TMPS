import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleResponsibilityPrinciple {

    public void demonstrate(Book firstBook, Book secondBook) {
        BookCatalog catalog = new BookCatalog();
        catalog.addBook(firstBook);
        catalog.addBook(secondBook);

        BookDisplay display = new BookDisplay();

        for (Book book : catalog.getBooks()) {
            display.displayBook(book);
        }
    }
}

// SRP: Book holds valid book data; it does not manage a catalog or print output.
class Book {
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

// SRP: BookCatalog manages the collection independently of presentation.
class BookCatalog {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        books.add(book);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books));
    }
}

// SRP: BookDisplay handles console presentation without changing book data.
class BookDisplay {
    public void displayBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: " + book.getPrice().toPlainString());
        System.out.println("Category: " + book.getCategory());
        System.out.println();
    }
}

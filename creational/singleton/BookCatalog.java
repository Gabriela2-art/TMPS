package creational.singleton;

import solid.srp.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookCatalog {
    // Singleton: one shared catalog for the whole application.
    private static final BookCatalog INSTANCE = new BookCatalog();
    private final List<Book> books = new ArrayList<>();

    private BookCatalog() { }

    public static BookCatalog getInstance() {
        return INSTANCE;
    }

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

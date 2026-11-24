package creational.builder;

import solid.srp.Book;

import java.math.BigDecimal;

// Builder: assembles a validated Book with a readable, step-by-step API.
public class BookBuilder {
    private String title;
    private String author;
    private BigDecimal price = new BigDecimal("0.00");
    private String category = "Uncategorized";

    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder author(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BookBuilder category(String category) {
        this.category = category;
        return this;
    }

    public Book build() {
        // Reuse Book's original validation; title and author are required.
        return new Book(title, author, price, category);
    }
}

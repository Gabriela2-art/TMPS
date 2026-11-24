package solid.srp;

import creational.singleton.BookCatalog;

public class SingleResponsibilityPrinciple {

    public void demonstrate(Book firstBook, Book secondBook) {
        BookCatalog catalog = BookCatalog.getInstance();
        catalog.addBook(firstBook);
        catalog.addBook(secondBook);

        BookDisplay display = new BookDisplay();

        for (Book book : catalog.getBooks()) {
            display.displayBook(book);
        }
    }
}

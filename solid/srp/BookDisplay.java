package solid.srp;

public class BookDisplay {
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

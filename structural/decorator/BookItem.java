package structural.decorator;
import solid.srp.Book;
import solid.ocp.DiscountStrategy;
import java.math.BigDecimal;
public class BookItem implements PurchaseItem {
    private final Book book;
    private final DiscountStrategy discount;
    public BookItem(Book book, DiscountStrategy discount) {
        this.book = book;
        this.discount = discount;
    }
    public String description() { return book.getTitle(); }
    public BigDecimal price() { return discount.applyDiscount(book.getPrice()); }
}

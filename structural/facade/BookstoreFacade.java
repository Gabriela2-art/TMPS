package structural.facade;
import structural.decorator.PurchaseItem;
import structural.decorator.BookItem;
import structural.proxy.PurchaseService;
import solid.srp.Book;
import solid.ocp.DiscountStrategy;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;
public class BookstoreFacade {
    private final PurchaseService service;
    public BookstoreFacade(PurchaseService service) { this.service = service; }
    public BigDecimal checkout(Book book, DiscountStrategy discount,
            List<UnaryOperator<PurchaseItem>> extras, String recipient) {
        PurchaseItem item = new BookItem(book, discount);
        for (UnaryOperator<PurchaseItem> extra : extras) {
            item = extra.apply(item);
        }
        return service.purchase(item, recipient);
    }
}

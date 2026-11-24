package structural.decorator;
import java.math.BigDecimal;
public class BookmarkDecorator extends ItemDecorator {
    public BookmarkDecorator(PurchaseItem wrapped) { super(wrapped); }
    public String description() { return super.description() + " + bookmark"; }
    public BigDecimal price() { return super.price().add(new BigDecimal("1.00")); }
}

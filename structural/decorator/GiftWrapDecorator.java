package structural.decorator;
import java.math.BigDecimal;
public class GiftWrapDecorator extends ItemDecorator {
    public GiftWrapDecorator(PurchaseItem wrapped) { super(wrapped); }
    public String description() { return super.description() + " + gift wrap"; }
    public BigDecimal price() { return super.price().add(new BigDecimal("2.00")); }
}

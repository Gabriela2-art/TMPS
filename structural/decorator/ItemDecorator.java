package structural.decorator;
import java.math.BigDecimal;
public abstract class ItemDecorator implements PurchaseItem {
    protected final PurchaseItem wrapped;
    protected ItemDecorator(PurchaseItem wrapped) { this.wrapped = wrapped; }
    public String description() { return wrapped.description(); }
    public BigDecimal price() { return wrapped.price(); }
}

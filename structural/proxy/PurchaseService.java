package structural.proxy;
import structural.decorator.PurchaseItem;
import java.math.BigDecimal;
public interface PurchaseService {
    BigDecimal purchase(PurchaseItem item, String recipient);
}

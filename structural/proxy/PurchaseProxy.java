package structural.proxy;
import structural.decorator.PurchaseItem;
import java.math.BigDecimal;
public class PurchaseProxy implements PurchaseService {
    private final PurchaseService realService;
    private final boolean authorized;
    public PurchaseProxy(PurchaseService realService, boolean authorized) {
        this.realService = realService;
        this.authorized = authorized;
    }
    public BigDecimal purchase(PurchaseItem item, String recipient) {
        if (!authorized) {
            throw new SecurityException("Purchase denied: unauthorized customer.");
        }
        return realService.purchase(item, recipient);
    }
}

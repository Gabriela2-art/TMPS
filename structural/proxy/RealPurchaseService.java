package structural.proxy;
import structural.decorator.PurchaseItem;
import solid.dip.NotificationService;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class RealPurchaseService implements PurchaseService {
    private final NotificationService notification;
    public RealPurchaseService(NotificationService notification) {
        this.notification = notification;
    }
    public BigDecimal purchase(PurchaseItem item, String recipient) {
        BigDecimal total = item.price().setScale(2, RoundingMode.HALF_UP);
        notification.sendNotification(recipient,
            "Purchase confirmed: " + item.description() + "; total: " + total);
        return total;
    }
}

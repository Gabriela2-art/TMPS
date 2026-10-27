package creational.factorymethod;

import solid.dip.DependencyInversionPrinciple;
import solid.dip.NotificationService;
import solid.srp.Book;

public abstract class NotificationFactory {
    public abstract NotificationService createNotificationService();

    public void confirmPurchase(String recipient, Book book) {
        NotificationService notification = createNotificationService();
        DependencyInversionPrinciple service = new DependencyInversionPrinciple(notification);
        service.notifyBookPurchased(recipient, book);
    }
}

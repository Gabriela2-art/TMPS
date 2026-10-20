// Factory Method: subclasses decide which NotificationService to instantiate.
public abstract class NotificationFactory {
    public abstract NotificationService createNotificationService();

    public void confirmPurchase(String recipient, Book book) {
        NotificationService notification = createNotificationService();
        DependencyInversionPrinciple service = new DependencyInversionPrinciple(notification);
        service.notifyBookPurchased(recipient, book);
    }
}

class EmailNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new EmailNotification();
    }
}

class SMSNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new SMSNotification();
    }
}

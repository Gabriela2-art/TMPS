package creational.factorymethod;

import solid.dip.EmailNotification;
import solid.dip.NotificationService;

public class EmailNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new EmailNotification();
    }
}

package creational.factorymethod;

import solid.dip.NotificationService;
import solid.dip.SMSNotification;

public class SMSNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new SMSNotification();
    }
}

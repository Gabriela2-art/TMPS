package solid.dip;

public class SMSNotification implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("SMS to " + recipient + ": " + message);
    }
}

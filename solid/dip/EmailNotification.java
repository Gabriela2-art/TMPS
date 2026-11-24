package solid.dip;

public class EmailNotification implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("Email to " + recipient + ": " + message);
    }
}

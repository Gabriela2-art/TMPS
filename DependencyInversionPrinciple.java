// This service handles book purchase notifications.
public class DependencyInversionPrinciple {
    private final NotificationService notificationService;

    // DIP: The notification implementation is supplied through the abstraction.
    public DependencyInversionPrinciple(NotificationService notificationService) {
        if (notificationService == null) {
            throw new IllegalArgumentException("Notification service must not be null.");
        }
        this.notificationService = notificationService;
    }

    public void notifyBookPurchased(String recipient, Book book) {
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient must not be blank.");
        }
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        String message = "Your purchase of \"" + book.getTitle()
                + "\" by " + book.getAuthor() + " is confirmed.";
        notificationService.sendNotification(recipient.trim(), message);
    }
}

// DIP: Both the service and delivery implementations use this contract.
interface NotificationService {
    void sendNotification(String recipient, String message);
}

// Delivery is simulated in the console for this laboratory.
class EmailNotification implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("Email to " + recipient + ": " + message);
    }
}

class SMSNotification implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("SMS to " + recipient + ": " + message);
    }
}

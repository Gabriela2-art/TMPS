package solid.dip;

import solid.srp.Book;

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

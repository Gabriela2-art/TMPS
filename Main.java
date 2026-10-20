import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // Builder: create the same domain objects as in Lab 0.
        Book throneOfGlass = new BookBuilder()
                .title("Throne of Glass")
                .author("Sarah J. Maas")
                .price(new BigDecimal("20.00"))
                .category("Fantasy")
                .build();

        Book theSixthFaction = new BookBuilder()
                .title("The Sixth Faction")
                .author("Veronica Roth")
                .price(new BigDecimal("22.00"))
                .category("Dystopian")
                .build();

        System.out.println("=== Lab 0: Single Responsibility Principle ===");
        SingleResponsibilityPrinciple srp = new SingleResponsibilityPrinciple();
        srp.demonstrate(throneOfGlass, theSixthFaction);

        System.out.println("=== Lab 0: Open/Closed Principle ===");
        OpenClosedPrinciple discountCalculator = new OpenClosedPrinciple();
        DiscountStrategy noDiscount = new NoDiscount();
        DiscountStrategy studentDiscount = new StudentDiscount();

        System.out.println("Book: " + throneOfGlass.getTitle());
        System.out.println("NoDiscount price: "
                + discountCalculator.calculateDiscountedPrice(throneOfGlass, noDiscount).toPlainString());
        System.out.println("StudentDiscount price (10% off): "
                + discountCalculator.calculateDiscountedPrice(throneOfGlass, studentDiscount).toPlainString());
        System.out.println();

        System.out.println("=== Lab 0: Dependency Inversion Principle ===");
        DependencyInversionPrinciple emailService =
                new DependencyInversionPrinciple(new EmailNotification());
        emailService.notifyBookPurchased("reader@example.com", throneOfGlass);
        DependencyInversionPrinciple smsService =
                new DependencyInversionPrinciple(new SMSNotification());
        smsService.notifyBookPurchased("+37360000000", theSixthFaction);
        System.out.println();

        System.out.println("=== Lab 1: Singleton ===");
        BookCatalog catalogA = BookCatalog.getInstance();
        BookCatalog catalogB = BookCatalog.getInstance();
        System.out.println("Same catalog instance: " + (catalogA == catalogB));
        System.out.println("Books in shared catalog: " + catalogB.getBooks().size());
        System.out.println();

        System.out.println("=== Lab 1: Builder ===");
        BookDisplay display = new BookDisplay();
        display.displayBook(throneOfGlass);
        Book newArrival = new BookBuilder()
                .title("A New Arrival")
                .author("Example Author")
                .category("Fiction")
                .build(); // Default price is 0.00
        display.displayBook(newArrival);

        System.out.println("=== Lab 1: Factory Method ===");
        NotificationFactory emailFactory = new EmailNotificationFactory();
        NotificationFactory smsFactory = new SMSNotificationFactory();
        emailFactory.confirmPurchase("reader@example.com", throneOfGlass);
        smsFactory.confirmPurchase("+37360000000", theSixthFaction);
    }
}

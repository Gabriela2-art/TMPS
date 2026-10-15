import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Book throneOfGlass = new Book("Throne of Glass", "Sarah J. Maas",
                new BigDecimal("20.00"), "Fantasy");
        Book theSixthFaction = new Book("The Sixth Faction", "Veronica Roth",
                new BigDecimal("22.00"), "Dystopian");

        System.out.println("=== Single Responsibility Principle ===");
        SingleResponsibilityPrinciple srp =
                new SingleResponsibilityPrinciple();
        srp.demonstrate(throneOfGlass, theSixthFaction);

        System.out.println("=== Open/Closed Principle ===");
        OpenClosedPrinciple discountCalculator = new OpenClosedPrinciple();
        DiscountStrategy noDiscount = new NoDiscount();
        DiscountStrategy studentDiscount = new StudentDiscount();

        System.out.println("Book: " + throneOfGlass.getTitle());
        System.out.println("NoDiscount price: "
                + discountCalculator.calculateDiscountedPrice(throneOfGlass, noDiscount).toPlainString());
        System.out.println("StudentDiscount price (10% off): "
                + discountCalculator.calculateDiscountedPrice(throneOfGlass, studentDiscount).toPlainString());
        System.out.println();

        System.out.println("=== Dependency Inversion Principle ===");
        NotificationService emailNotification = new EmailNotification();
        NotificationService smsNotification = new SMSNotification();

        DependencyInversionPrinciple emailService =
                new DependencyInversionPrinciple(emailNotification);
        DependencyInversionPrinciple smsService =
                new DependencyInversionPrinciple(smsNotification);

        emailService.notifyBookPurchased("reader@example.com", throneOfGlass);
        smsService.notifyBookPurchased("+37360000000", theSixthFaction);
    }
}
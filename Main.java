import creational.builder.BookBuilder;
import creational.factorymethod.EmailNotificationFactory;
import creational.factorymethod.NotificationFactory;
import creational.factorymethod.SMSNotificationFactory;
import creational.singleton.BookCatalog;
import solid.dip.DependencyInversionPrinciple;
import solid.dip.EmailNotification;
import solid.dip.SMSNotification;
import solid.ocp.DiscountStrategy;
import solid.ocp.NoDiscount;
import solid.ocp.OpenClosedPrinciple;
import solid.ocp.StudentDiscount;
import solid.srp.Book;
import solid.srp.BookDisplay;
import solid.srp.SingleResponsibilityPrinciple;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("Alege laboratorul pe care vrei sa il rulezi:");
            System.out.println("0 - Laboratorul 0 (principii SOLID)");
            System.out.println("1 - Laboratorul 1 (design patterns creationale)");
            System.out.println("2 - Iesire");
            System.out.print("Optiunea: ");

            String option = scanner.nextLine().trim();
            switch (option) {
                case "0":
                    runLab0();
                    break;
                case "1":
                    runLab1();
                    break;
                case "2":
                    System.out.println("Programul s-a incheiat.");
                    return;
                default:
                    System.out.println("Optiune invalida. Alege 0, 1 sau 2.");
            }
        }
    }

    private static void runLab0() {
        Book[] books = createBooks();
        Book throneOfGlass = books[0];
        Book theSixthFaction = books[1];

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
    }

    private static void runLab1() {
        Book[] books = createBooks();
        Book throneOfGlass = books[0];
        Book theSixthFaction = books[1];

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

    private static Book[] createBooks() {
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

        return new Book[]{throneOfGlass, theSixthFaction};
    }
}

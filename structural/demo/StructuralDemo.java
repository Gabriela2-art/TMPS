package structural.demo;
import creational.builder.BookBuilder;
import creational.factorymethod.EmailNotificationFactory;
import creational.factorymethod.NotificationFactory;
import structural.decorator.*;
import structural.facade.BookstoreFacade;
import structural.proxy.*;
import solid.srp.Book;
import solid.ocp.StudentDiscount;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.UnaryOperator;
public class StructuralDemo {
    public static void run() {
        Book book = new BookBuilder().title("Throne of Glass").author("Sarah J. Maas")
            .category("Fantasy").price(new BigDecimal("20.00")).build();
        NotificationFactory factory = new EmailNotificationFactory();
        PurchaseService real = new RealPurchaseService(factory.createNotificationService());
        List<UnaryOperator<PurchaseItem>> extras = List.of(GiftWrapDecorator::new, BookmarkDecorator::new);
        System.out.println();
        System.out.println("========================================");
        System.out.println("Lab 2: Structural Design Patterns");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Book: 20.00 | Student discount: 10% | Gift wrap: 2.00 | Bookmark: 1.00");
        BookstoreFacade facade = new BookstoreFacade(new PurchaseProxy(real, true));
        System.out.println("Final total: " + facade.checkout(book, new StudentDiscount(), extras, "reader@example.com"));
        System.out.println();
        try {
            new BookstoreFacade(new PurchaseProxy(real, false))
                .checkout(book, new StudentDiscount(), extras, "guest@example.com");
        } catch (SecurityException e) { System.out.println(e.getMessage()); }
    }
}

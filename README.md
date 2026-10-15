# SOLID Principles

### Course: Techniques and Mechanisms of Software Design
### Author: Botezatu Gabriela

----

## Theory
SOLID is a set of five object-oriented design principles introduced by Robert C. Martin. Their purpose is to make software easier to understand, extend and maintain by reducing the coupling between components and keeping each part of the system focused on a clear task. The acronym stands for Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation and Dependency Inversion.

The **Single Responsibility Principle (SRP)** states that a class should have only one reason to change. When a class mixes several concerns, such as storing data, managing a collection and printing output, a change in one concern can unintentionally break another. Splitting these concerns into separate classes keeps each class small and predictable.

The **Open/Closed Principle (OCP)** states that software entities should be open for extension but closed for modification. New behaviour should be added by writing new code, for example a new class implementing an existing interface, instead of editing code that already works and has been tested. This is usually achieved through abstractions and polymorphism.

The **Dependency Inversion Principle (DIP)** states that high-level modules should not depend on low-level modules; both should depend on abstractions. Instead of a service creating a concrete implementation itself, the implementation is supplied from the outside through an interface. This makes the service independent of the concrete technology and allows implementations to be replaced easily.

In this laboratory work, three of these principles (SRP, OCP and DIP) are applied in a small bookstore domain: managing books, calculating discounted prices and sending purchase notifications.


## Objectives:

* Understand the SOLID principles and the problems they help to avoid in object-oriented design.

* Apply the Single Responsibility Principle by separating book data, catalog management and presentation.

* Apply the Open/Closed Principle by implementing discounts as interchangeable strategies.

* Apply the Dependency Inversion Principle by making the notification service depend on an abstraction instead of a concrete delivery channel.


## Implementation description

**Single Responsibility Principle**

* The SRP part is split into three classes, each with a single responsibility. The Book class only holds and validates book data (title, author, price, category). The BookCatalog class manages the collection of books and exposes it as an unmodifiable list, so it cannot be changed from outside. The BookDisplay class is responsible only for printing a book to the console. In this way, a change in the output format affects only BookDisplay, while a change in how books are stored affects only BookCatalog.
```
class BookCatalog {
    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book must not be null.");
        }
        books.add(book);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books));
    }
}

class BookDisplay {
    public void displayBook(Book book) {
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Price: " + book.getPrice().toPlainString());
        System.out.println("Category: " + book.getCategory());
    }
}
```

**Open/Closed Principle**

* The OCP part defines the DiscountStrategy interface with a single method applyDiscount(). Each type of discount is a separate class implementing this interface: NoDiscount returns the original price, while StudentDiscount applies a 10% reduction. The OpenClosedPrinciple class calculates the final price using any strategy it receives, without knowing which concrete discount is used. To add a new discount type, it is enough to create a new class implementing DiscountStrategy, without modifying the existing calculator. BigDecimal is used for prices to avoid floating-point rounding errors.
```
interface DiscountStrategy {
    BigDecimal applyDiscount(BigDecimal price);
}

class StudentDiscount implements DiscountStrategy {
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price.multiply(BigDecimal.ONE.subtract(DISCOUNT_RATE));
    }
}

public BigDecimal calculateDiscountedPrice(Book book, DiscountStrategy strategy) {
    return strategy.applyDiscount(book.getPrice()).setScale(2, RoundingMode.HALF_UP);
}
```

**Dependency Inversion Principle**

* The DIP part introduces the NotificationService interface, which defines the contract for sending a message. The high-level class DependencyInversionPrinciple, which builds the purchase confirmation message, does not create a concrete notification object; instead, it receives a NotificationService through its constructor. The low-level classes EmailNotification and SMSNotification implement the same interface, and in this laboratory the delivery is simulated in the console. As a result, the service can switch between email and SMS (or any future channel) without any change in its code.
```
interface NotificationService {
    void sendNotification(String recipient, String message);
}

public DependencyInversionPrinciple(NotificationService notificationService) {
    if (notificationService == null) {
        throw new IllegalArgumentException("Notification service must not be null.");
    }
    this.notificationService = notificationService;
}

public void notifyBookPurchased(String recipient, Book book) {
    String message = "Your purchase of \"" + book.getTitle()
            + "\" by " + book.getAuthor() + " is confirmed.";
    notificationService.sendNotification(recipient.trim(), message);
}
```

**Main class**

* The Main class demonstrates all three principles. It creates two books, displays them through the SRP classes, calculates the price of a book with and without the student discount, and then sends purchase notifications using two services configured with different notification implementations (email and SMS).
```
public static void main(String[] args) {
    Book throneOfGlass = new Book("Throne of Glass", "Sarah J. Maas",
            new BigDecimal("20.00"), "Fantasy");

    OpenClosedPrinciple discountCalculator = new OpenClosedPrinciple();
    discountCalculator.calculateDiscountedPrice(throneOfGlass, new StudentDiscount());

    DependencyInversionPrinciple emailService =
            new DependencyInversionPrinciple(new EmailNotification());
    emailService.notifyBookPurchased("reader@example.com", throneOfGlass);
}
```

## Results
The program was executed from the Main class. In the first section, the two books are added to the catalog and printed one by one by the BookDisplay class, showing that data storage and presentation are handled separately.

In the second section, the price of "Throne of Glass" is calculated with two different strategies: without a discount the price remains 20.00, and with the student discount it becomes 18.00. Both results are obtained through the same calculator method, which confirms that new discount types can be plugged in without modifying it.

In the third section, the same service class sends one notification by email and another by SMS, depending only on the implementation passed to its constructor.

A screenshot of the console output is provided below.




## Conclusions

In this laboratory work, three fundamental SOLID principles were successfully implemented and demonstrated through a Java-based book management application: the Single Responsibility Principle (SRP), the Open/Closed Principle (OCP), and the Dependency Inversion Principle (DIP). The main objective was to understand how these principles contribute to the development of well-structured, maintainable, and extensible object-oriented software.

The Single Responsibility Principle was applied by separating book information, catalog management, and console presentation into independent classes. Each class was assigned a specific responsibility, making the application easier to understand, modify, and maintain. This separation also reduces the risk of introducing errors when changes are made to individual components.

The Open/Closed Principle was demonstrated through a flexible discount calculation system. By using an interface and different discount strategies, the application can support additional discount types without modifying the existing calculation logic. This approach improves extensibility and demonstrates how polymorphism can be used to introduce new functionality while preserving existing behavior.

The Dependency Inversion Principle was implemented through a notification system that supports different communication methods, such as email and SMS. Instead of depending directly on concrete notification classes, the service relies on an abstraction. As a result, notification implementations can be replaced or extended without changing the main service logic, reducing coupling between components.

The execution of the application demonstrated how these three principles work together in a practical scenario. The console output illustrated the separation of responsibilities, the application of different discount strategies, and the use of interchangeable notification services.

Overall, this laboratory work provided practical experience with important object-oriented design concepts, including abstraction, interfaces, polymorphism, and separation of concerns. The implementation showed that applying SOLID principles helps produce cleaner, more flexible, and easier-to-test code. These concepts establish a strong foundation for developing larger applications and understanding the design patterns that will be studied in future laboratory works.

## References
1. Robert C. Martin. Agile Software Development, Principles, Patterns, and Practices. Prentice Hall, 2002

2. Robert C. Martin. Clean Architecture: A Craftsman's Guide to Software Structure and Design. Prentice Hall, 2017

3. Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides. Design Patterns: Elements of Reusable Object-Oriented Software. Addison-Wesley, 1994



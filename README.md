# Creational Design Patterns

### Course: Techniques and Mechanisms of Software Design
### Laboratory Work 1

----

## Theory

Creational design patterns describe different ways of creating objects. Their purpose is to hide object-construction details, reduce coupling between classes and make the application easier to extend and maintain.

This laboratory work demonstrates three creational patterns in a small bookstore application: Singleton, Builder and Factory Method. The implementation also preserves the SOLID principles demonstrated in the previous laboratory work: Single Responsibility, Open/Closed and Dependency Inversion.

The **Singleton pattern** ensures that a class has only one instance and provides a global access point to it. In this project, the book catalog is shared throughout the application, so every caller works with the same collection.

The **Builder pattern** separates the construction of a complex object from its representation. It allows a `Book` object to be created step by step through a readable fluent interface. The same builder can also provide default values for optional fields.

The **Factory Method pattern** defines a method for creating an object, while allowing subclasses to decide which concrete implementation should be instantiated. In this project, different notification factories create email or SMS notification services without changing the purchase-confirmation workflow.

## Objectives

* Understand the purpose and structure of the Singleton, Builder and Factory Method patterns.

* Apply the Singleton pattern to maintain one shared book catalog.

* Apply the Builder pattern to construct validated `Book` objects step by step.

* Apply the Factory Method pattern to create interchangeable email and SMS notification services.

* Reuse the SOLID abstractions from the previous laboratory work in a larger bookstore example.

## Implementation description

**Singleton pattern**

* The `BookCatalog` class manages the collection of books and exposes one shared instance through `BookCatalog.getInstance()`. Its constructor is private, while the single instance is stored in a private static field. The catalog returns an unmodifiable copy of its books, preventing external code from changing the collection directly.

```java
class BookCatalog {
        private static final BookCatalog INSTANCE = new BookCatalog();
        private final List<Book> books = new ArrayList<>();

        private BookCatalog() { }

        public static BookCatalog getInstance() {
                return INSTANCE;
        }
}
```

The `Main` class obtains the catalog twice and compares the references. Both references point to the same object, which demonstrates the Singleton behavior.

```java
BookCatalog catalogA = BookCatalog.getInstance();
BookCatalog catalogB = BookCatalog.getInstance();
System.out.println("Same catalog instance: " + (catalogA == catalogB));
```

**Builder pattern**

* The `BookBuilder` class provides a fluent API for creating `Book` objects. The title, author, price and category can be configured through separate methods before calling `build()`. The builder uses `0.00` as the default price and `Uncategorized` as the default category. Final validation is delegated to the existing `Book` constructor.

```java
Book book = new BookBuilder()
                .title("Throne of Glass")
                .author("Sarah J. Maas")
                .price(new BigDecimal("20.00"))
                .category("Fantasy")
                .build();
```

This approach makes object creation easier to read and avoids a constructor with many parameters. It also allows a book with default values to be created when only the required fields are supplied.

**Factory Method pattern**

* The `NotificationFactory` class declares the `createNotificationService()` factory method. `EmailNotificationFactory` creates an `EmailNotification`, while `SMSNotificationFactory` creates an `SMSNotification`. The common `confirmPurchase()` method does not depend on a concrete delivery class; it uses the service returned by the factory method.

```java
public abstract class NotificationFactory {
        public abstract NotificationService createNotificationService();

        public void confirmPurchase(String recipient, Book book) {
                NotificationService notification = createNotificationService();
                DependencyInversionPrinciple service =
                                new DependencyInversionPrinciple(notification);
                service.notifyBookPurchased(recipient, book);
        }
}
```

The notification implementations simulate delivery by printing the message to the console. A new channel can be added by creating another `NotificationService` implementation and another factory, without modifying the existing confirmation logic.

**Existing SOLID implementation**

* The project keeps the three principles from Lab 0. `Book`, `BookCatalog` and `BookDisplay` have separate responsibilities. `OpenClosedPrinciple` calculates prices through interchangeable `DiscountStrategy` implementations such as `NoDiscount` and `StudentDiscount`. `DependencyInversionPrinciple` receives a `NotificationService` abstraction through its constructor, so it can work with either email or SMS delivery.

## Main class

* The `Main` class demonstrates both laboratories. It creates two books with the Builder, displays them through the SRP classes, calculates prices with two discount strategies, sends notifications through the DIP service, verifies the shared Singleton catalog, creates a book with default values and sends notifications through both Factory Method implementations.

```java
NotificationFactory emailFactory = new EmailNotificationFactory();
NotificationFactory smsFactory = new SMSNotificationFactory();

emailFactory.confirmPurchase("reader@example.com", throneOfGlass);
smsFactory.confirmPurchase("+37360000000", theSixthFaction);
```

## Results

The program was executed from the `Main` class. The first sections demonstrate the SOLID principles from Lab 0: two books are displayed, the student discount changes the price from `20.00` to `18.00`, and notifications are sent through email and SMS.

In the Singleton section, both catalog references point to the same instance and the shared catalog contains two books. This confirms that only one catalog object is used by the application.

In the Builder section, `Throne of Glass` is displayed with all explicitly configured fields. `A New Arrival` is created without a price, so the builder supplies the default value `0.00`.

In the Factory Method section, the email factory sends an email notification and the SMS factory sends an SMS notification. The confirmation logic is shared, while the concrete notification service is selected by the factory subclass.

A sample of the console output is shown below:

```text
=== Lab 1: Singleton ===
Same catalog instance: true
Books in shared catalog: 2

=== Lab 1: Factory Method ===
Email to reader@example.com: Your purchase of "Throne of Glass" by Sarah J. Maas is confirmed.
SMS to +37360000000: Your purchase of "The Sixth Faction" by Veronica Roth is confirmed.
```

## Files

* `Main.java` - entry point and demonstrations of Lab 0 and Lab 1.
* `SingleResponsibilityPrinciple.java` - book data, catalog, display and Singleton implementation.
* `OpenClosedPrinciple.java` - discount strategies and price calculation.
* `DependencyInversionPrinciple.java` - notification abstraction and concrete delivery services.
* `BookBuilder.java` - Builder implementation for creating books.
* `NotificationFactory.java` - Factory Method implementation for notifications.
* `output.txt` - sample execution output.

## Conclusions

This laboratory work provided a practical demonstration of three important creational design patterns: Singleton, Builder and Factory Method. The patterns were implemented in a Java-based bookstore application and integrated with the SOLID principles developed in the previous laboratory work. The application is intentionally small, but it illustrates how design patterns can solve common object-oriented design problems in a clear and reusable way.

The **Singleton pattern** was applied to the `BookCatalog` class. By using a private constructor and a single static instance, the application ensures that all parts of the program access the same catalog. This is useful when the application must maintain one shared collection of books and when creating multiple independent catalog objects would lead to inconsistent data. The demonstration in `Main` confirms that two calls to `BookCatalog.getInstance()` return the same object. At the same time, the catalog protects its internal collection by returning an unmodifiable copy, which prevents accidental changes from outside the class.

The **Builder pattern** was used to create `Book` objects. A book contains several fields, including title, author, price and category. Instead of relying on a long constructor or a sequence of unclear parameters, the builder allows each value to be specified through a fluent method. This makes the construction code easier to read and understand. The builder also supplies default values for optional information, such as the price and category, while the `Book` constructor remains responsible for validating the completed object. Consequently, object creation and object validation remain organized without duplicating the domain rules.

The **Factory Method pattern** was implemented through `NotificationFactory` and its concrete subclasses. The email and SMS factories decide which notification service must be created, while the shared `confirmPurchase()` method only works with the `NotificationService` abstraction. This removes the need for the confirmation workflow to know how a particular notification channel is constructed. If a new channel, such as a push notification or a messaging application, is added later, it can be introduced through a new implementation and a new factory without changing the existing purchase-confirmation logic.

The three patterns also reinforce the **SOLID principles** from Lab 0. The Single Responsibility Principle is respected because book data, catalog management and book presentation are handled by different classes. The Open/Closed Principle is demonstrated by the discount strategies: new discount types can be added by implementing `DiscountStrategy` instead of modifying the price calculator. The Dependency Inversion Principle is visible in the notification service, which depends on the `NotificationService` interface rather than directly on email or SMS classes. These principles and patterns work together to reduce coupling and keep responsibilities separated.

The execution results confirm the expected behavior of the application. Two books are added to the shared catalog and displayed correctly. The discount calculator produces both the original price and the student-discounted price using the same method. The Builder creates a complete book as well as a book that uses default values. Finally, the Factory Method creates the correct email and SMS services, and both services send the expected purchase confirmations to the console.

One important advantage of this design is extensibility. The existing code can be expanded without rewriting the central application logic. For example, a new `HolidayDiscount` can be added as another discount strategy, a `PushNotification` can implement `NotificationService`, and a corresponding factory can create it. Similarly, more book configuration options can be added to the builder while keeping the construction code readable. This makes the application a useful foundation for future laboratory work and for more complex bookstore features.

The implementation also improves testability. Classes that depend on abstractions can receive alternative implementations during tests. For example, a test double can implement `NotificationService` and record messages instead of printing them. The discount calculator can be tested with small independent strategies, and the builder can be tested with valid and invalid combinations of values. Separating these responsibilities makes failures easier to isolate and reduces the amount of code that must be examined when a problem occurs.

Although the Singleton pattern is appropriate for demonstrating a shared catalog in this laboratory, it should be used carefully in larger applications because global state can make tests and program behavior harder to control. In a production system, dependency injection or an application-level repository might be preferable. The example therefore demonstrates the structure and purpose of the pattern without suggesting that Singleton is the correct solution for every shared-resource problem.

Overall, the laboratory work shows that design patterns are not isolated code templates. They are reusable design ideas that help organize responsibilities, hide implementation details and make future changes less risky. The bookstore application became easier to understand because each class has a focused role, easier to extend because behavior is selected through interfaces and factories, and easier to test because dependencies can be replaced. These concepts provide a strong foundation for studying structural and behavioral design patterns in subsequent laboratory works.

## References

1. Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides. *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley, 1994.

2. Robert C. Martin. *Agile Software Development, Principles, Patterns, and Practices*. Prentice Hall, 2002.

3. Robert C. Martin. *Clean Architecture: A Craftsman's Guide to Software Structure and Design*. Prentice Hall, 2017.

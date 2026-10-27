# Creational Design Patterns

### Course: Techniques and Mechanisms of Software Design

### Author: Botezatu Gabriela

---

## Theory

Creational design patterns describe different ways of creating objects in object-oriented software. Their purpose is to hide object-construction details, reduce coupling between classes and make object creation more flexible. Instead of creating every object directly in the main application logic, these patterns provide reusable solutions for controlling how objects are instantiated. This improves maintainability because changes to the construction process can be isolated from the code that uses the created objects.

The **Singleton Pattern** ensures that a class has only one instance and provides a global access point to that instance. This pattern is useful when an application must coordinate access to one shared resource. In the bookstore application, the `BookCatalog` is represented as a Singleton so that all parts of the program use the same catalog instead of creating independent collections.

The **Builder Pattern** separates the construction of a complex object from its representation. It allows an object to be created step by step through a readable fluent interface. In this project, `BookBuilder` constructs validated `Book` objects by allowing the title, author, price and category to be specified separately. The builder also supplies default values for optional fields, which makes object creation more flexible.

The **Factory Method Pattern** defines a method for creating an object while allowing subclasses to decide which concrete implementation should be instantiated. In the bookstore application, `NotificationFactory` defines the common purchase-confirmation workflow, while `EmailNotificationFactory` and `SMSNotificationFactory` create different notification services. The client code therefore depends on abstractions rather than concrete notification classes.

These patterns extend the bookstore application developed in Lab 0 using the SOLID principles. The existing book model, discount strategies and notification abstractions are reused, while the creational patterns improve the way catalog objects, books and notification services are created. As a result, the application keeps responsibilities separated and becomes easier to extend without changing its central logic.

## Objectives:

* Understand the purpose and structure of the Singleton, Builder and Factory Method patterns.

* Apply the Singleton pattern to maintain one shared book catalog.

* Apply the Builder pattern to construct validated `Book` objects step by step.

* Apply the Factory Method pattern to create interchangeable email and SMS notification services.

* Use creational patterns together with the SOLID abstractions developed in Lab 0.

* Improve the flexibility, maintainability and reusability of the bookstore application.

## Implementation description

**Singleton Pattern**

* The `BookCatalog` class manages the collection of books and exposes one shared instance through `BookCatalog.getInstance()`. Its constructor is private, which prevents other classes from creating catalog objects directly. The single instance is stored in the private static `INSTANCE` field, and the public `getInstance()` method provides the global access point required by the Singleton pattern.

```java
public class BookCatalog {
    private static final BookCatalog INSTANCE = new BookCatalog();
    private final List<Book> books = new ArrayList<>();

    private BookCatalog() { }

    public static BookCatalog getInstance() {
        return INSTANCE;
    }
}
```

The catalog also exposes its books through an unmodifiable copy. This protects the internal collection from being changed directly by external code while preserving the shared catalog behavior.

**Builder Pattern**

* The `BookBuilder` class constructs `Book` objects through a fluent, step-by-step API. Each configuration method sets one field and returns `this`, so several method calls can be chained in a single expression. The builder initializes the optional price with `0.00` and the optional category with `Uncategorized`. When `build()` is called, it creates the final `Book` object and delegates validation to the `Book` constructor.

```java
public class BookBuilder {
    private String title;
    private String author;
    private BigDecimal price = new BigDecimal("0.00");
    private String category = "Uncategorized";

    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder author(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BookBuilder category(String category) {
        this.category = category;
        return this;
    }

    public Book build() {
        return new Book(title, author, price, category);
    }
}
```

This approach avoids a constructor with many parameters and makes the meaning of each value clear at the call site. It also permits a complete book to be created with explicit values or with only the required fields, while the builder supplies the appropriate defaults.

**Factory Method Pattern**

* The abstract `NotificationFactory` class declares the `createNotificationService()` factory method. Its `confirmPurchase()` method uses the service returned by that method and delegates the notification operation to `DependencyInversionPrinciple`. The concrete `EmailNotificationFactory` returns an `EmailNotification`, while `SMSNotificationFactory` returns an `SMSNotification`.

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

The factory method integrates with the existing `NotificationService` interface and the Dependency Inversion Principle from Lab 0. The common workflow depends on the interface rather than on a concrete email or SMS implementation. Consequently, the construction decision is moved to the appropriate factory subclass, and the purchase-confirmation logic remains unchanged when a different notification channel is introduced.

**Main class**

* The `Main` class provides a console menu that allows the user to select Lab 0 or Lab 1. When Lab 1 is selected, it creates books with `BookBuilder`, obtains the shared catalog through `BookCatalog.getInstance()`, and confirms purchases through the email and SMS factories. The examples use the same bookstore objects and notification abstractions established in the previous laboratory.

```java
BookCatalog catalogA = BookCatalog.getInstance();
BookCatalog catalogB = BookCatalog.getInstance();
System.out.println("Same catalog instance: " + (catalogA == catalogB));

Book throneOfGlass = new BookBuilder()
        .title("Throne of Glass")
        .author("Sarah J. Maas")
        .price(new BigDecimal("20.00"))
        .category("Fantasy")
        .build();

NotificationFactory emailFactory = new EmailNotificationFactory();
NotificationFactory smsFactory = new SMSNotificationFactory();
emailFactory.confirmPurchase("reader@example.com", throneOfGlass);
smsFactory.confirmPurchase("+37360000000", theSixthFaction);
```

## Results

The Singleton demonstration creates two references by calling `BookCatalog.getInstance()` twice. The console output confirms that both references point to the same instance through the expression `catalogA == catalogB`, which produces `true`. The application also reads the collection through the second reference, demonstrating that both references access one coordinated catalog object.

The Builder demonstration creates `Throne of Glass` by chaining the `title`, `author`, `price` and `category` methods before calling `build()`. It also creates `A New Arrival` without explicitly setting a price. In this case, the builder supplies the default price `0.00`, while the explicitly supplied title, author and category are preserved. The resulting objects are validated and displayed as regular `Book` instances.

The Factory Method demonstration creates two separate factories, `EmailNotificationFactory` and `SMSNotificationFactory`. The email factory creates an `EmailNotification`, while the SMS factory creates an `SMSNotification`. Both factories use the same `confirmPurchase()` workflow, but the resulting console messages are sent through their respective notification implementations. The output therefore contains one email confirmation and one SMS confirmation without requiring the main application to instantiate those concrete services directly.

The application displays the results after the user selects Lab 1 from the console menu. The menu also allows Lab 0 to be executed independently, so the output of the selected laboratory is shown without automatically running both laboratories in sequence.

<img width="492" height="373" alt="image" src="https://github.com/user-attachments/assets/ea24df0c-6f27-496b-817a-d613f8cea4b4" />


## Conclusions

This laboratory work provided a practical demonstration of three important creational design patterns: Singleton, Builder and Factory Method. The patterns were implemented in a Java-based bookstore application and integrated with the SOLID principles developed in Lab 0. The application illustrates how object creation can be organized separately from the business logic that uses the created objects.

The **Singleton pattern** was applied to the `BookCatalog` class because the bookstore requires one shared collection of books. The private constructor prevents uncontrolled creation of catalog objects, while the static `INSTANCE` field and `getInstance()` method provide a single access point. This guarantees that different parts of the application work with the same catalog state. The demonstration also shows that the Singleton protects its internal collection by returning an unmodifiable copy. Although Singleton is suitable for the shared catalog example, it should be used carefully in larger systems because global state can make testing and dependency management more difficult.

The **Builder pattern** was chosen for creating `Book` objects because a book contains several fields and some of them are optional. The fluent methods make the construction process readable and allow each property to be configured independently. Returning `this` from every configuration method enables method chaining, while default values make it possible to omit optional information. The `build()` method creates the final object and reuses the validation already implemented in `Book`, so construction logic and domain validation remain organized without unnecessary duplication.

The **Factory Method pattern** was implemented through `NotificationFactory`, `EmailNotificationFactory` and `SMSNotificationFactory`. This pattern was chosen because the application supports multiple notification channels that follow the same purchase-confirmation process. The abstract factory defines the creation contract, while each concrete subclass selects the appropriate notification service. The main workflow therefore depends on the `NotificationService` interface and does not need to know how email or SMS objects are instantiated. This makes the design easier to extend with another notification channel while keeping the existing confirmation logic unchanged.

The three patterns reinforce the **SOLID principles** from Lab 0. The Single Responsibility Principle is supported because book data, catalog management, object construction and notification creation are handled by focused classes. The Open/Closed Principle is reflected in the ability to add new builders or notification factories without modifying the existing workflows. The Dependency Inversion Principle is visible in the use of `NotificationService`, since the purchase-confirmation logic depends on an abstraction rather than directly on `EmailNotification` or `SMSNotification`.

The execution results confirm the expected behavior of the application. The two catalog references point to the same Singleton instance. The Builder creates books through readable method chaining and supplies the default price when that value is omitted. The Factory Method creates the correct email and SMS notification services through separate factory subclasses, and both services produce the expected console confirmations. These results demonstrate that the patterns are connected to the actual bookstore domain rather than being isolated examples.

The combined design improves maintainability and code reuse. Shared behavior is kept in the appropriate abstraction, object construction is separated from object usage, and concrete implementations can be replaced without changing the client code. A new notification channel can be introduced through another `NotificationService` implementation and factory, while additional book configuration can be added to `BookBuilder` without making the main application difficult to read. The catalog remains centralized, and the existing SOLID classes can continue to evolve independently.

Overall, the laboratory work shows that design patterns are reusable design ideas rather than rigid code templates. Singleton provides controlled shared access, Builder provides clear and flexible object construction, and Factory Method separates the creation of interchangeable services from the workflow that uses them. Together with the SOLID principles from Lab 0, these patterns improve the bookstore application's maintainability, extensibility, code reuse and alignment with object-oriented software design.

## References

1. Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides. *Design Patterns: Elements of Reusable Object-Oriented Software*. Addison-Wesley, 1994.

2. Robert C. Martin. *Agile Software Development, Principles, Patterns, and Practices*. Prentice Hall, 2002.

3. Robert C. Martin. *Clean Architecture: A Craftsman's Guide to Software Structure and Design*. Prentice Hall, 2017.

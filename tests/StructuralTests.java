package tests;
import creational.builder.BookBuilder;
import structural.decorator.*;
import structural.proxy.*;
import structural.facade.BookstoreFacade;
import solid.srp.Book;
import solid.ocp.*;
import java.math.BigDecimal;
import java.util.*;
public class StructuralTests {
    private static int passed;
    private static final Book BOOK = new BookBuilder().title("Throne of Glass")
        .author("Sarah J. Maas").price(new BigDecimal("20.00")).build();
    private static void check(boolean condition, String name) {
        if (!condition) throw new AssertionError(name);
        passed++; System.out.println("PASS " + name);
    }
    private static boolean price(BigDecimal value, String expected) {
        return value.compareTo(new BigDecimal(expected)) == 0;
    }
    public static void main(String[] args) {
        PurchaseItem base = new BookItem(BOOK, new NoDiscount());
        PurchaseItem a = new BookmarkDecorator(new GiftWrapDecorator(base));
        PurchaseItem b = new GiftWrapDecorator(new BookmarkDecorator(base));
        check(price(a.price(), "23") && price(b.price(), "23"), "decoratorsStackInEitherOrder");
        check(price(base.price(), "20") && a.description().contains("gift wrap") && a.description().contains("bookmark"), "decoratorsPreserveBaseAndAddDescription");
        List<String> calls = new ArrayList<>();
        PurchaseService real = new RealPurchaseService((recipient, message) -> calls.add(recipient + message));
        try { new PurchaseProxy(real, false).purchase(a, "guest"); throw new AssertionError("Expected denial"); }
        catch (SecurityException e) { check(calls.isEmpty(), "proxyBlocksUnauthorizedWithoutSideEffects"); }
        check(price(new PurchaseProxy(real, true).purchase(a, "reader"), "23") && calls.size() == 1 && calls.get(0).startsWith("reader") && calls.get(0).contains(a.description()), "proxyDelegatesAuthorized");
        List<String> order = new ArrayList<>();
        BookstoreFacade facade = new BookstoreFacade((item, recipient) -> {
            order.add("purchase");
            if (!recipient.equals("reader") || !item.description().endsWith("gift wrap + bookmark")) throw new AssertionError("Wrong delegation");
            return item.price();
        });
        BigDecimal total = facade.checkout(BOOK, new StudentDiscount(), List.of(
            item -> { order.add("wrap"); return new GiftWrapDecorator(item); },
            item -> { order.add("bookmark"); return new BookmarkDecorator(item); }), "reader");
        check(price(total, "21") && order.equals(List.of("wrap", "bookmark", "purchase")), "facadeAppliesExtrasBeforePurchase");
        calls.clear();
        BookstoreFacade integrated = new BookstoreFacade(new PurchaseProxy(real, true));
        check(price(integrated.checkout(BOOK, new NoDiscount(), List.of(), "reader"), "20") && calls.size() == 1, "facadeProxyIntegrationWithoutExtras");
        System.out.println(passed + " tests passed.");
    }
}

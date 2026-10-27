package solid.ocp;

import solid.srp.Book;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OpenClosedPrinciple {
    
    public BigDecimal calculateDiscountedPrice(Book book, DiscountStrategy strategy) {
        if (book == null || strategy == null) {
            throw new IllegalArgumentException("Book and discount strategy must not be null.");
        }
        return strategy.applyDiscount(book.getPrice()).setScale(2, RoundingMode.HALF_UP);
    }
}

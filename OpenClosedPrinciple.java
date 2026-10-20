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

// Each discount type implements the same contract and returns the resulting price.
interface DiscountStrategy {
    BigDecimal applyDiscount(BigDecimal price);
}

class NoDiscount implements DiscountStrategy {
    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price;
    }
}

class StudentDiscount implements DiscountStrategy {
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price.multiply(BigDecimal.ONE.subtract(DISCOUNT_RATE));
    }
}

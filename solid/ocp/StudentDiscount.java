package solid.ocp;

import java.math.BigDecimal;

public class StudentDiscount implements DiscountStrategy {
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal applyDiscount(BigDecimal price) {
        return price.multiply(BigDecimal.ONE.subtract(DISCOUNT_RATE));
    }
}

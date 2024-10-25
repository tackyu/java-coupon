package coupon.coupon.domain;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class Coupon {

    private static int MIN_DISCOUNT_RATE = 3;
    private static int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Discount discount;

    @Embedded
    private Order order;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private IssuableDuration issuableDuration;

    public Coupon(int discount, int order) {
        validate(discount, order);
        this.discount = new Discount(discount);
        this.order = new Order(order);
    }

    private void validate(int discount, int order) {
        int discountRate = calculateDiscountRate(discount, order);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    "Discount rate must be between " + MIN_DISCOUNT_RATE + " and " + MAX_DISCOUNT_RATE
            );
        }
    }

    public int calculateDiscountRate(int discount, int order) {
        return 100 * discount / order;
    }

    public Long getId() {
        return id;
    }
}
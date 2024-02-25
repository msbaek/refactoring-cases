package pe.msbaek.rfcases.tdd.byexample;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * $5+ 10CHF=$10(환율이 2:1일 경우)
 *
 * X $5 × 2 = $10
 * X Dollar 부작용(Value Object)
 * X equals
 * X dollar, franc 중복
 * X 통화
 * X times dup.
 * X test different class equality
 *
 * 5CHF X 2 = 10CHF
 * hashCode
 * Equal null
 * Equal object
 * BigDecimal 도입
 */
public class MoneyTest {
    @Test
    void testMultiplication() {
        Money five = Money.dollar(5);
        assertThat(five.times(2)).isEqualTo(Money.dollar(10));
        assertThat(five.times(3)).isEqualTo(Money.dollar(15));
    }

    @Test
    void testFrancMultiplication() {
        Money five = Money.franc(5);
        assertThat(five.times(2)).isEqualTo(Money.franc(10));
        assertThat(five.times(3)).isEqualTo(Money.franc(15));
    }

    @Test
    void testEquality() {
        assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5));
        assertThat(Money.dollar(6)).isNotEqualTo(Money.dollar(5));
        assertThat(Money.franc(5)).isEqualTo(Money.franc(5));
        assertThat(Money.franc(6)).isNotEqualTo(Money.franc(5));
    }

    @Test
    void testCurrency() {
        assertThat(Money.dollar(1).currency()).isEqualTo("USD");
        assertThat(Money.franc(1).currency()).isEqualTo("CHF");
    }

    @Test
    void testDifferentClassEquality() {
        assertThat(Money.franc(10)).isEqualTo(Money.franc(10));
        assertThat(Money.dollar(10)).isNotEqualTo(Money.franc(10));
    }
}
package pe.msbaek.rfcases.tdd.byexample;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * $5+ 10CHF=$10(환율이 2:1일 경우)
 *
 * X $5 × 2 = $10
 *
 * Dollar 부작용(Value Object)
 * BigDecimal 도입
 */
public class MoneyTest {
    @Test
    public void testMultiplication() {
        Dollar five = new Dollar(5);
        five.times(2);
        assertThat(five.amount()).isEqualTo(10);
        five.times(3);
        assertThat(five.amount()).isEqualTo(15);
    }
}
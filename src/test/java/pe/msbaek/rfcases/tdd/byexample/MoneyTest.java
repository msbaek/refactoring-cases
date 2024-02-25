package pe.msbaek.rfcases.tdd.byexample;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * $5+ 10CHF=$10(환율이 2:1일 경우)
 *
 * $5 × 2 = $10
 */
public class MoneyTest {
    @Test
    public void testMultiplication() {
        Dollar five = new Dollar(5);
        five.times(2);
        assertThat(five.amount()).isEqualTo(10);
    }
}
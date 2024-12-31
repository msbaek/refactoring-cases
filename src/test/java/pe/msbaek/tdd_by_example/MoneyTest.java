package pe.msbaek.tdd_by_example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/// - [ ] $5 + 10CHF = $10(환율이 2:1일 경우)
/// - [O] $5 × 2 = $10
/// - [ ] amount를 private으로 만들기
/// - [ ] Dollar 부작용(side effect)?
/// - [ ] Money 반올림?
public class MoneyTest {
    @DisplayName("어떤 금액(주가)을 어떤 수(주식의 수)로 곱한 금액을 결과로 얻을 수 있어야 한다")
    @Test
    void testMultiplication() {
        Dollar five = new Dollar(5);
        five.times(2);
        assertThat(five.amount).isEqualTo(10);
    }
}
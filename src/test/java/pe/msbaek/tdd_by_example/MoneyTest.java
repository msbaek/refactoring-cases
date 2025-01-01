package pe.msbaek.tdd_by_example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/// - [] $5 + 10CHF = $10(환율이 2:1일 경우)
/// - [X] $5 × 2 = $10
/// - [X] amount를 private으로 만들기
/// - [X] Dollar 부작용(side effect)?
/// - [] Money 반올림?
/// - [X] equals()
/// - [] hashCode()
/// - [] equal null
/// - [] equal object
/// - [X] 5CHF x 2 = 10CHF
/// - [] Dollar / Franc 중복
/// - [X] 공용 Equals
/// - [] 공용 times
/// - [X] Franc과  비교하기
/// - [X] 통화?
/// - [] testFrancMultiplication을 지워야 할까?
public class MoneyTest {
    @DisplayName("어떤 금액(주가)을 어떤 수(주식의 수)로 곱한 금액을 결과로 얻을 수 있어야 한다")
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
        assertThat(Money.dollar(5)).isNotEqualTo(Money.dollar(6)); // triangulate를 위한 두번째 예제
        assertThat(Money.dollar(5)).isNotEqualTo(Money.franc(5));
    }

    @Test
    void testCurrency() {
        assertThat(Money.dollar(1).currency()).isEqualTo("USD");
        assertThat(Money.franc(1).currency()).isEqualTo("CHF");
    }

    @Test
    void testDifferentClassEquality() {
        assertThat(new Money(10, "CHF").equals(new Money(10, "CHF"))).isTrue();
    }

    @Test
    void testSimpleAddtion() {
        final Money five = Money.dollar(5);
        Expression sum = five.plus(five);
        Bank bank = new Bank();
        final Money reduced = bank.reduce(sum, "USD");
        assertThat(reduced).isEqualTo(Money.dollar(10));
    }
}
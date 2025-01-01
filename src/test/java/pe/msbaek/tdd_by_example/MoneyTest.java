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
/// - [x] 공용 times
/// - [] Franc과 Dollar 비교하기
public class MoneyTest {
    @DisplayName("어떤 금액(주가)을 어떤 수(주식의 수)로 곱한 금액을 결과로 얻을 수 있어야 한다")
    @Test
    void testMultiplication() {
        Dollar five = new Dollar(5);
        assertThat(five.times(2)).isEqualTo(new Dollar(10));
        assertThat(five.times(3)).isEqualTo(new Dollar(15));
    }

    @Test
    void testFrancMultiplication() {
        Franc five = new Franc(5);
        assertThat(five.times(2)).isEqualTo(new Franc(10));
        assertThat(five.times(3)).isEqualTo(new Franc(15));
    }

    @Test
    void testEquality() {
        assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
        assertThat(new Dollar(5)).isNotEqualTo(new Dollar(6)); // triangulate를 위한 두번째 예제
        assertThat(new Franc(5)).isEqualTo(new Franc(5));
        assertThat(new Franc(5)).isNotEqualTo(new Franc(6));
    }
}
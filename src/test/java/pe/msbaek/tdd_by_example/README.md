# TDD By Example

## 1장. 다중 통화를 지원하는 Money 객체

### 1.1 todo list 추가하기

### 1.2 add failing test - testMultiplication

```Java

@DisplayName("어떤 금액(주가)을 어떤 수(주식의 수)로 곱한 금액을 결과로 얻을 수 있어야 한다")
@Test
void testMultiplication() {
    Dollar five = new Dollar(5);
    five.times(2);
    assertThat(five.amount).isEqualTo(10);
}
```

### 1.3 add todo list

- 위 테스트 코드의 문제
    - public field 사용
    - 예기치 못한 부작용 발생 가능
    - 금액을 계산하는데 정수형 사용

```Java
/// - [] amount를 private으로 만들기
/// - [] Dollar 부작용(side effect)?
/// - [] Money 반올림?
```

### 1.4 make it compile

### 1.5 make it work - faking

### 1.6 mark done in test list

## 2장. 타락한 객체

### 2.1 add failing case for immutable

- Dollar애 대한 연산을 호출하면 Dollar가 변경되는 것이 이상함
- 다음과 같이 사용할 수 있기를 바람

```Java
void testMultiplication() {
    Dollar five = new Dollar(5);
    five.times(2);
    assertThat(product.amount).isEqualTo(10);
    five.times(3);
    assertThat(product.amount).isEqualTo(15);
}
```

- 이 테스트를 통과할 명쾌한 방법이 떠오르지 않음
- times를 처음 호출한 이후에 five는 더 이상 5가 아님

### 2.2 make test meaningful

- 이 테스트를 통과할 명쾌한 방법이 떠오르지 않는다.
- times()를 처음 호출한 이후에 five는 더 이상 5가 아니다.
- 그렇다면 times()에서 새 로운 객체를 반환하게 만들면 어떨까?

```Java
void testMultiplication() {
    Dollar five = new Dollar(5);
    Dollar product = five.times(2);
    assertThat(product.amount).isEqualTo(10);
    product = five.times(3);
    assertThat(product.amount).isEqualTo(15);
}
```

### 2.3 make it work

### 2.4 mark done in test list

## 3장. 모두를 위한 평등

### 3.1 add todo list

- Value Object가 암시하는 2가지
    - 모든 연산은 새 객체를 반환해야 함
    - equals()를 구현해야 함

### 3.2 add failing test - testEquality

```Java

@Test
void testEquality() {
    assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
}
```

### 3.3 fake it - return true

```Java
public class Dollar {
    ...

    @Override
    public boolean equals(final Object obj) {
        return true;
    }
}
```

### 3.4 triangulate - add one more test

- 삼각측량을 하려면 예제가 두 개 이상 있어야만 코드를 일반화할 수 있음

```Java

@Test
void testEquality() {
    assertThat(new Dollar(5)).isEqualTo(new Dollar(5));
    assertThat(new Dollar(5)).isNotEqualTo(new Dollar(6)); // triangulate를 위한 두번째 예제
}
```

### 3.5 make it work - generalize equality

![img.png](img.png)

### 3.6 mark done in test list

### 3.7 add todo list

## 4장. 프라이버시

### 4.1 테스트가 의도(모델 코드)를 반영하도록 수정

- 개념적으로 Dollar.times() 연산은 호출 받은 객체의 값에 인자로 받은 곱수만큼 곱한 값을 갖는 Dollar를 반환해야 함
- 하지만 테스트가 정확히 그것을 말하지는 않음(Dollar가 아니라 int를 반환하는것으로 보임)

![img_1.png](img_1.png)

### 4.2 inline product

- 임시변수인 product는 더 이상 쓸모가 없어 보임
- 인라인시켜서 가독성을 높이자

### 4.3 make amount to private final

### 4.4 mark done in test list

## 5장. 솔직히 말하자면

### 5.1 add franc multiplication test

- Dollar 테스트를 복사한 후 수정해보자.

```Java

@Test
void testFrancMultiplication() {
    Franc five = new Franc(5);
    assertThat(five.times(2)).isEqualTo(new Franc(10));
    assertThat(five.times(3)).isEqualTo(new Franc(15));
}
```

### 5.2 add Franc by copying Dollar

### 5.3 mark done and add test in test list

## 6장. 돌아온 '모두를 위한 평등'

### 6.1 add super class

- Dollar, Franc의 수퍼클래스로 Money를 추가

### 6.2 pull member up - amount

### 6.3 pull member up - equaLs

### 6.4 remove equals in Franc

### 6.5 mark done and add test in test list

## 7장. 사과와 오렌지

### 7.1 add equality test for franc

- Dollar 케이스를 복붙해서 Franc 케이스를 추가

![img_2.png](img_2.png)

### 7.2 mark done in test list

### 7.3 add failing case for dollar(5) != franc(5)

![img_3.png](img_3.png)

### 7.4 make it pass by comparing type

![img_4.png](img_4.png)

### 7.5 mark done and add test in test list

- 모델 코드에서 클래스를 이런 식으로 사용하는 것은 좀 지저분해 보인다.
- 자바 객체의 용어를 사용하는 것보다 재정 분야에 맞는 용어를 사용하고 싶다.
- 하지만 현재는 통화(curency) 개념 같은 게 없고, 통화 개념을 도입할 충분한 이유가 없어 보이므로 잠시 동안은 이대로 두자.

## 8장. 객체 만들기

### 8.1 return Money in times

- Dollar, Franc의 times 메소드에서 공용 타입인 Money를 반환하도록 변경하여 두 메소드를 보다 비슷하게 만든다.

### 8.2 introduce factory method

- 불필요해 보이는 하위 클래스를 제거하기 하고 싶다.
- 하위 클래스에 대한 직접적인 참조를 없애기 위해 생성자를 팩토리 메소드로 대체한다.
- 절차
    - Replace constructor with factory method
    - pull members up

### 8.3 use base type

### 8.4 add todo in test list

## 9장. 우리가 사는 시간

- time은 시간을 의미하기도 하고, 곱셈을 의미하기도 함

- 할일 목록에서 어떤 것을 하면 귀찮고 불필요한 하위 클래스를 제거하는데 도움이 될까 ?
- 통화 개념을 도입하면 도움이 될 것이다.

### 9.1 add new failing test - testCurrency

- 통화 개념을 도입하면 도움이 될 것이다.

```Java

@Test
void testCurrency() {
    assertThat(Money.dollar(1).currency()).isEqualTo("USD");
    assertThat(Money.franc(1).currency()).isEqualTo("CHF");
}
```

### 9.2 make it work

- add abstract method currency() in Money
- implement currency() in Dollar and Franc

### 9.3 introduce currency field

- 두 클래스의 구현이 최대한 유사해지도록 하기 위해 currency 필드를 추가

![img_5.png](img_5.png)

### 9.4 pull members up - currency field

- currency 필드를 Money로 옮긴다.
- 베이스 틀래스의 생성자의 인자로 currency를 추가한다.

![img_6.png](img_6.png)

### 9.5 introduce parameter in c'tor

![img_7.png](img_7.png)

### 9.6 mark done in test list

## 10장. 흥미로운 시간

- times를 Money로 옮겨서 두 서브 클래스를 없애고 싶다.
- times에서 서로 다른 factory method를 호출하고 있는데, 이를 생성자로 변경하면 동일한 구조를 갖게 할 수 있다.
- 때로는 전진하기 위해 물러서야 할 때도 있는 법

### 10.1 inline factory method to make times look similar

### 10.2 use currency in times instead of constant

### 10.3 return Money in times

- Money의 2개의 abstract method를 구현 or 삭제(times, currency)
- Money 클래스 선언에서 abstract 제거
- Dollar, Franc에서 Money를 반환하도록 수정
- pull members up - times

### 10.4 add Money#toString for debugging

- toString()을 실패하는 테스트의 디버깅을 위해 추가
- 헉! 테스트도 없이 코드를 작성하네? 그래도 되는 건가?
- toString()을 작성하기 전에 테스트를 작성하는 게 맞다.
- 하지만 우린 지금 화면에 나타나는 결과를 보려던 참이다.
- toString()은 디버그 출력에만 쓰이기 때문에 이게 잘못 구현됨으로 인해 얻게 될 리스크가 적다.
- 이미 빨간 막대 상태인데 이 상태에서는 새로운 테스트를 작성하지 않는 게 좋을 것 같다.

```Java
org.opentest4j.AssertionFailedError:
expected:"10 USD (Dollar@5f8edcc5)"
but was:"10 USD (Money@6db9f5a4)"
Expected :10USD
Actual   :10USD
```

- 답은 맞았는데 클래스가 다르다. Franc 대신 Money가 왔다. 문제는 equals() 구현에 있다.

### 10.5 rollback times in Dollar and Franc

- 빨간 막대인 상황에서는 테스트를 추가로 작성하고 싶지 않다.
- 하지만 지금은 실제 모델 코드를 수정하려고 하는 중이고 테스트 없이는 모델 코드를 수정할 수 없다.
- 보수적인 방법을 따르자면 변경된 코드를 되돌려서 다시 초록 막대 상태로 돌아가야 한다.
- 그러고 나서 equals()를 위해 테스트를 고치고 구현 코드를 고칠 수 있게 되고, 그 후에야 원래 하던 일을 다 시 할 수 있다.

### 10.6 add failing test - testDifferentClassEquality

```Java
@Test
void testDifferentClassEquality() {
  assertThat(new Franc(10, "CHF").equals(new Money(10, "CHF"))).isTrue();
}
```

### 10.7 make it pass 

- class 대신 currency를 비교하도록 equals()를 수정 

### 10.8 remove subclasses

- Dollar, Franc의 times()를 제거  
- 생성자만 남은 서브 클래스는 존재의 이유가 없다.
- factory method에서 Money를 반환하도록 수정
- Dollar, Franc를 제거

## 11장. 모든 악의 근원

- 불필요한 코드들을 제거한다.

### 11.1 remove unnecessary test cases

![img_8.png](img_8.png)
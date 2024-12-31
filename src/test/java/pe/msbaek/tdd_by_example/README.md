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


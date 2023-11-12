# 기법
<!-- TOC -->
* [기법](#기법)
  * [Lift up conditional](#lift-up-conditional)
  * [유사한 기능을 추가하는 절차](#유사한-기능을-추가하는-절차)
  * [loop is one thing - SRP](#loop-is-one-thing---srp)
  * [Slow Scroll](#slow-scroll)
  * [collapse method → 한 레벨씩만 expand](#collapse-method--한-레벨씩만-expand)
  * [Split Phase](#split-phase)
  * [Split by Levels of Abstraction](#split-by-levels-of-abstraction)
  * [Split Unrelated Complexity](#split-unrelated-complexity)
  * [Imperative Shell / Functional Core Segregation](#imperative-shell--functional-core-segregation)
  * [combinational approval test](#combinational-approval-test)
  * [Approval Test를 위한 Printer](#approval-test를-위한-printer)
<!-- TOC -->
## Lift up conditional

- indentation에 깊게 들어가며 동일 조건문이 2곳 이상에서 사용되는 경우 조건문을 단순화 하기 위해
- 절차
    1. 중복 조건문을 변수로 추출
    2. 추출된 변수를 사용하는 블록을 메소드로 추출
    3. 추출된 메소드를 surround with if-else
        ```java
        if(condition) {
            extracted(true);
        } else {
            extracted(false);
        }
        ```
    4. inline extracted
    5. deal with IDE warnings
    6. 1~6을 중복 조건문에 대해서 수행
  
## 유사한 기능을 추가하는 절차
1. 복붙
2. Programming by difference
3. Template Method pattern
3.1 by Inheritance
3.2 by delegation

## loop is one thing - SRP
- try-catch도

## Slow Scroll
- 실제로 코드 라인을 읽는 것이 아니라 한번 스윽 모양과 컬러를 보는
  - 코드 스멜을 찾게 됨
    - 함수가 길다. 중첩이 구조가 깊다. 등
  - inspection tool이 표시해 주는

## collapse method → 한 레벨씩만 expand
- 들여쓰기가 많은 코드에서 어떤 일이 일어나는지에 대한 overview를 보는데 유용함
- ex.
- 전체 로직은 for-loop라는 것을 확인
- void 함수이니 item을 변경하는 함수일 것이다.
- expand all
- item에 커서를 두고 item이 어디서 사용되는지 조망(ctrl+opt+ ↑ ↓)
  - 많은 곳에서 사용되는 것올 보면 **feature envy**가 심함을 알 수 있음
  - **extract method** & **move instance method** 를 할 차례임

## Split Phase
- from Refactoring 2nd ed.
- 의도
  - 서로 다른 두 가지를 다루는 코드가 있을 때 이를 별도의 모듈로 분리
  - 이렇게 분리하면 변경이 필요할 때 두 가지 주제를 따로 처리할 수 있고 두 가지를 머릿속에 함께 담을 필요가 없기 때문임
- 절차
  - 1. Extract Method
    ![img_2.png](../images/split_phase_1)
  - 2. Introduce intermediate data structure that will communicate between the two phases
    ![img_3.png](../images/split_phase_2.png)
  - 3. Examine each parameter of the extracted second phase.
    - If it is used by first phase, move it to the intermediate data structure.
    - 3.1 basePrice
      ![img_4.png](../images/split_phase_3.png)
    - 3.2 shippingMethod
      - 첫번째 단계애서 사용되지 않았기 때문에 스킵
    - 3.3 quantity
      - 첫번째 단계에서 사용은 되지만, 생성은 다른 곳에 되었음
      - 이런 경우 파라미터 목록에 유지해도 됨
      - 하지만 개인적인 선호는 최대한 많이 intermediate DS로 옭김
      - ![img_5.png](../images/split_phase_4.png)
    - 3.4. discount
      - ![img_6.png](../images/split_phase_5.png)
  - 4. extract first-phase to return intermediate DS
    - PriceData를 먼저 variable로 추출
    - PriceData를 반환하도록 첫번째 단계를 메소드로 추출(이를 위해 먼저 intermediate DS를 variable로 추출)
    - ![img_7.png](../images/split_phase_6.png)
  
## Split by Levels of Abstraction
- d80da045776fa263468cd657726696b5cbbdd139
- high level과 low level의 코드가 섞여 있을 때
- low level 코드 호출 없이 high level 테스트는 partial mock이 필요
- low level을 **Extract Delegate**
- Test도 분리

## Split Unrelasted Complexity
- edf3319a6a78e84dd239b5d82d1113dcb3c74993
- 서로 다른 의존성을 갖는 기능들이 한 클래스에 있을 때
- 테스트 케이스에서 의존성 설정의 중복을 없애려고 setup으로 의존성 설정을 옮기면(for DRY) 테스트 오류 발생
  - 요구되는 상호작용(when)이 호출되지 않아서 - `Unnesessary stubbings detected`
- 절차
  - 테스트 분리: test method, setUp
  - 상용 코드 분리

## Imperative Shell / Functional Core Segregation
- 8f89bdf198ca341a282c674e5fab1699de22c008
- duplicate loop
  - 중간 결과를 담을 변수를 추가해서 함수로 추출 시 부작용이 없도록 
- split p↓hase
- extract functional core
- add functional core test

## combinational approval test
- b505eac73ab5abe2fb55b10e884e2fb76cec55d3
- 인자들을 조합해서 호출하기 위해 객체 배열(not int but Integer)을 갖는 테스트 메스드를 작성
- CombinatorialApprovals.verifyAllCombinations이 이 메소드를 호출하도록

## Approval Test를 위한 Printer
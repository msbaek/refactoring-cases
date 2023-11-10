# 기법
<!-- TOC -->
* [기법](#기법)
  * [Lift up conditional](#lift-up-conditional)
  * [유사한 기능을 추가하는 절차](#유사한-기능을-추가하는-절차)
  * [loop is one thing - SRP](#loop-is-one-thing---srp)
  * [Slow Scroll](#slow-scroll)
  * [collapse method → 한 레벨씩만 expand](#collapse-method--한-레벨씩만-expand)
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

## Split by Levels of Abstraction
- d80da045776fa263468cd657726696b5cbbdd139
- high level과 low level의 코드가 섞여 있을 때
- low level 코드 호출 없이 high level 테스트는 partial mock이 필요
- low level을 **Extract Delegate**
- Test도 분리

## combinational approval test
- b505eac73ab5abe2fb55b10e884e2fb76cec55d3

## Approval Test를 위한 Printer
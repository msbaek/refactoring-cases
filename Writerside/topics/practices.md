# 기법

## Lift up conditional

- indentation에 깊게 들어가며 동일 조건문이 2곳 이상에서 사용되는 경우 조건문을 단순화 하기 위해
- 절차
    1. 중복 조건문을 변수로 추출
    2. 추출된 변수를 사용하는 블록을 메소드로 추출
    3. 추출된 메소드를 surround with if-else
    4. 다음과 같은 구조로 if-else를 변경
        ```java
        if(condition) {
            extracted(true);
        } else {
            extracted(false);
        }
        ```
    5. inline extracted
    6. deal with IDE warnings
    7. 1~6을 중복 조건문에 대해서 수행
# Refactoring Cases

## [abstract till you drop](https://thinkfoo.wordpress.com/2015/05/17/one-thing-abstract-till-you-drop/)

이 예제는 extract till you drop(extract method를 수행할 수 없을 때까지 수행)을 한 후에 남게되는 많은 작은 private method들을 역할에 맞게 다른 클래스로 위치시키는 것을 보여준다.

extract till you drop은 [github](https://raw.githubusercontent.com/msbaek/clean-coders-2013/master/2.Functions.png), [youtube](https://www.youtube.com/watch?v=GYNT7O3rLhU)에서 자세히 보실 수 있습니다.

### 1. 최초 클래스

```
package abstract_till_you_drop;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class SymbolReplacer {
    protected String stringToReplace;
    protected List alreadyReplaced = new ArrayList();

    SymbolReplacer(String s) {
        this.stringToReplace = s;
    }

    String replace() {
        Pattern symbolPattern = Pattern.compile("\\$([a-zA-Z]\\w*)");
        Matcher symbolMatcher = symbolPattern.matcher(stringToReplace);
        while (symbolMatcher.find()) {
            String symbolName = symbolMatcher.group(1);
            if (getSymbol(symbolName) != null && !alreadyReplaced.contains(symbolName)) {
                alreadyReplaced.add(symbolName);
                stringToReplace = stringToReplace.replace("$" + symbolName, getSymbol(symbolName));
            }
        }
        return stringToReplace;
    }

    abstract protected String getSymbol(String symbolName);
}
```

### 2. 테스트 추가

#### abstract method에 대한 구현체를 추가.

```
package abstract_till_you_drop;

public class MyReplacer extends SymbolReplacer {
    MyReplacer(String s) {
        super(s);
    }

    @Override
    protected String getSymbol(String symbolName) {
        return "__";
    }
}
```

#### characterization test를 추가한다.

```
package abstract_till_you_drop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SymbolReplacerTest {
    @Test
    public void foo() {
        MyReplacer replacer = new MyReplacer("$ss aa $bb dd ff ss");
        assertThat(replacer.replace(), is("xx"));
    }
}
```

#### make it pass
테스트를 실행시켜서 시스템이 원하는 값을 알아내고 이를 이용하여 테스트를 성공시킨다. <- **characterization test**
![](https://api.monosnap.com/rpc/file/download?id=cT5fuxvetaBk2Abf3HvafvJGdyJq6w)

### 3. Prepare Extract Methods
symbolPattern, symbolMatcher를 field로 추출
큰 메소드를 작은 메소드를 extract하기 전의 사전 작업. 이 작업을 수행하면 extract method할 때 parameter로 전달할 필요가 없어진다.

![](https://api.monosnap.com/rpc/file/download?id=7kt24mePKPJ9fu9FQ77CaccQO4hjoc)

### 4. Extract method: replaceAllInstances
![](https://api.monosnap.com/rpc/file/download?id=V1h4JkqF7NA0qDFjHe8ZnlCakPqkxc)

### 5. Extract method: shouldReplaceSymbol
![](https://api.monosnap.com/rpc/file/download?id=rXgi4YvLF628KJOvZ8nZuuF1Oj4ank)

### 6. Extract method: replaceSymbol
![](https://api.monosnap.com/rpc/file/download?id=Tbe5Dj9XNloQ63G1QoSRtM9gl6653F)

### 7. Change while to for
![](https://api.monosnap.com/rpc/file/download?id=au3CPwAzkJjUqHWENJcc8YB26xY50G)

### 8. Extract method: nextSymbol
![](https://api.monosnap.com/rpc/file/download?id=xSXM4NMkocCjKKRkNYLCvhMadFFAmy)

### 9. Change inheritance to composition

#### prepare
SymbolTranslator 클래스를 생성
![](https://api.monosnap.com/rpc/file/download?id=S74yJJcF0YxXnuPix8I2m4i6lFoirJ)
![](https://api.monosnap.com/rpc/file/download?id=plLLZOHkqgGuvGgB1Dbari0PSLqYuL)

#### make it pass
![](https://api.monosnap.com/rpc/file/download?id=Wwb3LBU1mL1F5FvDPzBd6KHNvbB6Uj)
![](https://api.monosnap.com/rpc/file/download?id=D6XWjWjWc7IXOL4Rhq7sJUlN0vH4kL)
![](https://api.monosnap.com/rpc/file/download?id=Tv94ScsSeZLbJiHUjRRn4jKCEFuHaR)

### 10. Extract class - SymbolIterator
- symbolMatcher, symbolPattern는 nextSymbol에서만 사용됨.
- 다른 속성들보다 보다 응집도가 높거나 밀접하게 관계된 2개 이상의 속성들이 존재하는 경우가 있다.
- 이런 속성들을 별도의 클래스로 추출하면 가독성이 좋아진다.

#### prepare
symbolMatcher, symbolPattern를 SymbolIterator로 이동시키기 위해 static으로 변경. 그래야 intellij에서 move 가능.

![](https://api.monosnap.com/rpc/file/download?id=BROa3PK0RYpFKLHH0ySuLJnfLrPIr1)

#### move members
![](https://api.monosnap.com/rpc/file/download?id=iu4RX126OgGEyLjshlB22aBded237v)

![](https://api.monosnap.com/rpc/file/download?id=nYftViESspAkTA4QCH6zb3EnJDAnHV)

![](https://api.monosnap.com/rpc/file/download?id=K3xlfy6YHRMm8rDYN9H6nPJBI2jTFH)

#### move nextSymbol to SymbolIterator
![](https://api.monosnap.com/rpc/file/download?id=nf6nwOPbIx74tH2ypED9IwrGQnVPiz)
![](https://api.monosnap.com/rpc/file/download?id=7PMx0BO5qooDbbT3HmRjUDeqd8SwkP)

#### make it work
![](https://api.monosnap.com/rpc/file/download?id=Zm2w59OnJQVlgfhSOugdNV3HB2I5uh)
![](https://api.monosnap.com/rpc/file/download?id=IEpviDg3hESnggiN6a8JeBbYurUTNo)

### 11. Extract method - symbolExpression
가독성을 위해 아래와 같이 extract method를 수행
![](https://api.monosnap.com/rpc/file/download?id=dWHcZMuI2EGQ2uvoSsPekIWr7HZZDr)

### 12. Move symbolExpression to SymbolTranslator
symbolExpression은 SymbolTranslator와 밀접한 관계를 갖는다. 따라서 SymbolTranslator로 이동시킨다.
![](https://api.monosnap.com/rpc/file/download?id=VQwQqXjWq8iBtx6XRCI0qoPCWyrdwh)
![](https://api.monosnap.com/rpc/file/download?id=MBixZzmIeXiJD63lVjvDN0hUoCxClM)

### 13. Feature envy
replaceSymbol은 symbolTranslator의 2개의 메소드를 호출한다. feature envy다.
SymbolTranslator의 translate 메소드로 이동하자.

#### prepare: extract method translate
2개의 메소드 호출을 IDE의 기능으로 이동시킬 수 없다. 먼저 2개의 메소드 호출을 translate 함수로 추출한다.
![](https://api.monosnap.com/rpc/file/download?id=idv1w2wJH3RTtlYrs2yvwQH1CnXdpd)

#### move method translate to SymbolTranslator
![](https://api.monosnap.com/rpc/file/download?id=a4fAXlikPacoHQtOi0gLZI8FfpULFY)
![](https://api.monosnap.com/rpc/file/download?id=JS8sjzA482XCmoTvR7vEygelfxm91s)

#### refactoring
symbolExpression는 더 이상 외부에서 호출되지 않으니 private으로 변경
![](https://api.monosnap.com/rpc/file/download?id=3O1RfHccyiGWfLdmLdy5JnMr1YhslI)
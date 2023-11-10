package pe.msbaek.rfcases.functionalcore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@Getter
public class Supplier {
    private Long id;

    public Supplier(long id) {
        this.id = id;
    }
}
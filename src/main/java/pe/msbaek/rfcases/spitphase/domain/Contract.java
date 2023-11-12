package pe.msbaek.rfcases.spitphase.domain;

import lombok.Data;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
public class Contract {
    boolean hasWarning(Double warningThreshold) {
        return getStatus() == Status.ACTIVE
                && getLastPayment().isBefore(now().minusDays(60))
                && getRemainingValue() > warningThreshold;
    }

    public enum Status {
        DRAFT, ACTIVE,CLOSED
    }
    private String number;
    private String name;
    private LocalDateTime lastPayment;
    private Status status;
    private Double remainingValue;
}
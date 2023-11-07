package pe.msbaek.rfcases.kt4u;

import lombok.Getter;

@Getter
public enum PackingStatus {
    READY("ready", "포장대기"),
    PROCESSING("processing", "포장중"),
    COMPLETED("completed", "포장완료"),
    ERROR("error", "포장오류"),
    REGISTERED("registered", "송장발행완료"),
    PRINTED("printed", "송장출력완료");

    private final String code;
    private final String description;

    PackingStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getGroupCode() {
        return getClass().getSimpleName();
    }

    public String getKey() {
        return name();
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
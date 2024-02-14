package pe.msbaek.rfcases.tdd;

import org.apache.commons.lang3.StringUtils;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class FanClubProductTest {
    @DisplayName("메모를 입력하면 기존 메모 앞에 메모 형식에 맞게 포맷팅하여 추가한다")
    @Test
    void formatMemo() {
        // given
        String originalMemo = "[2020-01-01 09:00:00][1234] 기존 메모\n";

        // when
        String newMemo = "새로운 메모";
        LocalDateTime now = LocalDateTime.of(2020, 1, 2, 10, 11, 12);
        Long userId = 4321l;
        String formattedMemo = formatMemo(newMemo, now, userId, originalMemo);

        // then
        Approvals.verify(formattedMemo);
    }

    FanClubProduct fanClubProduct = new FanClubProduct();

    private String formatMemo(String newMemo, LocalDateTime now, Long userId, String originalMemo) {
        if (StringUtils.isBlank(newMemo)) return originalMemo;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return String.format("[%s][%d] %s\n",
                now.format(formatter),
                userId,
                newMemo) + StringUtils.defaultString(originalMemo);
    }
}
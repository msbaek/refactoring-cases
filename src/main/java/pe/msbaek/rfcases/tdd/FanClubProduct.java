package pe.msbaek.rfcases.tdd;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FanClubProduct {
    public void existingMethod() {
        System.out.println("existingMethod");
    }

    String formatMemo(String newMemo, LocalDateTime now, Long userId, String originalMemo) {
        if (StringUtils.isBlank(newMemo)) return originalMemo;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return String.format("[%s][%d] %s\n",
                now.format(formatter),
                userId,
                newMemo) + StringUtils.defaultString(originalMemo);
    }
}
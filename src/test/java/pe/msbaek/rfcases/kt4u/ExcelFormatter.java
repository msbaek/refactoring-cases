package pe.msbaek.rfcases.kt4u;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ExcelFormatter {
    static String formatDate(final Instant value) {
        if (null == value) return null;
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault())
                .format(value);
    }

    static String formatUser(final String userName, final String userLoginId) {
        return userName + "(" + userLoginId + ")";
    }
}
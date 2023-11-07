package pe.msbaek.rfcases.kt4u;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ExcelFormatter {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    static String formatDate(final Instant value) {
        if (null == value)
            return null;
        return dateTimeFormatter.format(value);
    }

    static String formatUser(final String userName, final String userLoginId) {
        return userName + "(" + userLoginId + ")";
    }
}
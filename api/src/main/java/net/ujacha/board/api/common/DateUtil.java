package net.ujacha.board.api.common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DateUtil {

    public static String ago(LocalDateTime before, LocalDateTime now, Locale locale) {
        return ago(before, now, locale, false);
    }


    public static String ago(LocalDateTime before, LocalDateTime now, boolean shortDisplay) {
        return ago(before, now, Locale.ENGLISH, true);
    }

    public static String ago(LocalDateTime before, LocalDateTime now, Locale locale, boolean shortDisplay) {

        boolean ko = false;
        if ("ko".equals(locale.getLanguage())) {
            ko = true;
        }

        final long seconds = ChronoUnit.SECONDS.between(before, now);

        // #초 전 -> 금방
        if (seconds < 60) {
            return seconds(shortDisplay, ko, seconds);
        }

        // #분 전
        final long minutes = ChronoUnit.MINUTES.between(before, now);
        if (minutes < 60) {
            return minutes(shortDisplay, ko, minutes);
        }

        // #시간 전
        final long hours = ChronoUnit.HOURS.between(before, now);
        if (hours < 24) {
            return hours(shortDisplay, ko, hours);
        }

        // #일 전
        final long days = ChronoUnit.DAYS.between(before, now);
        if (days < 31) {
            return days(shortDisplay, ko, days);
        }

        // #개월 전
        final long months = ChronoUnit.MONTHS.between(before, now);
        if (months < 12) {
            return months(shortDisplay, ko, months);
        }

        // #년 전
        final long years = ChronoUnit.YEARS.between(before, now);
        return years(shortDisplay, ko, years);
    }

    private static String seconds(boolean shortDisplay, boolean ko, long seconds) {
        return shortDisplay
                ? seconds + "s"
                : ko
                    ? "금방"
                    : (seconds == 1
                        ? seconds + " SECOND AGO"
                        : seconds + " SECONDS AGO");
    }

    private static String minutes(boolean shortDisplay, boolean ko, long minutes) {
        return shortDisplay
                ? minutes + "m"
                : ko
                    ? minutes + "분 전"
                    : (minutes == 1
                        ? minutes + " MINUTE AGO"
                        : minutes + " MINUTES AGO");
    }

    private static String hours(boolean shortDisplay, boolean ko, long hours) {
        return shortDisplay
                ? hours + "h"
                : ko
                    ? hours + "시간 전"
                    : (hours == 1
                        ? hours + " HOUR AGO"
                        : hours + " HOURS AGO");
    }

    private static String days(boolean shortDisplay, boolean ko, long days) {
        return shortDisplay
                ? days + "D"
                : ko
                    ? (days == 1
                        ? "어제"
                        : days + "일 전")
                    : (days == 1
                        ? "YESTERDAY"
                        : days + " DAYS AGO");
    }

    private static String months(boolean shortDisplay, boolean ko, long months) {
        return shortDisplay
                ? months + "M"
                : ko
                    ? months + "개월 전"
                    : (months == 1
                        ? months + " MONTH AGO"
                        : months + " MONTHS AGO");
    }

    private static String years(boolean shortDisplay, boolean ko, long years) {
        return shortDisplay
                ? years + "Y"
                : ko
                    ? years + "년 전"
                    : (years == 1
                        ? years + " YEAR AGO"
                        : years + " YEARS AGO");
    }
}

package net.ujacha.board.api.common;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilTest {
    @Test
    public void test_ago_ko() {
        // Given
        final Locale locale = Locale.KOREAN;
        final LocalDateTime now = LocalDateTime.of(2020, 3, 13, 23, 02, 50);

        // When
        final LocalDateTime before_59s = now.minus(59, ChronoUnit.SECONDS);
        final String ago_59s = DateUtil.ago(before_59s, now, locale);

        final LocalDateTime before_60s = now.minus(60, ChronoUnit.SECONDS);
        final String ago_60s = DateUtil.ago(before_60s, now, locale);

        final LocalDateTime before_123s = now.minus(123, ChronoUnit.SECONDS);
        final String ago_123s = DateUtil.ago(before_123s, now, locale);

        final LocalDateTime before_31m = now.minus(31, ChronoUnit.MINUTES);
        final String ago_31m = DateUtil.ago(before_31m, now, locale);

        final LocalDateTime before_61m = now.minus(61, ChronoUnit.MINUTES);
        final String ago_61m = DateUtil.ago(before_61m, now, locale);

        final LocalDateTime before_12h = now.minus(12, ChronoUnit.HOURS);
        final String ago_12h = DateUtil.ago(before_12h, now, locale);

        final LocalDateTime before_24h = now.minus(24, ChronoUnit.HOURS);
        final String ago_24h = DateUtil.ago(before_24h, now, locale);

        final LocalDateTime before_30h = now.minus(30, ChronoUnit.HOURS);
        final String ago_30h = DateUtil.ago(before_30h, now, locale);

        final LocalDateTime before_49h = now.minus(49, ChronoUnit.HOURS);
        final String ago_49h = DateUtil.ago(before_49h, now, locale);

        final LocalDateTime before_40d = now.minus(40, ChronoUnit.DAYS);
        final String ago_40d = DateUtil.ago(before_40d, now, locale);

        final LocalDateTime before_400d = now.minus(400, ChronoUnit.DAYS);
        final String ago_400d = DateUtil.ago(before_400d, now, locale);


        // Then
        assertThat(ago_59s).isEqualTo("금방");
        assertThat(ago_60s).isEqualTo("1분 전");
        assertThat(ago_123s).isEqualTo("2분 전");
        assertThat(ago_31m).isEqualTo("31분 전");
        assertThat(ago_61m).isEqualTo("1시간 전");
        assertThat(ago_12h).isEqualTo("12시간 전");
        assertThat(ago_24h).isEqualTo("어제");
        assertThat(ago_30h).isEqualTo("어제");
        assertThat(ago_49h).isEqualTo("2일 전");
        assertThat(ago_40d).isEqualTo("1개월 전");
        assertThat(ago_400d).isEqualTo("1년 전");
    }

    @Test
    public void test_ago_en() {
        // Given
        final Locale locale = Locale.ENGLISH;
        final LocalDateTime now = LocalDateTime.of(2020, 3, 13, 23, 02, 50);

        // When
        final LocalDateTime before_59s = now.minus(59, ChronoUnit.SECONDS);
        final String ago_59s = DateUtil.ago(before_59s, now, locale);

        final LocalDateTime before_60s = now.minus(60, ChronoUnit.SECONDS);
        final String ago_60s = DateUtil.ago(before_60s, now, locale);

        final LocalDateTime before_123s = now.minus(123, ChronoUnit.SECONDS);
        final String ago_123s = DateUtil.ago(before_123s, now, locale);

        final LocalDateTime before_31m = now.minus(31, ChronoUnit.MINUTES);
        final String ago_31m = DateUtil.ago(before_31m, now, locale);

        final LocalDateTime before_61m = now.minus(61, ChronoUnit.MINUTES);
        final String ago_61m = DateUtil.ago(before_61m, now, locale);

        final LocalDateTime before_12h = now.minus(12, ChronoUnit.HOURS);
        final String ago_12h = DateUtil.ago(before_12h, now, locale);

        final LocalDateTime before_24h = now.minus(24, ChronoUnit.HOURS);
        final String ago_24h = DateUtil.ago(before_24h, now, locale);

        final LocalDateTime before_30h = now.minus(30, ChronoUnit.HOURS);
        final String ago_30h = DateUtil.ago(before_30h, now, locale);

        final LocalDateTime before_49h = now.minus(49, ChronoUnit.HOURS);
        final String ago_49h = DateUtil.ago(before_49h, now, locale);

        final LocalDateTime before_40d = now.minus(40, ChronoUnit.DAYS);
        final String ago_40d = DateUtil.ago(before_40d, now, locale);

        final LocalDateTime before_80d = now.minus(80, ChronoUnit.DAYS);
        final String ago_80d = DateUtil.ago(before_80d, now, locale);

        final LocalDateTime before_400d = now.minus(400, ChronoUnit.DAYS);
        final String ago_400d = DateUtil.ago(before_400d, now, locale);

        final LocalDateTime before_800d = now.minus(800, ChronoUnit.DAYS);
        final String ago_800d = DateUtil.ago(before_800d, now, locale);


        // Then
        assertThat(ago_59s).isEqualTo("59 SECONDS AGO");
        assertThat(ago_60s).isEqualTo("1 MINUTE AGO");
        assertThat(ago_123s).isEqualTo("2 MINUTES AGO");
        assertThat(ago_31m).isEqualTo("31 MINUTES AGO");
        assertThat(ago_61m).isEqualTo("1 HOUR AGO");
        assertThat(ago_12h).isEqualTo("12 HOURS AGO");
        assertThat(ago_24h).isEqualTo("YESTERDAY");
        assertThat(ago_30h).isEqualTo("YESTERDAY");
        assertThat(ago_49h).isEqualTo("2 DAYS AGO");
        assertThat(ago_40d).isEqualTo("1 MONTH AGO");
        assertThat(ago_80d).isEqualTo("2 MONTHS AGO");
        assertThat(ago_400d).isEqualTo("1 YEAR AGO");
        assertThat(ago_800d).isEqualTo("2 YEARS AGO");
    }

    @Test
    public void test_ago_short() {
        // Given
        final Locale locale = Locale.ENGLISH;
        final LocalDateTime now = LocalDateTime.of(2020, 3, 13, 23, 02, 50);

        // When
        final LocalDateTime before_59s = now.minus(59, ChronoUnit.SECONDS);
        boolean shortDisplay = true;

        final String ago_59s = DateUtil.ago(before_59s, now, shortDisplay);

        final LocalDateTime before_60s = now.minus(60, ChronoUnit.SECONDS);
        final String ago_60s = DateUtil.ago(before_60s, now, shortDisplay);

        final LocalDateTime before_123s = now.minus(123, ChronoUnit.SECONDS);
        final String ago_123s = DateUtil.ago(before_123s, now, shortDisplay);

        final LocalDateTime before_31m = now.minus(31, ChronoUnit.MINUTES);
        final String ago_31m = DateUtil.ago(before_31m, now, shortDisplay);

        final LocalDateTime before_61m = now.minus(61, ChronoUnit.MINUTES);
        final String ago_61m = DateUtil.ago(before_61m, now, shortDisplay);

        final LocalDateTime before_12h = now.minus(12, ChronoUnit.HOURS);
        final String ago_12h = DateUtil.ago(before_12h, now, shortDisplay);

        final LocalDateTime before_24h = now.minus(24, ChronoUnit.HOURS);
        final String ago_24h = DateUtil.ago(before_24h, now, shortDisplay);

        final LocalDateTime before_30h = now.minus(30, ChronoUnit.HOURS);
        final String ago_30h = DateUtil.ago(before_30h, now, shortDisplay);

        final LocalDateTime before_49h = now.minus(49, ChronoUnit.HOURS);
        final String ago_49h = DateUtil.ago(before_49h, now, shortDisplay);

        final LocalDateTime before_40d = now.minus(40, ChronoUnit.DAYS);
        final String ago_40d = DateUtil.ago(before_40d, now, shortDisplay);

        final LocalDateTime before_400d = now.minus(400, ChronoUnit.DAYS);
        final String ago_400d = DateUtil.ago(before_400d, now, shortDisplay);


        // Then
        assertThat(ago_59s).isEqualTo("59s");
        assertThat(ago_60s).isEqualTo("1m");
        assertThat(ago_123s).isEqualTo("2m");
        assertThat(ago_31m).isEqualTo("31m");
        assertThat(ago_61m).isEqualTo("1h");
        assertThat(ago_12h).isEqualTo("12h");
        assertThat(ago_24h).isEqualTo("1D");
        assertThat(ago_30h).isEqualTo("1D");
        assertThat(ago_49h).isEqualTo("2D");
        assertThat(ago_40d).isEqualTo("1M");
        assertThat(ago_400d).isEqualTo("1Y");
    }

}
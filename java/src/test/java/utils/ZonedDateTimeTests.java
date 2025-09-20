package utils;


import java.time.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ZonedDateTimeTests {

    @Test
    void 현재_시간_구하기() {
        System.out.println("시스템 현재 시간: " + ZonedDateTime.now());
        System.out.println("뉴욕 현재 시간: " + ZonedDateTime.now(ZoneId.of("America/New_York")));
        System.out.println("베를린 현재 시간: " + ZonedDateTime.now(ZoneId.of("Europe/Berlin")));
    }

    @Test
    void 특정_시간대의_특정_날짜와_시간_구하기() {
        System.out.println(ZonedDateTime.of(
                1234, 5, 6, 0, 0, 0, 0,
                ZoneId.of("Asia/Tokyo"))
        );
    }

    @Test
    void ZonedDateTime_변환하기() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        // ZonedDateTime -> Instant
        Instant instant = zonedDateTime.toInstant();

        // Instant -> ZonedDateTime
        ZonedDateTime fromInstant = instant.atZone(ZoneId.systemDefault());

        // ZonedDateTime -> LocalDateTime
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        // LocalDateTime -> ZonedDateTime
        ZonedDateTime fromLocalDateTime = localDateTime.atZone(ZoneId.systemDefault());

        Assertions.assertEquals(zonedDateTime, fromInstant);
        Assertions.assertEquals(zonedDateTime, fromLocalDateTime);
    }

    @Test
    void 서머타임_적용하기() {
        ZoneId NEW_YORK = ZoneId.of("America/New_York");
        ZonedDateTime summer = ZonedDateTime.of(2025, 7, 1, 12, 0, 0, 0, NEW_YORK);
        ZonedDateTime winter = ZonedDateTime.of(2025, 1, 1, 12, 0, 0, 0, NEW_YORK);

        System.out.println("서머 타임 자동 적용: " + summer.getOffset());
        System.out.println("표준시 자동 적용: " + winter.getOffset());
    }

    @Test
    void ZoneId로_타임존_조작하기() {
        System.out.println("시스템 타임존: " + ZoneId.systemDefault());

        // 문자열로부터 생성하기
        System.out.println("뉴욕 타임존: " + ZoneId.of("America/New_York"));

        // ZoneId 목록 출력
        System.out.println("지원하는 타임존 목록");
        ZoneId.getAvailableZoneIds().stream().sorted().forEach(System.out::println);

    }

    @Test
    void ZoneOffset으로_오프셋_조작하기() {
        // ZoneOffset 상수값 사용하기
        System.out.println("시스템 오프셋: " + ZoneOffset.systemDefault());
        System.out.println("UTC: " + ZoneOffset.UTC);
        System.out.println("오프셋 최대값: " + ZoneOffset.MAX);
        System.out.println("오프셋 최소값: " + ZoneOffset.MIN);

        // 고정된 오프셋 지정하기
        ZoneOffset plusOneOffset = ZoneOffset.ofHours(1);
        ZoneOffset minusOffset = ZoneOffset.ofHours(-1);
        ZoneOffset plusNineOffset = ZoneOffset.of("+09:00");

        System.out.println("고정 오프셋: " + ZoneId.ofOffset("UTC", plusOneOffset));
        System.out.println("고정 오프셋: " + ZoneId.ofOffset("UTC", minusOffset));
        System.out.println("고정 오프셋: " + ZoneId.ofOffset("UTC", plusNineOffset));
    }

}

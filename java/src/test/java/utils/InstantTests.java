package utils;


import java.time.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InstantTests {

    @Test
    void 현재_시간_출력하기() {
        System.out.println(Instant.now());
    }

    @Test
    void 에포크_타임을_대한민국_시간으로_변환하기() {
        Instant epochTime = Instant.EPOCH;

        System.out.println(epochTime.atZone(ZoneId.of("Asia/Seoul")));
        System.out.println(epochTime.atOffset(ZoneOffset.ofHours(9)));
    }

    @Test
    void Instant_변환하기() {
        Instant now = Instant.now();

        // Instant -> LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, ZoneId.systemDefault());

        // Instant -> ZonedDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, ZoneId.systemDefault());
        ZonedDateTime zonedDateTime2 = now.atZone(ZoneId.systemDefault());

        // Instant -> OffsetDateTime
        OffsetDateTime offsetDateTime = now.atOffset(ZoneOffset.ofHours(9));

        // LocalDateTime -> Instant
        Instant fromLocal = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();

        System.out.println(now);
        System.out.println(localDateTime);
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTime2);
        System.out.println(offsetDateTime);
        System.out.println(fromLocal);
    }

    @Test
    void epoch_기반_생성하기() {
        // 1970-01-01T01:00:00Z (1시간)
        Instant epochSecond = Instant.ofEpochSecond(3600);

        // 1970-01-01T01:00:00Z (1시간)
        Instant epochMilli = Instant.ofEpochMilli(3600000);

        Instant epochNano = Instant.ofEpochSecond(1, 500_000_000);

        System.out.println(epochSecond);
        System.out.println(epochMilli);
        System.out.println(epochNano);
    }
}

package time;


import java.time.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class OffsetDateTimeTests {

    @Test
    void 현재_시간_구하기() {
        System.out.println("시스템 현재 시간: " + OffsetDateTime.now());
    }

    @Test
    void OffsetDateTime_변환하기() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();

        // OffsetDateTime -> Others
        Instant instant = offsetDateTime.toInstant();
        ZonedDateTime zonedDateTime = offsetDateTime.toZonedDateTime();
        ZonedDateTime newYork_zonedDateTime = offsetDateTime.atZoneSameInstant(ZoneId.of("America/New_York"));
        LocalDateTime localDateTime = offsetDateTime.toLocalDateTime();
        long epochSecond = offsetDateTime.toEpochSecond();

        // Others -> OffsetDateTime
        OffsetDateTime fromInstant = instant.atOffset(ZoneOffset.from(offsetDateTime));
        OffsetDateTime fromZonedDateTime = zonedDateTime.toOffsetDateTime();
        OffsetDateTime fromLocalDateTime = localDateTime.atOffset(ZoneOffset.from(offsetDateTime));

        Assertions.assertEquals(offsetDateTime, fromInstant);
        Assertions.assertEquals(offsetDateTime, fromZonedDateTime);
        Assertions.assertEquals(offsetDateTime, fromLocalDateTime);
    }
}

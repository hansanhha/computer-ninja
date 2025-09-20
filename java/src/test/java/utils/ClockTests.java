package utils;


import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClockTests {

    @Test
    void 시스템_시작_경과시간_구하기() {
        Duration systemStartupTime = Duration.ofNanos(System.nanoTime());

        long day = systemStartupTime.toDays();
        long hour = systemStartupTime.toHours() % 24;
        long minute = systemStartupTime.toMinutes() % 60;
        long second = systemStartupTime.toSeconds() % 60;

        System.out.printf("%d일 %d시간 %d분 %d초\n", day, hour, minute, second);
    }

    @Test
    void SystemClock_테스트() {
        Clock clock = Clock.systemDefaultZone();

        System.out.println("현재 시간(UTC): " + clock.instant().atZone(ZoneId.systemDefault()));
        System.out.println("현재 시간(유닉스 타임스탬프): " + clock.millis()); // System.currentTimeMillis()
    }

    @Test
    void FixedClock_테스트() {
        Clock fixedClock = Clock.fixed(Instant.ofEpochSecond(0), ZoneId.systemDefault());

        System.out.println("0 EpochTime: " + fixedClock.instant().atZone(ZoneId.systemDefault()));
    }

    @Test
    void OffsetClock_테스트() {
        Clock utcClock = Clock.systemUTC();

        System.out.println("기준 UTC 시간: " + utcClock.instant());

        Clock seoulClock = Clock.offset(utcClock, Duration.ofHours(9));
        Clock newyorkClock = Clock.offset(utcClock, Duration.ofHours(-4));

        System.out.println("서울 시간: " + seoulClock.instant());
        System.out.println("뉴욕 시간: " + newyorkClock.instant());
    }

    @Test
    void TickClock_테스트() throws InterruptedException {
        Clock clock = Clock.tickSeconds(ZoneId.systemDefault());
        System.out.println("기준: " + clock.instant());

        Thread.sleep(500);
        System.out.println("0.5초 후: " +  clock.instant());

        Thread.sleep(500);
        System.out.println("1초 후: " + clock.instant());
    }

}

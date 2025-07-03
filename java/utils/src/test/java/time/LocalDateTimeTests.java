package time;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LocalDateTimeTests {

    @Test
    void 오늘_날짜_구하기() {
        System.out.println("오늘: " + LocalDate.now());
    }

    @Test
    void 특정_날짜_구하기() {
        System.out.println("날짜: " + LocalDate.of(1234, 5,6));
    }

    @Test
    void 유닉스_시간으로부터_날짜_구하기() {
        System.out.println("유닉스 시간+10일: " + LocalDate.ofEpochDay(10));
    }

    @Test
    void 이번달_남은_날짜들_구하기() {
        LocalDate now = LocalDate.now();

        int lengthOfMonth = now.lengthOfMonth();
        int dayOfMonth = now.getDayOfMonth();

        Stream<LocalDate> daysOfMonth = now.datesUntil(LocalDate.now().plusDays(lengthOfMonth - dayOfMonth));

        daysOfMonth.forEach(System.out::println);
    }

    @Test
    void 현재_로컬_시간_구하기() {
        System.out.println(LocalTime.now());
    }

    @Test
    void 지금_구하기() {
        System.out.println(LocalDateTime.now());
    }

}

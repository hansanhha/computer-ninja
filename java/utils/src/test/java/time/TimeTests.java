package time;


import java.time.*;
import java.time.temporal.WeekFields;
import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TimeTests {

    @Test
    void 올해_구하기() {
        Year year = Year.now();
        System.out.println("올해 연도: " + year.getValue() + "년");
        System.out.println("윤년 여부: " + year.isLeap());
        System.out.println("올해 100일: " + year.atDay(100));
    }

    @Test
    void 이번달_구하기() {
        YearMonth month = YearMonth.now();
        System.out.printf("이번 달: %s월(%s)\n", month.getMonthValue(), Month.of(month.getMonthValue()).name());
        System.out.println("이번 달 일수: " + month.lengthOfMonth() + "일");
    }

    @Test
    void 이번주_구하기() {
        System.out.println("이번 주: " + LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear()) + "번째 주");
    }

    @Test
    void 오늘_날짜_구하기() {
        System.out.println("오늘 날짜: " + MonthDay.now().getDayOfMonth() + "일");
    }

    @Test
    void 오늘_요일_구하기() {
        System.out.println("오늘 요일: " + DayOfWeek.of(WeekFields.ISO.getMinimalDaysInFirstWeek()));
    }

    @Test
    void 올해_1월_1일_요일_구하기() {
        System.out.println("올해 1월 1일 요일: " + Year.now().atMonth(1).atDay(1).getDayOfWeek());
    }

    @Test
    void 올해_크리스마스_요일_구하기() {
        System.out.println("올해 크리스마스 요일: " + Year.now().atMonth(12).atDay(25).getDayOfWeek());
    }

    @Test
    void 이번달_달력_출력하기() {
        YearMonth month = YearMonth.now();
        LocalDate firstDayOfMonth = month.atDay(1);
        DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();
        int firstDayIndex = firstDayOfWeek.getValue();
        int monthLength = month.lengthOfMonth();
        int today = LocalDate.now().getDayOfMonth();

        System.out.printf("%d년 %d월달력\n\n", Year.now().getValue(), month.getMonthValue());

        List.of(DayOfWeek.values()).forEach(dow -> System.out.printf("%10s\t", dow.name()));
        System.out.println();

        for (int i = 1; i < firstDayIndex; i++) {
            System.out.printf("%10s\t", "");
        }

        for(int day = 1; day <= monthLength; day++) {
            if (today == day) System.out.printf("%10d*\t", day);
            else System.out.printf("%10d\t", day);

            int currentDayOfWeek = (firstDayIndex + day - 1) % 7;
            if (currentDayOfWeek == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    @Test
    void 시간간격_구하기() {
        Duration between = Duration.between(
                LocalTime.of(10, 0),
                LocalTime.of(12, 30)
        );

        System.out.println(between);
    }

    @Test
    void 날짜간격_구하기() {
        Period between = Period.between(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2025, 5, 22)
        );

        System.out.println(between);
    }

}

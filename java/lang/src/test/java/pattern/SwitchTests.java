package pattern;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayName("스위치 패턴 매칭 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SwitchTests {

    @Test
    void 기존_switch문은_상수값_기반_분기만_가능하다() {
        /*
            기존 switch 문법

            switch (value) {
                case condition:
                    statement;
                    break;
                case condition:
                    statement;
                    break;
                default:
                    statement;
            }
         */

        int value = 10;

        switch (value) {
            case 1:
                System.out.println("value는 1이다");
                break;
            case 2:
                System.out.println("value는 2이다");
                break;
            default:
                System.out.println("value는 1도 아니고 2도 아니다");
        }
    }

    @Test
    void 개선된_switch문은_객체_분기도_가능하다() {

        /*
            개선된 switch 문법

            switch (value) {
                case condition -> statement;
                case condition -> statement;
                default -> statement;
            }

            - switch 안에서 instanceof + 변수 바인딩이 가능하다
            - break 키워드가 필요없다
            - case 문에 화살표(->)를 사용하여 간결하게 표현할 수 있다 (람다 스타일)
         */

        Object value = "hello switch";
        Object intValue = 10;

        // 컴파일러가 자동으로 타입 검사와 형 변환을 수행한다
        switch (intValue) {
            case String str -> System.out.println("value는 String 타입: " + str);
            case Integer num -> System.out.println("value는 Integer 타입: " + num);
            default -> System.out.println("value는 다른 타입이다: " + value.getClass().getSimpleName());
        }

    }

    @Test
    void yield_키워드를_사용하여_값을_반환하는_표현식을_작성할수있다() {
        String day = "UNKNOWN";

        // 기존 switch 문에서 값을 조작하는 방법
        String result;
        switch (day) {
            case  "MONDAY":
                result = "월요일";
                break;
            case "TUESDAY":
                result = "화요일";
                break;
            default:
                result = "알 수 없는 요일";
        }

        String result2 = switch (day) {
            case "MONDAY" -> "월요일";
            case "TUESDAY" -> "화요일";

            // 블록에서 값을 반환할 때 yield 키워드를 사용한다
            default -> {
                System.out.println("알 수 없는 요일을 처리한다");
                yield "알 수 없는 요일";
            }
        };

        System.out.println("result: " + result); // result: 300
    }

    @Test
    void record를_이용하면_구조적_분해를_할수있다() {
        Circle circle = new Circle(10.0);
        Rectangle rectangle = new Rectangle(5.0, 10.0);

        area(circle);
        area(rectangle);
    }

    @Test
    void sealed_클래스를_이용하면_완전한_분기가_가능하다() {
        Circle circle = new Circle(10.0);
        Rectangle rectangle = new Rectangle(5.0, 10.0);

        describe(circle);
        describe(rectangle);
    }

    @Test
    void when_절을_이용하면_구조적_분해에_조건을_추가할수있다() {
        Circle circle = new Circle(10.0);
        Rectangle rectangle = new Rectangle(5.0, 10.0);
        Rectangle square = new Rectangle(10.0, 10.0);

        advancedDescribe(circle);
        advancedDescribe(rectangle);
        advancedDescribe(square);
    }

    double area(Shape shape) {
        /*
            구조적 분해 패턴 매칭을 사용하여 레코드의 필드를 직접 추출할 수 있다
            기존에는 ((Rectangle) shape).width()와 같이 명시적인 형변환이 필요했다
         */
        return switch (shape) {
            case Rectangle(double w, double h) -> w * h;
            case Circle(double r) -> Math.PI * r * r;
        };
    }

    void describe(Shape shape) {
        /*
            sealed 인터페이스를 사용하여 모든 하위 타입을 완전히 분기할 수 있다
            case 문이 하나라도 빠지면 컴파일 에러가 발생한다
         */
        switch (shape) {
            case Circle(double radius) -> System.out.println("원 둘레: " + radius);
            case Rectangle(double width, double height) -> System.out.println("직사각형 너비: " + width + ", 높이: " + height);
        }
    }

    void advancedDescribe(Shape shape) {
        switch (shape) {
            case Rectangle(double w, double h) when w == h -> System.out.println("정사각형");
            case Rectangle(double w, double h) -> System.out.println("직사각형");
            case Circle(double r) -> System.out.println("원");
        }
    }

    sealed interface Shape permits Circle, Rectangle { }

    record Circle(double radius) implements Shape { }
    record Rectangle(double width, double height) implements Shape { }

}

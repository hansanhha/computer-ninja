package syntax.unnamed;


import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayName("Unnamed 관련 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UnnamedTest {

    @Test
    void unnamed_패턴을_이용하면_패턴매칭시_변수_바인딩을_생략할수있다() {

        Shape circle = new Circle(10.0);
        Shape rectangle = new Rectangle(5.0, 10.0);

        // _ 와일드카드 패턴을 사용하여 변수에 바인딩하지 않고 형 검사만 수행할 수 있다
        if (circle instanceof Circle _) {
            System.out.println("원");
        }

        if (rectangle instanceof Rectangle _) {
            System.out.println("직사각형");
        }
    }

    @Test
    void unnamed_변수를_이용하면_지역변수를_생략할수있다() throws IOException {
        for (String _ : List.of("a", "b", "c")) {
            // 아무것도 하지 않는다
        }

        try (var _ = openConnection()) {
            // 연결은 열어야 하는데 변수는 쓰지 않는다
        }

    }

    sealed interface Shape {}
    record Circle(double radius) implements Shape {}
    record Rectangle(double width, double height) implements Shape {}

    private Closeable openConnection() {
        // 연결을 여는 로직
        return Object::new;
    }

}

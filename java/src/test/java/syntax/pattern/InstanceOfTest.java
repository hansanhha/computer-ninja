package syntax.pattern;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayName("instanceof 패턴 매칭 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class InstanceOfTest {

    @Test
    void 기존_instanceof_연산_이후_명시적인_형변환이_필요하다() {
        Object obj = "hello instanceof";

        // 명시적인 형변환 필요
        if (obj instanceof String) {
            String str = (String) obj;
        }

    }

    @Test
    void instanceof_연산_이후_형변환이_자동으로_이루어진다() {
        Object obj = "hello instanceof";

        // instanceof 연산 후 자동으로 타입 검사와 함께, 변수에 할당해준다
        if (obj instanceof String str) {
            // str은 String 타입으로 자동 형변환된다
            System.out.println(str);
        }

    }

}

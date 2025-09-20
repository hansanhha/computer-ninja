package syntax.string;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayName("문자열 템플릿 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StringTemplateTest {


    @Test
    void 문자열_템플릿으로_가독성과_안전성을_높일수있다() {
        String name = "hansanhha";
        String language = "java";

        // 문자열 연결 방식
        String greeting = "Hello, " + name + " language: " + language + ".";

        // String.format() 방식
        String greeting2 = String.format("Hello, %s language: %s.", name, language);


    }
}

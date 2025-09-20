package syntax.string;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayName("텍스트 블록 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TextBlockTest {

    @Test
    void 텍스트_블록은_줄바꿈_탭_따옴표를_그대로_포함한다() {
        String user =
                """
                {
                    "name": "hansanhha",
                    "language": java,
                }
                """;

        System.out.println(user);
    }


}

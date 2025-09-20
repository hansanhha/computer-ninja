package syntax.record;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


@DisplayName("로컬 레코드 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class LocalRecordTest {

    @ParameterizedTest
    @ValueSource(strings = {"java", "kotlin", "python"})
    void 레코드는_메서드_내부에_정의할_수_있다(String word) {

        // 메서드 내부에 레코드 타입 정의
        record WordLength(String word, int length) {}

        WordLength wordLength = new WordLength(word, word.length());
        
    }


}

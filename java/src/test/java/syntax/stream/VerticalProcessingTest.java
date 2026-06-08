package syntax.stream;

import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class VerticalProcessingTest {
    
    @Test
    void 스트림은_수직적으로_데이터를_처리한다() {
        List<Integer> result = Stream.of(1, 2, 3, 4)
                              .filter(n -> {
                                System.out.println("filter: " + n);
                                return n % 2 == 0;
                              })
                              .map(n -> {
                                System.out.println("map: " + n);
                                return n * 10;
                              })
                              .toList();

        Assertions.assertThat(result.size()).isEqualByComparingTo(2);
    }
}

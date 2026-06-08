package syntax.stream;

import java.util.List;
import java.util.stream.Gatherers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class StreamAPITest {
    
    @Test
    void mapMulti는_스트림을_만들지않는다() {
        List<String> phrases = List.of("Hello World", "Java Stream");

        List<String> words = phrases.stream()
            .<String>mapMulti((phrase, consumer) -> {
                for (String word : phrase.split(" ")) {
                    consumer.accept(word); // 스트림을 만들지 않고 바로 소비자에 전달
                }
            })
            .toList();

        Assertions.assertThat(words).containsExactly("Hello", "World", "Java", "Stream");
    }

    @Test
    void gather_windowFixed는_특정개수로_쪼개서_배치처리한다() {
        List<Integer> numbers = List.of(1 ,2 ,3, 4, 5, 6, 7);

        // windowFixed: 데이터를 특정 개수 단위로 쪼개서 대량 배치 처리
        List<List<Integer>> batch = numbers.stream()
            .gather(Gatherers.windowFixed(3))
            .toList();

        // [[1, 2, 3], [4, 5, 6], [7]]
        System.out.println(batch);
    }

    @Test
    void gather_windowSliding은_슬라이딩윈도우로_배치처리한다() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // windowSliding: 윈도우 슬라이딩 기능
        List<List<Integer>> sliding = numbers.stream()
            .gather(Gatherers.windowSliding(3))
            .toList();

        // [[1, 2, 3], [2, 3, 4], [3, 4, 5]]
        System.out.println(sliding);
    }
 
    @Test
    void gather_scan은_데이터가_흐를때마다_누적된결과를_방출한다() {
        List<String> words = List.of("A", "B", "C");

        // 연속적인 상태 누적
        // reducde()는 스트림을 끝내고 최종 결과 1개만 주지만 scan은 과정의 스트림을 유지한다
        List<String> runningAppends = words.stream()
            .gather(Gatherers.scan(() -> "", (acc, element) -> acc + element))
            .toList();

        // [A, AB, ABC]
        System.out.println(runningAppends); 
    }

}

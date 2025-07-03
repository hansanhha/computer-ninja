package concurrent;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("뮤텍스를 활용한 동기화 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MutexTest {

    // 테스트 실패 가능성 있음
    @Test
    @DisplayName("동기화 처리를 하지 않으면 경쟁 상태가 발생하여 예상치 못한 결과를 초래한다")
    void shouldRaiseRaceCondition() {
        SharedCounter sharedCounter = new SharedCounter();

        try (ExecutorService executor = Executors.newFixedThreadPool(1000)) {
            for (int i = 0; i < 1000; i++) {
                executor.submit(sharedCounter::incrementUnsafe);
            }
        }

        assertThat(sharedCounter.getCount()).isNotEqualTo(1000);
    }

    @Test
    @DisplayName("뮤텍스를 사용하여 동기화 처리를 하면 경쟁 상태를 방지할 수 있다")
    void shouldPreventRaceCondition() {
        SharedCounter sharedCounter = new SharedCounter();

        try (ExecutorService executor = Executors.newFixedThreadPool(1000)) {
            for (int i = 0; i < 1000; i++) {
                executor.submit(sharedCounter::incrementSafe);
            }
        }

        assertThat(sharedCounter.getCount()).isEqualTo(1000);
    }

    static class SharedCounter {
        private int count = 0;

        public void incrementUnsafe() {
            count++;
        }

        // 메서드 전체를 임계 구역으로 설정 (뮤텍스 구현)
        public synchronized void incrementSafe() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}

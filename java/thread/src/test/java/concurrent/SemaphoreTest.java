package concurrent;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayName("세마포어를 활용한 동기화 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SemaphoreTest {

    @Test
    void semaphoreUsingExample() {
        Semaphore semaphore = new Semaphore(3);

        try (ExecutorService executor = Executors.newFixedThreadPool(100)){
            for (int i = 0; i < 100; i++) {
                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        System.out.println("Thread " + Thread.currentThread().getName() + " 세마포어 획득");

                        // 작업 시뮬레이션
                        Thread.sleep(10);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        System.out.println("Thread " + Thread.currentThread().getName() + " 세마포어 해제");
                        semaphore.release();
                    }
                });
            }
        }
    }

}

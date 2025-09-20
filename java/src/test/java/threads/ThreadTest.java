package threads;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("ScopedValue 관련 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ThreadTest {
    
    ThreadLocal<String> username = new ThreadLocal<>();

    @Test
    void Runnable타입의_람다식으로_Thread를_생성할수있다() {
        Runnable task = () -> System.out.println("hello thread");
        Thread thread = new Thread(task);
        thread.start();
    }

    @Test
    void 스레드로컬은_스레드별로_독립적으로_동작한다() {
        Runnable task = () -> {
            String name = Thread.currentThread().getName();
            username.set(name);
            try {
                System.out.println("current thread name: " + username.get());
            } finally {
                username.remove();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start();
        t2.start();
    }

    @Test
    void 동기vs비동기() throws InterruptedException, ExecutionException {
        System.out.println("========== synchronization task ==========");
        long syncStart = System.currentTimeMillis();

        IntStream.rangeClosed(1, 5)
            .map(this::longTask)
            .forEach(result -> System.out.println("result: " + result));

        long syncElapsedTime = System.currentTimeMillis() - syncStart;


        System.out.println();
        System.out.println("========== asynchronization task ==========");
        long asyncStart = System.currentTimeMillis();

        List<CompletableFuture<Integer>> futures = 
            IntStream.rangeClosed(1, 5)
                     .mapToObj(i -> CompletableFuture.supplyAsync(() -> longTask(i)))
                     .toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<Integer> future : futures) {
            System.out.println("result: " + future.get());
        }

        long asyncElapsedTime = System.currentTimeMillis() - asyncStart;

        assertThat(syncElapsedTime).isGreaterThan(asyncElapsedTime);

        System.out.println();
        System.out.println("syncElapsedTime: " + syncElapsedTime);
        System.out.println("asyncElapsedTime: " + asyncElapsedTime);
    }

    int longTask(int id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        System.out.println("task completed: " + id + " on " + Thread.currentThread().getName() + " thread");
        return id * 10;
    }

}

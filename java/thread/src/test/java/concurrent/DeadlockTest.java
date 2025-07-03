package concurrent;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayName("데드락 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class DeadlockTest {

//    @Test
    @DisplayName("데드락 상태에 빠진다")
    void shouldFallIntoDeadlock() {
        final Object resourceA = new Object();
        final Object resourceB = new Object();

        // t1은 resourceA를 먼저 획득하고, resourceB를 획득하려고 시도한다
        Thread t1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + " acquired resource A");

                try {Thread.sleep(100);} catch (InterruptedException ignored) {}

                synchronized (resourceB) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource B");
                }
            }
        });

        // t2는 resourceB를 먼저 획득하고, resourceA를 획득하려고 시도한다
        Thread t2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + " acquired resource B");

                try {Thread.sleep(100);} catch (InterruptedException ignored) {}

                synchronized (resourceA) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource A");
                }
            }
        });

        t1.start();
        t2.start();

        // 두 스레드는 서로의 리소스를 기다리며 데드락 상태에 빠진다
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException ignored) {}

    }

    @Test
    @DisplayName("ReentrantLock을 이용하여 데드락 상태를 피한다")
    void shouldAvoidDeadlockUsingReentrantLock() {
        ReentrantLock lock1 = new ReentrantLock();
        ReentrantLock lock2 = new ReentrantLock();

        Runnable task = () -> {
            if (lock1.tryLock()) {
                System.out.println(Thread.currentThread().getName() + " acquired lock1");

                try {
                    Thread.sleep(100);

                    if (lock2.tryLock()) {
                        System.out.println(Thread.currentThread().getName() + " acquired lock2");

                        try {Thread.sleep(100);}
                        catch (InterruptedException ignored) {}
                        finally {
                            System.out.println(Thread.currentThread().getName() + " released lock2");
                            lock2.unlock();
                        }
                    }
                }
                catch (InterruptedException ignored) {}
                finally {
                    System.out.println(Thread.currentThread().getName() + " released lock1");
                    lock1.unlock();
                }
            }
        };

        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            for (int i = 0; i < 2; i++) {
                executor.submit(task);
            }
        }
    }


}

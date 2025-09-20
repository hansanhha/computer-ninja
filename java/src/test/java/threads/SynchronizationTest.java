package threads;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("동기화 관련 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SynchronizationTest {

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

    // @Test
    @DisplayName("데드락 상태에 빠진다")
    void shouldFallIntoDeadlock() {
        final Object resourceA = new Object();
        final Object resourceB = new Object();

        // t1은 resourceA를 먼저 획득하고, resourceB를 획득하려고 시도한다
        Thread t1 = new Thread(() -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + " acquired resource A");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }

                synchronized (resourceB) {
                    System.out.println(Thread.currentThread().getName() + " acquired resource B");
                }
            }
        });

        // t2는 resourceB를 먼저 획득하고, resourceA를 획득하려고 시도한다
        Thread t2 = new Thread(() -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + " acquired resource B");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }

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
        } catch (InterruptedException ignored) {
        }

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

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ignored) {
                        } finally {
                            System.out.println(Thread.currentThread().getName() + " released lock2");
                            lock2.unlock();
                        }
                    }
                } catch (InterruptedException ignored) {
                } finally {
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

    @Test
    void semaphoreUsingExample() {
        Semaphore semaphore = new Semaphore(3);

        try (ExecutorService executor = Executors.newFixedThreadPool(100)) {
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

    @Test
    void CyclicBarrierExample() {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            System.out.println("arrived all threads, execute next step");
        });

        Runnable task = () -> {
            System.out.println("ready " + Thread.currentThread().getName());
            try {
                barrier.await();
                System.out.println("execute " + Thread.currentThread().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
            for (int i = 0; i < 3; i++) {
                executor.submit(task);
            }
        }
    }

    @Test
    void CountDownLatchExample() throws InterruptedException {
        int workers = 5;
        CountDownLatch latch = new CountDownLatch(workers);
        ExecutorService executor = Executors.newFixedThreadPool(workers);

        for (int i = 0; i < workers; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    System.out.println("start task: " + Thread.currentThread().getName());
                    Thread.sleep(taskId * 100);
                    System.out.println("completed task: " + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        System.out.println("waiting for all threads task...");
        latch.await();
        System.out.println("all tasks completed. next code executing");
    }

    @Test
    void ReentrantLock은_재진입할수있다() {
        ReentrantLock lock = new ReentrantLock();

        try {
            lock.lock();
            try {
                lock.lock(); // 재진입
            } finally {
                lock.unlock(); // 모든 락을 해제해야 완전히 락이 해제된다
            }
        } finally {
            lock.unlock();
        }
    }

    @Test
    void ReentrantLock은_기본적으로_비공정락이며_공정락으로도_동작할수있다() throws InterruptedException {
        int threads = 10;
        ReentrantLock unfairLock = new ReentrantLock();
        ReentrantLock fairLock = new ReentrantLock(true);
        CountDownLatch latch = new CountDownLatch(threads);

        System.out.println("=============== unfair lock ===============");
        try (ExecutorService executor = Executors.newFixedThreadPool(threads)) {
            for (int i = 0; i < threads; i++) {
                executor.submit(() -> {
                    unfairLock.lock();
                    System.out.println("acquired lock: " + Thread.currentThread().getName());
                    unfairLock.unlock();
                    latch.countDown();
                });
            }
        }

        latch.await();

        System.out.println("=============== fair lock ===============");
        try (ExecutorService executor = Executors.newFixedThreadPool(threads)) {
            for (int i = 0; i < threads; i++) {
                executor.submit(() -> {
                    fairLock.lock();
                    System.out.println("acquired lock: " + Thread.currentThread().getName());
                    fairLock.unlock();
                });
            }
        }
    }

    @Test
    void ReentrantLock의_일반락_획득() {
        ReentrantLock lock = new ReentrantLock();

        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
            for (int i = 0; i < 5; i++) {
                executor.submit(() -> {
                    lock.lock();
                    try {
                        System.out.println("acquired lock: " + Thread.currentThread().getName() + " at " + LocalDateTime.now());
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("release lock: " + Thread.currentThread().getName()  + " at " + LocalDateTime.now());
                        lock.unlock();
                    }
                });
            }
        }
    }

    @Test
    void ReadWriteLockExample() {
        
    }

}

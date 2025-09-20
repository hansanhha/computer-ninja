package threads;


import java.util.concurrent.Executors;


public class ThreadExample {

    // 전역 변수(메서드 영역): 공유 대상
    private static int staticVar;

    // 인스턴스 변수(힙): 공유 대상
    // 각 인스턴스마다 별도로 가지지만, 인스턴스를 공유할 수 있기 때문에 공유 대상이 된다
    private int instanceVar;

    // 스레드 로컬: 각 스레드마다 별도로 가진다
    private ThreadLocal<Integer> threadLocalVar = ThreadLocal.withInitial(() -> 0);

    public void main() {
        Runnable run = () -> {
            // 메서드 지역 변수(스택): 각 스레드마다 별도로 가진다
            int methodLocalVar = 0;
            staticVar++;
            instanceVar++;
            threadLocalVar.set(threadLocalVar.get() + 1);
        };

        try (var executor = Executors.newFixedThreadPool(2)) {
            executor.submit(run);
            executor.submit(run);
        } catch (Exception e) {
        }

        System.out.println("staticVar: " + staticVar);
        System.out.println("instanceVar: " + instanceVar);
        System.out.println("threadLocalVar: " + threadLocalVar.get());
    }

}

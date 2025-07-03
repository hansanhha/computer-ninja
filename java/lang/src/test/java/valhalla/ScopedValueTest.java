package valhalla;


import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("ScopedValue 관련 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ScopedValueTest {

    // 새로운 스코프값 `ScopedValue<T>` 생성, 마치 스레드 로컬처럼 값을 전달할 통로(키)를 생성한다
    static final ScopedValue<User> USER_SCOPED_VALUE = ScopedValue.newInstance();

    @Test
    void 스코프블록안에_값을_바인딩하고_접근한다() {
        /*
            ScopedValue.where(key, value).run(Runnable): 지정한 값으로 컨텍스트를 바인딩하고 실행한다

            key.isBound(): 현재 스코프에서 값이 바인딩되어 있는지 확인한다
            key.get(): 현재 바인딩된 값을 반환한다 (스코프 밖에서 접근하면 예외 발생)
            key.orElse(<T>): 값이 바인딩되어 있지 않으면 주어진 값을 반환한다
            key.orElseThrow(Supplier<Exception>): 값이 바인딩되어 있지 않으면 주어진 예외를 발생시킨다

            ScopeValue는 값을 설정하는 메서드가 없다
            대신, 스코프 블록 안에 값을 바인딩하고 실행하는 방식으로 사용한다
         */
        ScopedValue.where(USER_SCOPED_VALUE, new User("hansanhha")).run(() -> {
            System.out.println("스코프 바운드: " + USER_SCOPED_VALUE.isBound());
            greet();
        });
    }

    @Test
    void 스코프블록안에_값을_바인딩하지않으면_접근할수없다() {
        assertThatThrownBy(this::greet).isExactlyInstanceOf(IllegalStateException.class);
    }

    @Test
    void 스코프밖에서_접근하면_예외가_발생한다() {
        assertThatThrownBy(() -> USER_SCOPED_VALUE.get()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 가상스레드에서도_스코프를_사용할수있다() {
        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            System.out.println("스레드: " + Thread.currentThread().threadId());

            // 가상 스레드에서 실행할 스코프와 Runnable 설정
            executorService.submit(() ->
                    ScopedValue.where(USER_SCOPED_VALUE, new User("hansanhha")).run(() -> {
                        System.out.println("가상 스레드에서 스코프 바운드 접근, 스레드: " + Thread.currentThread().threadId());
                        greet();
                    })
            );
        }
    }

    @Test
    void 스코프를_중첩할수있다() {
        // 가장 가까운 바인딩이 우선 적용된다
        ScopedValue.where(USER_SCOPED_VALUE, new User("hansanhha")).run(() -> {

            System.out.println("외부 스코프 접근");
            greet();

            ScopedValue.where(USER_SCOPED_VALUE, new User("new user")).run(() -> {
                System.out.println("내부 스코프 접근");
                greet();
            });

            greet();
        });
    }

    void greet() {
        User bound = USER_SCOPED_VALUE.orElseThrow(IllegalStateException::new);
        System.out.println("이름: " + bound.name);
    }

    record User(
            String name) {

    }

}

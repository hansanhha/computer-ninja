package threads;


import java.util.NoSuchElementException;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("ScopedValue 관련 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ScopedValueTest {

    // 새로운 스코프값 `ScopedValue<T>` 생성
    // 스레드 로컬처럼 여러 컴포넌트에서 쉽게 접근할 수 있다
    static final ScopedValue<User> BINDED_USER = ScopedValue.newInstance();

    @Test
    void 스코프블록안에_값을_바인딩하고_접근한다() {
        /*
            ScopedValue.where(ScopedValue<T>, T).run(Runnable)
            -> 지정한 값으로 컨텍스트를 바인딩하고 실행한다

            where 메서드를 통해 스코프 블록 안에 데이터를 바인딩한다
            run 메서드가 호출된 시점엔 현재 스레드에서 고유로 식별 가능한 스코프 값은 바인딩되어 있고 Runnable 람다식을 호출한다
            해당 람다식에서 직/간접적으로 바인딩된 값에 접근할 수 있으나 run 메서드가 종료되면 바인딩된 값은 제거된다
         */
        ScopedValue
        .where(BINDED_USER, new User("hansanhha"))
        .run(() -> {
            greet();
        });
    }

    @Test
    void 스코프블록안에_바인딩된_키의_존재여부를_확인할수있다() {
        /*
            key.isBound(): 현재 스코프에서 값이 바인딩되어 있는지 확인한다
            key.get(): 현재 바인딩된 값을 반환한다 (스코프 밖에서 접근하면 예외 발생)
            key.orElse(<T>): 값이 바인딩되어 있지 않으면 주어진 값을 반환한다
            key.orElseThrow(Supplier<Exception>): 값이 바인딩되어 있지 않으면 주어진 예외를 발생시킨다
         */
        ScopedValue
        .where(BINDED_USER, new User("hansanhha"))
        .run(() -> {
            assertThat(BINDED_USER.isBound()).isTrue();
            assertThat(BINDED_USER.get()).isEqualTo(new User("hansanhha"));
        });
    }

    @Test
    void run_메서드_밖에서_스코프블록의_값에_접근할수없다() {
        // 간접 접근
        assertThatThrownBy(this::greet).isExactlyInstanceOf(IllegalStateException.class);
        // 직접 접근
        assertThatThrownBy(() -> BINDED_USER.get()).isExactlyInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 가상스레드에서_스코프블록을_사용할수있다() {
        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            System.out.println("스레드: " + Thread.currentThread().threadId());

            // 가상 스레드에서 실행할 스코프와 Runnable 설정
            executorService.submit(() ->
                    ScopedValue.where(BINDED_USER, new User("hansanhha")).run(() -> {
                        System.out.println("가상 스레드에서 스코프 바운드 접근, 스레드: " + Thread.currentThread().threadId());
                        greet();
                    })
            );
        }
    }

    @Test
    void 스코프를_중첩할수있다() {
        // 가장 가까운 바인딩이 우선 적용된다
        ScopedValue.where(BINDED_USER, new User("hansanhha")).run(() -> {

            System.out.println("외부 스코프 접근");
            greet();

            ScopedValue.where(BINDED_USER, new User("new user")).run(() -> {
                System.out.println("내부 스코프 접근");
                greet();
            });

            greet();
        });
    }

    // 현재 스코프 블록에 바인딩된 값에 접근한다
    void greet() {
        User bound = BINDED_USER.orElseThrow(IllegalStateException::new);
        System.out.println("스코프 블록에 바인딩된 이름: " + bound.name);
    }

    record User(String name) {}

}

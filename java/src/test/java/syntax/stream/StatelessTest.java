package syntax.stream;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatelessTest {
    
    @Test
    void 병렬스트림에서_stateful연산은_부작용을발생시킨다() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add(new User("user-"+i));
        }

        List<String> names = new ArrayList<>();

        // names 컬렉션은 다른 곳에서도 사용된다고 가정
        addUserName(names);

        // 외부 상태를 건드리는 연산
        users.stream().forEach(u -> names.add(u.name()));

        // 스트림에서 100개를 처리했으나 다른 곳에서도 외부 상태를 사용하여 101개의 데이터가 저장됨
        Assertions.assertThat(names.size()).isNotEqualTo(100);
        Assertions.assertThat(names.size()).isEqualTo(101);
    }

    private record User(String name) {}
    private void addUserName(List<String> names) {
        names.add("VIP User");
    }
}

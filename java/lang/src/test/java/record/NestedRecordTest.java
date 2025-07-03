package record;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("중첩 레코드 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class NestedRecordTest {

    @Test
    void 레코드는_다른_레코드를_포함할_수_있다() {
        // 레코드 타입 정의
        record Address(String city, String street) {}
        record Person(String name, Address address) {}

        Address address = new Address("서울", "성동구");
        Person person = new Person("hansanhha", address);

        assertThat(person.address).isEqualTo(address);
    }

    @Test
    void 레코드는_내부에_레코드를_정의할_수_있다() {
        // 레코드 타입 정의
        record Person(String name, Address address) {
            record Address(String city, String street) {}
        }

        Person.Address address = new Person.Address("서울", "성동구");
        Person person = new Person("hansanhha", address);

        assertThat(person.address).isEqualTo(address);
    }

}

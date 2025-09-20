package syntax.record;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("레코드 직렬화/역직렬화 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SerializationTest {

    @Test
    void 레코드는_직렬화가_가능하다() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // 레코드 타입 정의
        record Person(String name, int age) {}
        Person person = new Person("hansanhha", 1);

        // 직렬화
        String serializedPerson = mapper.writeValueAsString(person);

        // 역직렬화
        Person deserializedPerson = mapper.readValue(serializedPerson, Person.class);

        assertThat(person).isEqualTo(deserializedPerson);
        System.out.println(serializedPerson);
    }

    @Test
    void JsonCreator를_사용하여_필드명과_키를_매핑할수있다() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        record Person(String name, int age) {

            // 직렬화 시 name 필드가 fullName으로 매핑된다
            // 역직렬화 시 fullName 키를 name 필드로 매핑한다
            @JsonCreator
            Person(@JsonProperty("fullName") String name, @JsonProperty("age") int age) {
                this.name = name;
                this.age = age;
            }
        }

        Person person = new Person("hansanhha", 1);

        // 직렬화
        String serializedPerson = mapper.writeValueAsString(person);

        // 역직렬화
        Person deserializedPerson = mapper.readValue(serializedPerson, Person.class);

        assertThat(person).isEqualTo(deserializedPerson);
        System.out.println(serializedPerson);
    }


}

package hansanhha;


import hansanhha.basic.BasicEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class EntityMappingTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void 엔티티의_논리적이름과_물리적이름_검증() {
        EntityType<BasicEntity> basicEntityType = em.getMetamodel().entity(BasicEntity.class);
        String logicalName = basicEntityType.getName();
        String physicalName = "basic_entity_example";
        Set<Attribute<BasicEntity, ?>> logicalAttributes = basicEntityType.getDeclaredAttributes();

        // JPA 메타모델에서 논리적 엔티티 이름/필드 이름 검증
        assertThat(logicalName).isEqualTo("basicEntityExample");
        assertThat(logicalAttributes.stream().map(Attribute::getName)).containsExactlyInAnyOrder(
                "id",
                "username",
                "email"
        );

        // SQL을 통해 물리적 테이블 이름/필드 이름 검증
        assertThat(doesTableExist(physicalName)).isTrue();
        assertThat(getTableColumnNames(physicalName)).containsExactlyInAnyOrder(
                "basic_entity_id",
                "username",
                "email_address"
        );
    }

    private boolean doesTableExist(String tableName) {
        String sql = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, tableName.toLowerCase());
        return count != null && count > 0;
    }

    private List<String> getTableColumnNames(String tableName) {
        String sql = "SELECT column_name FROM information_schema.columns WHERE table_name = ?";
        return jdbcTemplate.queryForList(sql, String.class, tableName.toLowerCase());
    }

}

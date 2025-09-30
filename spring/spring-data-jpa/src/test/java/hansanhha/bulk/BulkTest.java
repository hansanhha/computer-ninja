package hansanhha.bulk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
@EntityScan(basePackageClasses = Account.class)
@DataJpaTest
public class BulkTest {
    
    @Autowired
    private AccountRepository repository;

    @Autowired
    private EntityManager em;

    Statistics stat;

    @BeforeEach
    void setup() {
        SessionFactory sf = em.unwrap(Session.class).getSessionFactory();
        stat = sf.getStatistics();
        stat.setStatisticsEnabled(true);
    }

    @AfterEach
    void tearDown() {
        stat.clear();
        stat.setStatisticsEnabled(false);
    }

    @Transactional
    @Commit
    void insertDummy() {
        List<Account> accounts = Stream
            .iterate(0, x -> x < 1000, x -> x + 1)
            .map(x -> new Account("test-"+x))
            .toList();

        repository.saveAll(accounts);
    }


    @Test
    void batch_1000_insert_query() {
        List<Account> accounts = Stream
            .iterate(0, x -> x < 1000, x -> x + 1)
            .map(x -> new Account("test-"+x))
            .toList();

        long start = System.currentTimeMillis();
        repository.saveAll(accounts);
        long elapsedTime = System.currentTimeMillis() - start;

        System.out.println("elapsed time: " + elapsedTime + "ms");
    }

    @Test
    void batch_1000_update_query() {
        insertDummy();

        long start = System.currentTimeMillis();
        repository.bulkUpdateInactivated(LocalDateTime.now());
        long elapsedTime = System.currentTimeMillis() - start;

        System.out.println("elapsed time: " + elapsedTime + "ms");
    }

}

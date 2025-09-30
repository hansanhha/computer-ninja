package hansanhha.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.persistence.EntityManager;

@EnableJpaRepositories(basePackageClasses = PBookStoreRepository.class)
@EntityScan(basePackageClasses = {PBook.class, PBookStore.class})
@DataJpaTest
public class PaginationFetchJoinTest {

    @Autowired
    private PBookStoreRepository repository;

    @Autowired
    private EntityManager em;

    Statistics stat;

    @BeforeEach
    void setup() {
        List<PBookStore> stores = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PBookStore store = new PBookStore("bookstore-" + i);
            PBookStore saved = repository.save(store);
            for (int j = 0; j < 100; j++) {
                saved.addBook(new PBook("book-"+j, LocalDate.now()));
            }
            stores.add(saved);
        }
        repository.saveAll(stores);
        em.flush();
        em.clear();
        

        SessionFactory sf = em.unwrap(Session.class).getSessionFactory();
        stat = sf.getStatistics();
        stat.setStatisticsEnabled(true);
    }

    @AfterEach
    void tearDown() {
        stat.clear();
        stat.setStatisticsEnabled(false);
    }

    @Test
    void inMemory_pagination() {
        // 하이버네이트 인메모리 페이지네이션 경고 메시지 출력
        // org.hibernate.orm.query: HHH90003004: firstResult/maxResults specified with collection fetch; applying in memory
        List<PBookStore> stores = repository.findStores(PageRequest.ofSize(5));
    }

    @Test
    void batchSizeLoading_without_pagination() {
        Page<PBookStore> stores = repository.findAll(PageRequest.ofSize(5));

        PBookStore store = stores.getContent().getFirst();
        PBook book = store.getBooks().getFirst();

        assertThat(stat.getQueryExecutionCount()).isEqualTo(2);
    }
}

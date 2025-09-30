package hansanhha.pagination;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

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
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.support.WindowIterator;

import jakarta.persistence.EntityManager;

@EnableJpaRepositories(basePackageClasses = PBookRepository.class)
@EntityScan(basePackageClasses = PBook.class)
@DataJpaTest
public class PaginationTest {
    
    private static int booksTotalSize = 1000;

    @Autowired
    private PBookRepository repository;

    @Autowired
    private EntityManager em;

    Statistics stat;

    @BeforeEach
    void setup() {
        List<PBook> books = new ArrayList<>(booksTotalSize);
        for (int i = 0; i < booksTotalSize; i++) {
            books.add(new PBook(
                "book-"+i, 
                LocalDate.of(2025, ThreadLocalRandom.current().nextInt(1, 13), ThreadLocalRandom.current().nextInt(1, 29))));
        }
        repository.saveAll(books);

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
    void zeroOffset_20Size_list() {
        long start = System.currentTimeMillis();
        List<PBook> books = repository.findAllToList(PageRequest.of(0, 20));
        long elapsedTime = System.currentTimeMillis() - start;
        
        assertThat(books).hasSize(20);
        assertThat(stat.getQueryExecutionCount()).isEqualTo(1);
        System.out.println("list(0 offset) elapsed time: " + elapsedTime + "ms");
    }

    @Test
    void zeroOffset_20Size_page() {
        long start = System.currentTimeMillis();
        Page<PBook> books = repository.findAllToPage(PageRequest.of(0, 20));
        long elapsedTime = System.currentTimeMillis() - start;
        
        assertThat(books.getContent()).hasSize(20);
        assertThat(books.getTotalElements()).isEqualTo(booksTotalSize);
        assertThat(books.hasContent()).isTrue();
        assertThat(books.hasNext()).isTrue();
        assertThat(books.hasPrevious()).isFalse();
        assertThat(stat.getQueryExecutionCount()).isEqualTo(2);
        System.out.println("page(0 offset) elapsed time: " + elapsedTime + "ms");
    }

    @Test
    void halfOffset_20size_page() {
        long start = System.currentTimeMillis();
        Page<PBook> books = repository.findAllToPage(PageRequest.of((booksTotalSize/20)/2, 20));
        long elapsedTime = System.currentTimeMillis() - start;
        
        assertThat(books.getContent()).hasSize(20);
        assertThat(books.getTotalElements()).isEqualTo(booksTotalSize);
        assertThat(books.hasContent()).isTrue();
        assertThat(books.hasNext()).isTrue();
        assertThat(books.hasPrevious()).isTrue();
        assertThat(stat.getQueryExecutionCount()).isEqualTo(2);
        System.out.println("total size: " + booksTotalSize);
        System.out.println("logical offset: " + (booksTotalSize/20)/2);
        System.out.println("physical offset: " + books.getPageable().getOffset());
        System.out.println("page number: " + books.getPageable().getPageNumber());
        System.out.println("page size: " + books.getPageable().getPageSize());
        System.out.println("number: " + books.getNumber());
        System.out.println("number of elements: " + books.getNumberOfElements());
        // page number == logical offset
        // page size == limit
        System.out.println("elapsed time: " + elapsedTime + "ms");
    }

    @Test
    void lastOffset_20size_page() {
        long start = System.currentTimeMillis();
        Page<PBook> books = repository.findAllToPage(PageRequest.of(booksTotalSize/20-1, 20));
        long elapsedTime = System.currentTimeMillis() - start;
        
        assertThat(books.getContent()).hasSize(20);
        assertThat(books.hasContent()).isTrue();
        assertThat(books.hasNext()).isFalse();
        assertThat(books.hasPrevious()).isTrue();
        assertThat(stat.getQueryExecutionCount()).isEqualTo(2);
        System.out.println("total size: " + booksTotalSize);
        System.out.println("logical offset: " + (booksTotalSize/20-1));
        System.out.println("physical offset: " + books.getPageable().getOffset());
        System.out.println("page number: " + books.getPageable().getPageNumber());
        System.out.println("page size: " + books.getPageable().getPageSize());
        System.out.println("elapsed time: " + elapsedTime + "ms");
    }

    @Test
    void zeroOffset_20Size_slice() {
        long start = System.currentTimeMillis();
        Slice<PBook> books = repository.findAllToSlice(PageRequest.of(0, 20));
        long elapsedTime = System.currentTimeMillis() - start;   

        assertThat(books.getContent()).hasSize(20);
        assertThat(books.getNumber()).isEqualTo(0);
        assertThat(books.hasContent()).isTrue();
        assertThat(books.hasNext()).isTrue();
        assertThat(books.hasPrevious()).isFalse();   
        assertThat(stat.getQueryExecutionCount()).isEqualTo(1);
        System.out.println("slice(0 offset) elapsed time: " + elapsedTime + "ms");
    }

    @Test
    void firstOffset_20Size_window() {
        long start = System.currentTimeMillis();
        Window<PBook> books = repository.findAllBy(PageRequest.ofSize(20), ScrollPosition.offset());
        long elapsedTime = System.currentTimeMillis() - start;   

        assertThat(books.getContent()).hasSize(20);
        assertThat(books.getContent().getFirst().getId()).isEqualTo(1);
        assertThat(books.hasNext()).isTrue();
        assertThat(books.isLast()).isFalse();
        assertThat(stat.getQueryExecutionCount()).isEqualTo(1);
        System.out.println("slice(0 offset) elapsed time: " + elapsedTime + "ms");        
    }

    @Test
    void firstKeyset_20Size_window() {
        // WindowIterator<Book> books = WindowIterator
        //     .of(position -> repository.findAllToKeysetWindow(PageRequest.ofSize(20), position))
        //     .startingAt(ScrollPosition.keyset());

        long start = System.currentTimeMillis();
        Window<PBook> books = repository.findAllBy(PageRequest.ofSize(20), ScrollPosition.keyset());
        long elapsedTime = System.currentTimeMillis() - start;   

        assertThat(books.getContent()).hasSize(20);
        assertThat(books.getContent().getFirst().getId()).isEqualTo(1);
        assertThat(books.hasNext()).isTrue();
        assertThat(books.isLast()).isFalse();
        assertThat(stat.getQueryExecutionCount()).isEqualTo(1);
        System.out.println("slice(0 offset) elapsed time: " + elapsedTime + "ms");
    }

    @Test
    void scrollKeysetForwardWithId_window() {
        long start = System.currentTimeMillis();
        Window<PBook> books = repository.findAllBy(PageRequest.ofSize(20), ScrollPosition.keyset());
        int totalFetchSize = books.size();

        // Book 엔티티의 id 필드를 기준으로 키 셋 페이지네이션
        while (books.hasNext()) {
            PBook lastBook = books.getContent().getLast();
            ScrollPosition nextPosition = books.positionAt(lastBook); // 아래 코드와 동일하다
            // ScrollPosition nextPosition = ScrollPosition.forward(Map.of("id", lastBook.getId()));
            books = repository.findAllBy(PageRequest.ofSize(20), nextPosition);
            totalFetchSize += books.size();
        }   
        long elapsedTime = System.currentTimeMillis() - start;

        assertThat(totalFetchSize).isEqualTo(1000);
        System.out.println("elapsed time (keyset forward): " + elapsedTime + "ms");
        System.out.println("total query execution count:" + stat.getQueryExecutionCount());
    }

    @Test
    void scrollKeysetForwardWithPublishedAt_window() {
        long start = System.currentTimeMillis();
        Sort.TypedSort<PBook> bookSort = Sort.sort(PBook.class);
        Sort sort = bookSort.by(PBook::getPublishedAt).ascending();
        Window<PBook> books = repository.findAllBy(PageRequest.ofSize(20).withSort(sort), ScrollPosition.forward(Map.of("id", 0, "publishedAt", LocalDate.of(2025, 01, 01))));
        int totalFetchSize = books.size();

        // Book 엔티티의 id와 publishedAt 필드를 기준으로 키 셋 페이지네이션
        while (books.hasNext()) {
            books = repository.findAllBy(PageRequest.ofSize(20), books.positionAt(books.getContent().getLast()));
            totalFetchSize += books.size();
        }
        long elapsedTime = System.currentTimeMillis() - start;

        /* 
         * 첫 쿼리
         * select b.id, b.name, b.published_at 
         * from pagination_book b 
         * where b.published_at > '2025-01-01T00:00:00.000+0900' 
         *     or b.published_at='2025-01-01T00:00:00.000+0900' 
         *     and b.id > 0 
         * order by b.published_at, b.id 
         * fetch first 21 rows only;
         * 
         * 마지막 쿼리
         * select b.id, b.name, b.published_at 
         * from pagination_book b 
         * where b.id > 982 
         * order by b.id 
         * fetch first 21 rows only;
         */

        System.out.println("total fetch size: " + totalFetchSize);
        System.out.println("elapsed time (keyset forward): " + elapsedTime + "ms");
        System.out.println("total query execution count: " + stat.getQueryExecutionCount());
    }

    @Test
    void scrollKeysetBackwardWithId_window() {
        long start = System.currentTimeMillis();
        Window<PBook> books = repository.findAllBy(PageRequest.ofSize(20), ScrollPosition.backward(Map.of("id", 1001L)));
        int totalFetchSize = books.size();   

        // Book 엔티티의 id 필드를 기준으로 키 셋 페이지네이션 (backward)
        while (books.hasNext()) {
            PBook firstBook = books.getContent().getFirst();
            ScrollPosition nextPosition = ScrollPosition.backward(Map.of("id", firstBook.getId()));
            books = repository.findAllBy(PageRequest.ofSize(20), nextPosition);
            totalFetchSize += books.size();
        }
        long elapsedTime = System.currentTimeMillis() - start;

        /*
         * 첫 쿼리
         * select b.id,b.name,b.published_at 
         * from pagination_book b 
         * where b.id < 1001 
         * order by b.id desc 
         * fetch first 21 rows only;
         * 
         * 마지막 쿼리
         * select b.id,b.name,b.published_at 
         * from pagination_book b 
         * where b.id < 21 
         * order by b.id desc 
         * fetch first 21 rows only;
         */

        System.out.println("total fetch size: " + totalFetchSize);
        System.out.println("elapsed time (keyset backward): " + elapsedTime + "ms");
        System.out.println("total query execution count: " + stat.getQueryExecutionCount());        
    }

    @Test
    void scrollSpecificKeyset_windowIterator() {
        long start = System.currentTimeMillis();
        Sort.TypedSort<PBook> bookSort = Sort.sort(PBook.class);
        Sort sort = bookSort.by(PBook::getPublishedAt).descending();

        WindowIterator<PBook> books = WindowIterator
                .of(position -> repository.findAllBy(PageRequest.ofSize(20).withSort(sort), position))
                .startingAt(ScrollPosition.forward(Map.of("id", 500L, "publishedAt", LocalDate.of(2025, 05, 31))));
        
        List<PBook> result = new ArrayList<>();
        books.forEachRemaining(result::add);
        long elapsedTime = System.currentTimeMillis() - start;

        System.out.println("total fetch size: " + result.size());
        System.out.println("elapsed time (keyset backward): " + elapsedTime + "ms");
    }
    
}

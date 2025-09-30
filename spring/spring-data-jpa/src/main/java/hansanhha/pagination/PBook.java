package hansanhha.pagination;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "paginationBook")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookstore_id")
    private PBookStore bookStore;

    public PBook(String name, LocalDate publishedAt) {
        this.name = name;
        this.publishedAt = publishedAt;
    }

    void setBookStore(PBookStore bookStore) {
        this.bookStore = bookStore;
    }
    
}

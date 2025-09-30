package hansanhha.pagination;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "paginationBookStore")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PBookStore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "bookStore", cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 100)
    private List<PBook> books = new ArrayList<>();

    public PBookStore(String name) {
        this.name = name;
    }

    public void addBook(PBook book) {
        books.add(book);
        book.setBookStore(this);
    }
}

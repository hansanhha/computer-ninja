package hansanhha.pagination;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPBook is a Querydsl query type for PBook
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPBook extends EntityPathBase<PBook> {

    private static final long serialVersionUID = 965496989L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPBook pBook = new QPBook("pBook");

    public final QPBookStore bookStore;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final DatePath<java.time.LocalDate> publishedAt = createDate("publishedAt", java.time.LocalDate.class);

    public QPBook(String variable) {
        this(PBook.class, forVariable(variable), INITS);
    }

    public QPBook(Path<? extends PBook> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPBook(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPBook(PathMetadata metadata, PathInits inits) {
        this(PBook.class, metadata, inits);
    }

    public QPBook(Class<? extends PBook> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bookStore = inits.isInitialized("bookStore") ? new QPBookStore(forProperty("bookStore")) : null;
    }

}


package hansanhha.pagination;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPBookStore is a Querydsl query type for PBookStore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPBookStore extends EntityPathBase<PBookStore> {

    private static final long serialVersionUID = 1918276164L;

    public static final QPBookStore pBookStore = new QPBookStore("pBookStore");

    public final ListPath<PBook, QPBook> books = this.<PBook, QPBook>createList("books", PBook.class, QPBook.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QPBookStore(String variable) {
        super(PBookStore.class, forVariable(variable));
    }

    public QPBookStore(Path<? extends PBookStore> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPBookStore(PathMetadata metadata) {
        super(PBookStore.class, metadata);
    }

}


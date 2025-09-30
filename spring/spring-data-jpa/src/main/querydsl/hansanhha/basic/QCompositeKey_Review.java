package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompositeKey_Review is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompositeKey_Review extends EntityPathBase<CompositeKey.Review> {

    private static final long serialVersionUID = -139490218L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompositeKey_Review review = new QCompositeKey_Review("review");

    public final QCompositeKey_ReviewId id;

    public QCompositeKey_Review(String variable) {
        this(CompositeKey.Review.class, forVariable(variable), INITS);
    }

    public QCompositeKey_Review(Path<? extends CompositeKey.Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompositeKey_Review(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompositeKey_Review(PathMetadata metadata, PathInits inits) {
        this(CompositeKey.Review.class, metadata, inits);
    }

    public QCompositeKey_Review(Class<? extends CompositeKey.Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCompositeKey_ReviewId(forProperty("id")) : null;
    }

}


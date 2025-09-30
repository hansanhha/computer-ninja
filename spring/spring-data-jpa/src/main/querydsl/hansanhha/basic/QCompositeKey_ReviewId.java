package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompositeKey_ReviewId is a Querydsl query type for ReviewId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCompositeKey_ReviewId extends BeanPath<CompositeKey.ReviewId> {

    private static final long serialVersionUID = -906110959L;

    public static final QCompositeKey_ReviewId reviewId = new QCompositeKey_ReviewId("reviewId");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QCompositeKey_ReviewId(String variable) {
        super(CompositeKey.ReviewId.class, forVariable(variable));
    }

    public QCompositeKey_ReviewId(Path<CompositeKey.ReviewId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompositeKey_ReviewId(PathMetadata metadata) {
        super(CompositeKey.ReviewId.class, metadata);
    }

}


package hansanhha.ddd.compare_to_jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderJPA is a Querydsl query type for OrderJPA
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderJPA extends EntityPathBase<OrderJPA> {

    private static final long serialVersionUID = 1365698248L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderJPA orderJPA = new QOrderJPA("orderJPA");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QAddressJPA shippingAddress;

    public QOrderJPA(String variable) {
        this(OrderJPA.class, forVariable(variable), INITS);
    }

    public QOrderJPA(Path<? extends OrderJPA> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderJPA(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderJPA(PathMetadata metadata, PathInits inits) {
        this(OrderJPA.class, metadata, inits);
    }

    public QOrderJPA(Class<? extends OrderJPA> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.shippingAddress = inits.isInitialized("shippingAddress") ? new QAddressJPA(forProperty("shippingAddress")) : null;
    }

}


package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompositeKey_OrderItem is a Querydsl query type for OrderItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompositeKey_OrderItem extends EntityPathBase<CompositeKey.OrderItem> {

    private static final long serialVersionUID = 1090421187L;

    public static final QCompositeKey_OrderItem orderItem = new QCompositeKey_OrderItem("orderItem");

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public QCompositeKey_OrderItem(String variable) {
        super(CompositeKey.OrderItem.class, forVariable(variable));
    }

    public QCompositeKey_OrderItem(Path<? extends CompositeKey.OrderItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompositeKey_OrderItem(PathMetadata metadata) {
        super(CompositeKey.OrderItem.class, metadata);
    }

}


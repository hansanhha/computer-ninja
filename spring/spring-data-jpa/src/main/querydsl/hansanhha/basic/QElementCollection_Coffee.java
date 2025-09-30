package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QElementCollection_Coffee is a Querydsl query type for Coffee
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QElementCollection_Coffee extends BeanPath<ElementCollection.Coffee> {

    private static final long serialVersionUID = 271948312L;

    public static final QElementCollection_Coffee coffee = new QElementCollection_Coffee("coffee");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final StringPath name = createString("name");

    public QElementCollection_Coffee(String variable) {
        super(ElementCollection.Coffee.class, forVariable(variable));
    }

    public QElementCollection_Coffee(Path<ElementCollection.Coffee> path) {
        super(path.getType(), path.getMetadata());
    }

    public QElementCollection_Coffee(PathMetadata metadata) {
        super(ElementCollection.Coffee.class, metadata);
    }

}


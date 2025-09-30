package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QElementCollection is a Querydsl query type for ElementCollection
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QElementCollection extends EntityPathBase<ElementCollection> {

    private static final long serialVersionUID = -431797950L;

    public static final QElementCollection elementCollection = new QElementCollection("elementCollection");

    public final ListPath<ElementCollection.Coffee, QElementCollection_Coffee> coffees = this.<ElementCollection.Coffee, QElementCollection_Coffee>createList("coffees", ElementCollection.Coffee.class, QElementCollection_Coffee.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QElementCollection(String variable) {
        super(ElementCollection.class, forVariable(variable));
    }

    public QElementCollection(Path<? extends ElementCollection> path) {
        super(path.getType(), path.getMetadata());
    }

    public QElementCollection(PathMetadata metadata) {
        super(ElementCollection.class, metadata);
    }

}


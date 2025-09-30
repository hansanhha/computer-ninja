package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmbedded is a Querydsl query type for Embedded
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmbedded extends EntityPathBase<Embedded> {

    private static final long serialVersionUID = 27621122L;

    public static final QEmbedded embedded = new QEmbedded("embedded");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QEmbedded(String variable) {
        super(Embedded.class, forVariable(variable));
    }

    public QEmbedded(Path<? extends Embedded> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmbedded(PathMetadata metadata) {
        super(Embedded.class, metadata);
    }

}


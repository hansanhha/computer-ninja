package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEnumerated is a Querydsl query type for Enumerated
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEnumerated extends EntityPathBase<Enumerated> {

    private static final long serialVersionUID = 566818040L;

    public static final QEnumerated enumerated = new QEnumerated("enumerated");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<Enumerated.Role> role = createEnum("role", Enumerated.Role.class);

    public QEnumerated(String variable) {
        super(Enumerated.class, forVariable(variable));
    }

    public QEnumerated(Path<? extends Enumerated> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEnumerated(PathMetadata metadata) {
        super(Enumerated.class, metadata);
    }

}


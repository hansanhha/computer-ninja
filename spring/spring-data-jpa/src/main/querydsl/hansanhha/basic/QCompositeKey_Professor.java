package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompositeKey_Professor is a Querydsl query type for Professor
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompositeKey_Professor extends EntityPathBase<CompositeKey.Professor> {

    private static final long serialVersionUID = 473446577L;

    public static final QCompositeKey_Professor professor = new QCompositeKey_Professor("professor");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCompositeKey_Professor(String variable) {
        super(CompositeKey.Professor.class, forVariable(variable));
    }

    public QCompositeKey_Professor(Path<? extends CompositeKey.Professor> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompositeKey_Professor(PathMetadata metadata) {
        super(CompositeKey.Professor.class, metadata);
    }

}


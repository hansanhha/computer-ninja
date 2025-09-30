package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompositeKey_CourseId is a Querydsl query type for CourseId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QCompositeKey_CourseId extends BeanPath<CompositeKey.CourseId> {

    private static final long serialVersionUID = -1013277676L;

    public static final QCompositeKey_CourseId courseId = new QCompositeKey_CourseId("courseId");

    public final NumberPath<Long> professorId = createNumber("professorId", Long.class);

    public final NumberPath<Long> studentId = createNumber("studentId", Long.class);

    public QCompositeKey_CourseId(String variable) {
        super(CompositeKey.CourseId.class, forVariable(variable));
    }

    public QCompositeKey_CourseId(Path<CompositeKey.CourseId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompositeKey_CourseId(PathMetadata metadata) {
        super(CompositeKey.CourseId.class, metadata);
    }

}


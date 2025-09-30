package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompositeKey_Course is a Querydsl query type for Course
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompositeKey_Course extends EntityPathBase<CompositeKey.Course> {

    private static final long serialVersionUID = -559712999L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompositeKey_Course course = new QCompositeKey_Course("course");

    public final QCompositeKey_CourseId id;

    public final QCompositeKey_Professor professor;

    public final QCompositeKey_Student student;

    public QCompositeKey_Course(String variable) {
        this(CompositeKey.Course.class, forVariable(variable), INITS);
    }

    public QCompositeKey_Course(Path<? extends CompositeKey.Course> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompositeKey_Course(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompositeKey_Course(PathMetadata metadata, PathInits inits) {
        this(CompositeKey.Course.class, metadata, inits);
    }

    public QCompositeKey_Course(Class<? extends CompositeKey.Course> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QCompositeKey_CourseId(forProperty("id")) : null;
        this.professor = inits.isInitialized("professor") ? new QCompositeKey_Professor(forProperty("professor")) : null;
        this.student = inits.isInitialized("student") ? new QCompositeKey_Student(forProperty("student")) : null;
    }

}


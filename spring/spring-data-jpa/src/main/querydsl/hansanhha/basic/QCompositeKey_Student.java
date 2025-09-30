package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompositeKey_Student is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompositeKey_Student extends EntityPathBase<CompositeKey.Student> {

    private static final long serialVersionUID = 1286638845L;

    public static final QCompositeKey_Student student = new QCompositeKey_Student("student");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QCompositeKey_Student(String variable) {
        super(CompositeKey.Student.class, forVariable(variable));
    }

    public QCompositeKey_Student(Path<? extends CompositeKey.Student> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompositeKey_Student(PathMetadata metadata) {
        super(CompositeKey.Student.class, metadata);
    }

}


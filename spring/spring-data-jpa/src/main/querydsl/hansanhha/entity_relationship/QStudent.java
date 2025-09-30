package hansanhha.entity_relationship;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudent is a Querydsl query type for Student
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudent extends EntityPathBase<Student> {

    private static final long serialVersionUID = 51964841L;

    public static final QStudent student = new QStudent("student");

    public final ListPath<Course, QCourse> courses = this.<Course, QCourse>createList("courses", Course.class, QCourse.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QStudent(String variable) {
        super(Student.class, forVariable(variable));
    }

    public QStudent(Path<? extends Student> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudent(PathMetadata metadata) {
        super(Student.class, metadata);
    }

}


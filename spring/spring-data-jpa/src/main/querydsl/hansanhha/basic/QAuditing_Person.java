package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditing_Person is a Querydsl query type for Person
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAuditing_Person extends EntityPathBase<Auditing.Person> {

    private static final long serialVersionUID = 2064860580L;

    public static final QAuditing_Person person = new QAuditing_Person("person");

    public final QAuditing_BaseEntity _super = new QAuditing_BaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public QAuditing_Person(String variable) {
        super(Auditing.Person.class, forVariable(variable));
    }

    public QAuditing_Person(Path<? extends Auditing.Person> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditing_Person(PathMetadata metadata) {
        super(Auditing.Person.class, metadata);
    }

}


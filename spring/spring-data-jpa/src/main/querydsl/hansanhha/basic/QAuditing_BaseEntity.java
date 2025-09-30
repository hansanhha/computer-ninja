package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditing_BaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditing_BaseEntity extends EntityPathBase<Auditing.BaseEntity> {

    private static final long serialVersionUID = 497983715L;

    public static final QAuditing_BaseEntity baseEntity = new QAuditing_BaseEntity("baseEntity");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = createDateTime("lastModifiedDate", java.time.LocalDateTime.class);

    public QAuditing_BaseEntity(String variable) {
        super(Auditing.BaseEntity.class, forVariable(variable));
    }

    public QAuditing_BaseEntity(Path<? extends Auditing.BaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditing_BaseEntity(PathMetadata metadata) {
        super(Auditing.BaseEntity.class, metadata);
    }

}


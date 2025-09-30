package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMappedSuperclass_BaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QMappedSuperclass_BaseEntity extends EntityPathBase<MappedSuperclass.BaseEntity> {

    private static final long serialVersionUID = -708762118L;

    public static final QMappedSuperclass_BaseEntity baseEntity = new QMappedSuperclass_BaseEntity("baseEntity");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath name = createString("name");

    public QMappedSuperclass_BaseEntity(String variable) {
        super(MappedSuperclass.BaseEntity.class, forVariable(variable));
    }

    public QMappedSuperclass_BaseEntity(Path<? extends MappedSuperclass.BaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMappedSuperclass_BaseEntity(PathMetadata metadata) {
        super(MappedSuperclass.BaseEntity.class, metadata);
    }

}


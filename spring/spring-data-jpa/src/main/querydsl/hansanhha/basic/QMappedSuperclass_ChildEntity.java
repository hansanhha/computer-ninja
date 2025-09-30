package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMappedSuperclass_ChildEntity is a Querydsl query type for ChildEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMappedSuperclass_ChildEntity extends EntityPathBase<MappedSuperclass.ChildEntity> {

    private static final long serialVersionUID = 1398869241L;

    public static final QMappedSuperclass_ChildEntity childEntity = new QMappedSuperclass_ChildEntity("childEntity");

    public final QMappedSuperclass_BaseEntity _super = new QMappedSuperclass_BaseEntity(this);

    //inherited
    public final NumberPath<Integer> age = _super.age;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath name = _super.name;

    public QMappedSuperclass_ChildEntity(String variable) {
        super(MappedSuperclass.ChildEntity.class, forVariable(variable));
    }

    public QMappedSuperclass_ChildEntity(Path<? extends MappedSuperclass.ChildEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMappedSuperclass_ChildEntity(PathMetadata metadata) {
        super(MappedSuperclass.ChildEntity.class, metadata);
    }

}


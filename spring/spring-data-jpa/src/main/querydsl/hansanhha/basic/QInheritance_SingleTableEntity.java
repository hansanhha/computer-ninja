package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInheritance_SingleTableEntity is a Querydsl query type for SingleTableEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInheritance_SingleTableEntity extends EntityPathBase<Inheritance.SingleTableEntity> {

    private static final long serialVersionUID = -1750163923L;

    public static final QInheritance_SingleTableEntity singleTableEntity = new QInheritance_SingleTableEntity("singleTableEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QInheritance_SingleTableEntity(String variable) {
        super(Inheritance.SingleTableEntity.class, forVariable(variable));
    }

    public QInheritance_SingleTableEntity(Path<? extends Inheritance.SingleTableEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInheritance_SingleTableEntity(PathMetadata metadata) {
        super(Inheritance.SingleTableEntity.class, metadata);
    }

}


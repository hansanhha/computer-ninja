package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInheritance_SingleTableChildEntity is a Querydsl query type for SingleTableChildEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInheritance_SingleTableChildEntity extends EntityPathBase<Inheritance.SingleTableChildEntity> {

    private static final long serialVersionUID = 1138992149L;

    public static final QInheritance_SingleTableChildEntity singleTableChildEntity = new QInheritance_SingleTableChildEntity("singleTableChildEntity");

    public final QInheritance_SingleTableEntity _super = new QInheritance_SingleTableEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final StringPath name = _super.name;

    public QInheritance_SingleTableChildEntity(String variable) {
        super(Inheritance.SingleTableChildEntity.class, forVariable(variable));
    }

    public QInheritance_SingleTableChildEntity(Path<? extends Inheritance.SingleTableChildEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInheritance_SingleTableChildEntity(PathMetadata metadata) {
        super(Inheritance.SingleTableChildEntity.class, metadata);
    }

}


package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInheritance_JoinedTableEntity is a Querydsl query type for JoinedTableEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInheritance_JoinedTableEntity extends EntityPathBase<Inheritance.JoinedTableEntity> {

    private static final long serialVersionUID = -375283572L;

    public static final QInheritance_JoinedTableEntity joinedTableEntity = new QInheritance_JoinedTableEntity("joinedTableEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QInheritance_JoinedTableEntity(String variable) {
        super(Inheritance.JoinedTableEntity.class, forVariable(variable));
    }

    public QInheritance_JoinedTableEntity(Path<? extends Inheritance.JoinedTableEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInheritance_JoinedTableEntity(PathMetadata metadata) {
        super(Inheritance.JoinedTableEntity.class, metadata);
    }

}


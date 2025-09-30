package hansanhha.basic;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QInheritance_TablePerClassEntity is a Querydsl query type for TablePerClassEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInheritance_TablePerClassEntity extends EntityPathBase<Inheritance.TablePerClassEntity> {

    private static final long serialVersionUID = 386946768L;

    public static final QInheritance_TablePerClassEntity tablePerClassEntity = new QInheritance_TablePerClassEntity("tablePerClassEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QInheritance_TablePerClassEntity(String variable) {
        super(Inheritance.TablePerClassEntity.class, forVariable(variable));
    }

    public QInheritance_TablePerClassEntity(Path<? extends Inheritance.TablePerClassEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInheritance_TablePerClassEntity(PathMetadata metadata) {
        super(Inheritance.TablePerClassEntity.class, metadata);
    }

}


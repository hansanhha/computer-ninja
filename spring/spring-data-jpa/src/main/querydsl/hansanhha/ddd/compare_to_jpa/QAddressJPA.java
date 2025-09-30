package hansanhha.ddd.compare_to_jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddressJPA is a Querydsl query type for AddressJPA
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAddressJPA extends BeanPath<AddressJPA> {

    private static final long serialVersionUID = -1218843230L;

    public static final QAddressJPA addressJPA = new QAddressJPA("addressJPA");

    public final StringPath city = createString("city");

    public final StringPath street = createString("street");

    public QAddressJPA(String variable) {
        super(AddressJPA.class, forVariable(variable));
    }

    public QAddressJPA(Path<? extends AddressJPA> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddressJPA(PathMetadata metadata) {
        super(AddressJPA.class, metadata);
    }

}


package hansanhha.transaction.propagation;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTxLog is a Querydsl query type for TxLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTxLog extends EntityPathBase<TxLog> {

    private static final long serialVersionUID = 917745334L;

    public static final QTxLog txLog = new QTxLog("txLog");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath message = createString("message");

    public QTxLog(String variable) {
        super(TxLog.class, forVariable(variable));
    }

    public QTxLog(Path<? extends TxLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTxLog(PathMetadata metadata) {
        super(TxLog.class, metadata);
    }

}


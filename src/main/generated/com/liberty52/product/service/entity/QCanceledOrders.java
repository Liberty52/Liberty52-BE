package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCanceledOrders is a Querydsl query type for CanceledOrders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCanceledOrders extends EntityPathBase<CanceledOrders> {

    private static final long serialVersionUID = 1353241386L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCanceledOrders canceledOrders = new QCanceledOrders("canceledOrders");

    public final StringPath approvedAdminName = createString("approvedAdminName");

    public final DateTimePath<java.time.LocalDateTime> canceledAt = createDateTime("canceledAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final StringPath id = createString("id");

    public final QOrders orders;

    public final StringPath reason = createString("reason");

    public final DateTimePath<java.time.LocalDateTime> reqAt = createDateTime("reqAt", java.time.LocalDateTime.class);

    public QCanceledOrders(String variable) {
        this(CanceledOrders.class, forVariable(variable), INITS);
    }

    public QCanceledOrders(Path<? extends CanceledOrders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCanceledOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCanceledOrders(PathMetadata metadata, PathInits inits) {
        this(CanceledOrders.class, metadata, inits);
    }

    public QCanceledOrders(Class<? extends CanceledOrders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}


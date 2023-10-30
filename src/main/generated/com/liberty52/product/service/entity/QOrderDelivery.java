package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderDelivery is a Querydsl query type for OrderDelivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDelivery extends EntityPathBase<OrderDelivery> {

    private static final long serialVersionUID = 1478248854L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderDelivery orderDelivery = new QOrderDelivery("orderDelivery");

    public final StringPath courierCompanyCode = createString("courierCompanyCode");

    public final StringPath courierCompanyName = createString("courierCompanyName");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath id = createString("id");

    public final QOrders order;

    public final StringPath trackingNumber = createString("trackingNumber");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QOrderDelivery(String variable) {
        this(OrderDelivery.class, forVariable(variable), INITS);
    }

    public QOrderDelivery(Path<? extends OrderDelivery> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderDelivery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderDelivery(PathMetadata metadata, PathInits inits) {
        this(OrderDelivery.class, metadata, inits);
    }

    public QOrderDelivery(Class<? extends OrderDelivery> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrders(forProperty("order"), inits.get("order")) : null;
    }

}


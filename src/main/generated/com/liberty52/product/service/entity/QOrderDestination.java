package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderDestination is a Querydsl query type for OrderDestination
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderDestination extends EntityPathBase<OrderDestination> {

    private static final long serialVersionUID = 1729993516L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderDestination orderDestination = new QOrderDestination("orderDestination");

    public final StringPath address1 = createString("address1");

    public final StringPath address2 = createString("address2");

    public final StringPath id = createString("id");

    public final QOrders orders;

    public final StringPath receiverEmail = createString("receiverEmail");

    public final StringPath receiverName = createString("receiverName");

    public final StringPath receiverPhoneNumber = createString("receiverPhoneNumber");

    public final StringPath zipCode = createString("zipCode");

    public QOrderDestination(String variable) {
        this(OrderDestination.class, forVariable(variable), INITS);
    }

    public QOrderDestination(Path<? extends OrderDestination> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderDestination(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderDestination(PathMetadata metadata, PathInits inits) {
        this(OrderDestination.class, metadata, inits);
    }

    public QOrderDestination(Class<? extends OrderDestination> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}


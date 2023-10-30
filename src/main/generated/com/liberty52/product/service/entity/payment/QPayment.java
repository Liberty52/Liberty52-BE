package com.liberty52.product.service.entity.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayment is a Querydsl query type for Payment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayment extends EntityPathBase<Payment<? extends Payment.PaymentInfo>> {

    private static final long serialVersionUID = -457563822L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayment payment = new QPayment("payment");

    public final StringPath id = createString("id");

    public final StringPath info = createString("info");

    public final com.liberty52.product.service.entity.QOrders orders;

    public final DateTimePath<java.time.LocalDateTime> reqAt = createDateTime("reqAt", java.time.LocalDateTime.class);

    public final EnumPath<PaymentStatus> status = createEnum("status", PaymentStatus.class);

    public final EnumPath<PaymentType> type = createEnum("type", PaymentType.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QPayment(String variable) {
        this((Class) Payment.class, forVariable(variable), INITS);
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QPayment(Path<? extends Payment> path) {
        this((Class) path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QPayment(PathMetadata metadata, PathInits inits) {
        this((Class) Payment.class, metadata, inits);
    }

    public QPayment(Class<? extends Payment<? extends Payment.PaymentInfo>> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new com.liberty52.product.service.entity.QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}


package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 1810236721L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final StringPath authId = createString("authId");

    public final QCanceledOrders canceledOrders;

    public final ListPath<CustomProduct, QCustomProduct> customProducts = this.<CustomProduct, QCustomProduct>createList("customProducts", CustomProduct.class, QCustomProduct.class, PathInits.DIRECT2);

    public final NumberPath<Integer> deliveryPrice = createNumber("deliveryPrice", Integer.class);

    public final StringPath id = createString("id");

    public final QOrderDelivery orderDelivery;

    public final QOrderDestination orderDestination;

    public final DateTimePath<java.time.LocalDateTime> orderedAt = createDateTime("orderedAt", java.time.LocalDateTime.class);

    public final StringPath orderNum = createString("orderNum");

    public final EnumPath<OrderStatus> orderStatus = createEnum("orderStatus", OrderStatus.class);

    public final com.liberty52.product.service.entity.payment.QPayment payment;

    public final NumberPath<Integer> totalQuantity = createNumber("totalQuantity", Integer.class);

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.canceledOrders = inits.isInitialized("canceledOrders") ? new QCanceledOrders(forProperty("canceledOrders"), inits.get("canceledOrders")) : null;
        this.orderDelivery = inits.isInitialized("orderDelivery") ? new QOrderDelivery(forProperty("orderDelivery"), inits.get("orderDelivery")) : null;
        this.orderDestination = inits.isInitialized("orderDestination") ? new QOrderDestination(forProperty("orderDestination"), inits.get("orderDestination")) : null;
        this.payment = inits.isInitialized("payment") ? new com.liberty52.product.service.entity.payment.QPayment(forProperty("payment"), inits.get("payment")) : null;
    }

}


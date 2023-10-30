package com.liberty52.product.service.entity.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardPayment is a Querydsl query type for CardPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardPayment extends EntityPathBase<CardPayment> {

    private static final long serialVersionUID = 474359330L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardPayment cardPayment = new QCardPayment("cardPayment");

    public final QPayment _super;

    //inherited
    public final StringPath id;

    //inherited
    public final StringPath info;

    public final SimplePath<CardPayment.CardPaymentInfo> infoAsDto = createSimple("infoAsDto", CardPayment.CardPaymentInfo.class);

    public final StringPath infoAsString = createString("infoAsString");

    // inherited
    public final com.liberty52.product.service.entity.QOrders orders;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> reqAt;

    //inherited
    public final EnumPath<PaymentStatus> status;

    //inherited
    public final EnumPath<PaymentType> type;

    public QCardPayment(String variable) {
        this(CardPayment.class, forVariable(variable), INITS);
    }

    public QCardPayment(Path<? extends CardPayment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardPayment(PathMetadata metadata, PathInits inits) {
        this(CardPayment.class, metadata, inits);
    }

    public QCardPayment(Class<? extends CardPayment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QPayment(type, metadata, inits);
        this.id = _super.id;
        this.info = _super.info;
        this.orders = _super.orders;
        this.reqAt = _super.reqAt;
        this.status = _super.status;
        this.type = _super.type;
    }

}


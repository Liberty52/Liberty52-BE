package com.liberty52.product.service.entity.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVBankPayment is a Querydsl query type for VBankPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVBankPayment extends EntityPathBase<VBankPayment> {

    private static final long serialVersionUID = -772396536L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVBankPayment vBankPayment = new QVBankPayment("vBankPayment");

    public final QPayment _super;

    //inherited
    public final StringPath id;

    //inherited
    public final StringPath info;

    public final SimplePath<VBankPayment.VBankPaymentInfo> infoAsDto = createSimple("infoAsDto", VBankPayment.VBankPaymentInfo.class);

    public final StringPath infoAsString = createString("infoAsString");

    // inherited
    public final com.liberty52.product.service.entity.QOrders orders;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> reqAt;

    //inherited
    public final EnumPath<PaymentStatus> status;

    //inherited
    public final EnumPath<PaymentType> type;

    public QVBankPayment(String variable) {
        this(VBankPayment.class, forVariable(variable), INITS);
    }

    public QVBankPayment(Path<? extends VBankPayment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVBankPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVBankPayment(PathMetadata metadata, PathInits inits) {
        this(VBankPayment.class, metadata, inits);
    }

    public QVBankPayment(Class<? extends VBankPayment> type, PathMetadata metadata, PathInits inits) {
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


package com.liberty52.product.service.entity.payment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVBank is a Querydsl query type for VBank
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVBank extends EntityPathBase<VBank> {

    private static final long serialVersionUID = -1622695394L;

    public static final QVBank vBank = new QVBank("vBank");

    public final StringPath account = createString("account");

    public final EnumPath<BankType> bank = createEnum("bank", BankType.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath holder = createString("holder");

    public final StringPath id = createString("id");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QVBank(String variable) {
        super(VBank.class, forVariable(variable));
    }

    public QVBank(Path<? extends VBank> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVBank(PathMetadata metadata) {
        super(VBank.class, metadata);
    }

}


package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryOption is a Querydsl query type for DeliveryOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryOption extends EntityPathBase<DeliveryOption> {

    private static final long serialVersionUID = 583688149L;

    public static final QDeliveryOption deliveryOption = new QDeliveryOption("deliveryOption");

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> feeUpdatedAt = createDateTime("feeUpdatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QDeliveryOption(String variable) {
        super(DeliveryOption.class, forVariable(variable));
    }

    public QDeliveryOption(Path<? extends DeliveryOption> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryOption(PathMetadata metadata) {
        super(DeliveryOption.class, metadata);
    }

}


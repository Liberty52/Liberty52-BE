package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductDeliveryOption is a Querydsl query type for ProductDeliveryOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDeliveryOption extends EntityPathBase<ProductDeliveryOption> {

    private static final long serialVersionUID = -1833201524L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductDeliveryOption productDeliveryOption = new QProductDeliveryOption("productDeliveryOption");

    public final StringPath courierName = createString("courierName");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> fee = createNumber("fee", Integer.class);

    public final StringPath id = createString("id");

    public final QProduct product;

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public QProductDeliveryOption(String variable) {
        this(ProductDeliveryOption.class, forVariable(variable), INITS);
    }

    public QProductDeliveryOption(Path<? extends ProductDeliveryOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductDeliveryOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductDeliveryOption(PathMetadata metadata, PathInits inits) {
        this(ProductDeliveryOption.class, metadata, inits);
    }

    public QProductDeliveryOption(Class<? extends ProductDeliveryOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}


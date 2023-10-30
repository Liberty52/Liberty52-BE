package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomProduct is a Querydsl query type for CustomProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomProduct extends EntityPathBase<CustomProduct> {

    private static final long serialVersionUID = -458573710L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomProduct customProduct = new QCustomProduct("customProduct");

    public final StringPath authId = createString("authId");

    public final QCart cart;

    public final StringPath id = createString("id");

    public final StringPath modelingPictureUrl = createString("modelingPictureUrl");

    public final ListPath<CustomProductOption, QCustomProductOption> options = this.<CustomProductOption, QCustomProductOption>createList("options", CustomProductOption.class, QCustomProductOption.class, PathInits.DIRECT2);

    public final QOrders orders;

    public final QProduct product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final QReview review;

    public final StringPath thumbnailPictureUrl = createString("thumbnailPictureUrl");

    public final StringPath userCustomPictureUrl = createString("userCustomPictureUrl");

    public QCustomProduct(String variable) {
        this(CustomProduct.class, forVariable(variable), INITS);
    }

    public QCustomProduct(Path<? extends CustomProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomProduct(PathMetadata metadata, PathInits inits) {
        this(CustomProduct.class, metadata, inits);
    }

    public QCustomProduct(Class<? extends CustomProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new QCart(forProperty("cart")) : null;
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review"), inits.get("review")) : null;
    }

}


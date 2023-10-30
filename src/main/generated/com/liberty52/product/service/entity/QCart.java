package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = -2049884052L;

    public static final QCart cart = new QCart("cart");

    public final StringPath authId = createString("authId");

    public final ListPath<CustomProduct, QCustomProduct> customProducts = this.<CustomProduct, QCustomProduct>createList("customProducts", CustomProduct.class, QCustomProduct.class, PathInits.DIRECT2);

    public final DatePath<java.time.LocalDate> expiryDate = createDate("expiryDate", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public QCart(String variable) {
        super(Cart.class, forVariable(variable));
    }

    public QCart(Path<? extends Cart> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCart(PathMetadata metadata) {
        super(Cart.class, metadata);
    }

}


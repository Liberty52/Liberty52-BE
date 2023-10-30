package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptionDetail is a Querydsl query type for OptionDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptionDetail extends EntityPathBase<OptionDetail> {

    private static final long serialVersionUID = -1406444942L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptionDetail optionDetail = new QOptionDetail("optionDetail");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final BooleanPath onSale = createBoolean("onSale");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final QProductOption productOption;

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QOptionDetail(String variable) {
        this(OptionDetail.class, forVariable(variable), INITS);
    }

    public QOptionDetail(Path<? extends OptionDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptionDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptionDetail(PathMetadata metadata, PathInits inits) {
        this(OptionDetail.class, metadata, inits);
    }

    public QOptionDetail(Class<? extends OptionDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productOption = inits.isInitialized("productOption") ? new QProductOption(forProperty("productOption"), inits.get("productOption")) : null;
    }

}


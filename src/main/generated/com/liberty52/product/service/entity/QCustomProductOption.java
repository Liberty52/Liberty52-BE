package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomProductOption is a Querydsl query type for CustomProductOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomProductOption extends EntityPathBase<CustomProductOption> {

    private static final long serialVersionUID = -747204729L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomProductOption customProductOption = new QCustomProductOption("customProductOption");

    public final QCustomProduct customProduct;

    public final StringPath detailId = createString("detailId");

    public final StringPath detailName = createString("detailName");

    public final StringPath id = createString("id");

    public final QOptionDetail optionDetail;

    public final StringPath optionId = createString("optionId");

    public final StringPath optionName = createString("optionName");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QCustomProductOption(String variable) {
        this(CustomProductOption.class, forVariable(variable), INITS);
    }

    public QCustomProductOption(Path<? extends CustomProductOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomProductOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomProductOption(PathMetadata metadata, PathInits inits) {
        this(CustomProductOption.class, metadata, inits);
    }

    public QCustomProductOption(Class<? extends CustomProductOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.customProduct = inits.isInitialized("customProduct") ? new QCustomProduct(forProperty("customProduct"), inits.get("customProduct")) : null;
        this.optionDetail = inits.isInitialized("optionDetail") ? new QOptionDetail(forProperty("optionDetail"), inits.get("optionDetail")) : null;
    }

}


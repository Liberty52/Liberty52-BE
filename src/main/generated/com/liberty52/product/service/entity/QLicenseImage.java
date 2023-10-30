package com.liberty52.product.service.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLicenseImage is a Querydsl query type for LicenseImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLicenseImage extends EntityPathBase<LicenseImage> {

    private static final long serialVersionUID = -800887066L;

    public static final QLicenseImage licenseImage = new QLicenseImage("licenseImage");

    public final StringPath artistName = createString("artistName");

    public final StringPath artName = createString("artName");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final StringPath id = createString("id");

    public final StringPath licenseImageUrl = createString("licenseImageUrl");

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final NumberPath<Integer> stock = createNumber("stock", Integer.class);

    public QLicenseImage(String variable) {
        super(LicenseImage.class, forVariable(variable));
    }

    public QLicenseImage(Path<? extends LicenseImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLicenseImage(PathMetadata metadata) {
        super(LicenseImage.class, metadata);
    }

}


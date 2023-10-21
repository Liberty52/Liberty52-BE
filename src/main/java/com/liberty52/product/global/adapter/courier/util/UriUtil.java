package com.liberty52.product.global.adapter.courier.util;

import org.springframework.web.util.UriBuilder;

import java.net.URI;

public class UriUtil {

    public static URI uriWithApiKey(
            UriBuilder uriBuilder,
            String path,
            String apiKeyHeader,
            String apiKeyValue
    ) {
        return uriBuilderWithApiKey(uriBuilder, path, apiKeyHeader, apiKeyValue).build();
    }

    public static UriBuilder uriBuilderWithApiKey(
            UriBuilder uriBuilder,
            String path,
            String apiKeyHeader,
            String apiKeyValue
    ) {
        return uriBuilder.path(path).queryParam(apiKeyHeader, apiKeyValue);
    }
}

package com.liberty52.smartcourier.util;

import org.springframework.web.util.UriBuilder;

import java.net.URI;

public class UriUtil {

    public static URI uriWithApiKey(UriBuilder uriBuilder, String path, String apiKey) {
        return uriBuilderWithApiKey(uriBuilder, path, apiKey).build();
    }

    public static UriBuilder uriBuilderWithApiKey(UriBuilder uriBuilder, String path, String apiKey) {
        return uriBuilder.path(path).queryParam("t_key", apiKey);
    }
}

package com.liberty52.main.global.adapter.dalle.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liberty52.main.global.exception.internal.DallEApiClientExceptions;
import lombok.Getter;

import java.util.List;

public class DallEDto {
    @Getter
    public static final class Request {
        private final String prompt;
        private final int n;
        private final String size;
        @JsonProperty("response_format")
        private final String responseFormat;
        private final String user;

        public Request(String prompt, int n, Request.Size size, Request.Format responseFormat, String user) {
            this.prompt = prompt;
            this.n = n;
            this.size = size.get();
            this.responseFormat = responseFormat.getValue();
            this.user = user;
            validValues();
        }

        private void validValues() {
            if (prompt.length() < 1 || prompt.length() > 1000)
                throw new DallEApiClientExceptions.InvalidPrompt();
            if (n < 1 || n > 10)
                throw new DallEApiClientExceptions.InvalidN();
            if (!Request.Format.contains(responseFormat))
                throw new DallEApiClientExceptions.InvalidResponseFormat();
            if (user != null && user.isBlank())
                throw new DallEApiClientExceptions.InvalidUser();
        }

        @Getter
        public enum Format {
            URL("url"),
            B64_JSON("b64_json"),
            ;

            final String value;

            Format(String value) {
                this.value = value;
            }

            public static boolean contains(String responseFormat) {
                for (Request.Format e : values()) {
                    if (e.value.equals(responseFormat)) return true;
                }
                return false;
            }
        }

        public enum Size {
            S256(256),
            S512(512),
            S1024(1024);

            final int width;
            final int height;

            Size(int width) {
                this.width = this.height = width;
            }

            public String get() {
                return width + "x" + height;
            }
        }
    }

    @Getter
    public abstract static class Response<T extends Response.Data> {
        private Long created;
        private List<T> data;

        public static class URL extends Response<Response.Data.URL> {
        }

        public static class B64Json extends Response<Response.Data.B64Json> {
        }

        public abstract static class Data {
            @Getter
            public static class URL extends Response.Data {
                private String url;
            }

            @Getter
            public static class B64Json extends Response.Data {
                @JsonProperty("b64_json")
                private String b64Json;
            }
        }
    }
}

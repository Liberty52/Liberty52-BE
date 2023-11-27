package com.liberty52.main.global.adapter;

import java.net.URL;

public interface StoragePresigner {
    URL generatePresignedUrl(String key, BlobContentType contentType);

    enum BlobContentType {
        TEXT_PAIN, TEXT_RTF,
        IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF, IMAGE_BMP, IMAGE_TIFF,
        APPLICATION_MSWORD, APPLICATION_ZIP, APPLICATION_PDF, APPLICATION_X_ZIP, APPLICATION_X_COMPRESSED,
        AUDIO_MPEG;

        public String toExtString() {
            String ext = switch (this) {
                case TEXT_PAIN -> "txt";
                case TEXT_RTF -> "rtf";
                case IMAGE_JPEG -> "jpeg";
                case IMAGE_PNG -> "png";
                case IMAGE_GIF -> "gif";
                case IMAGE_BMP -> "bmp";
                case IMAGE_TIFF -> "tiff";
                case APPLICATION_MSWORD -> "docx";
                case APPLICATION_ZIP -> "zip";
                case APPLICATION_PDF -> "pdf";
                case APPLICATION_X_ZIP -> "zip";
                case AUDIO_MPEG -> "mpeg";
                default -> "";
            };
            if (!ext.isBlank()) {
                return "." + ext;
            }
            return "";
        }

        public String toValueString() {
            return switch (this) {
                case TEXT_PAIN -> "text/plain";
                case TEXT_RTF -> "text/rtf";
                case IMAGE_JPEG -> "image/jpeg";
                case IMAGE_PNG -> "image/png";
                case IMAGE_GIF -> "image/gif";
                case IMAGE_BMP -> "image/bmp";
                case IMAGE_TIFF -> "image/tiff";
                case APPLICATION_MSWORD -> "application/msword";
                case APPLICATION_ZIP -> "application/zip";
                case APPLICATION_PDF -> "application/pdf";
                case APPLICATION_X_ZIP -> "application/x-zip";
                case APPLICATION_X_COMPRESSED -> "application/x-compressed";
                case AUDIO_MPEG -> "audio/mpeg";
            };
        }
    }
}

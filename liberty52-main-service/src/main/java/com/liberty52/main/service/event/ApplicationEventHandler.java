package com.liberty52.main.service.event;

import com.liberty52.main.global.adapter.s3.S3Uploader;
import com.liberty52.main.service.event.internal.ImageRemovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationEventHandler {
    private final S3Uploader s3Uploader;


    @EventListener
    public void deleteImageInS3OnCustomProductRemoved(ImageRemovedEvent event) {
        String imageUrl = event.getBody().url();
        try {
            s3Uploader.delete(imageUrl);
            log.info("An Image is deleted from s3. The url was {}", imageUrl);
        } catch (Exception e) {
            log.error("Unexpected Error in {}. Trying to delete image in s3. The url was {}", this.getClass().getName(), imageUrl);
            e.printStackTrace();
        }
    }
}

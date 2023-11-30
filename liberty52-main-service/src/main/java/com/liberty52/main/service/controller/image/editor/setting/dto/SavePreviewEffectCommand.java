package com.liberty52.main.service.controller.image.editor.setting.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavePreviewEffectCommand {
    private String name;
    private Integer opacity;
    private String src;
}

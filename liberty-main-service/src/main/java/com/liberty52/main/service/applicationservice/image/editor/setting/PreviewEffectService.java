package com.liberty52.main.service.applicationservice.image.editor.setting;

import com.liberty52.main.service.controller.image.editor.setting.dto.PreviewEffectResponse;
import com.liberty52.main.service.controller.image.editor.setting.dto.UpdatePreviewEffectCommand;

import java.util.List;

public interface PreviewEffectService {
    List<PreviewEffectResponse> getAll();

    void delete(String id);

    void put(UpdatePreviewEffectCommand cmd);
}

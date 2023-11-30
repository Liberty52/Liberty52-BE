package com.liberty52.main.service.controller.image.editor.setting;

import com.liberty52.main.global.util.Validator;
import com.liberty52.main.service.applicationservice.image.editor.setting.PreviewEffectService;
import com.liberty52.main.service.controller.image.editor.setting.dto.PreviewEffectResponse;
import com.liberty52.main.service.controller.image.editor.setting.dto.UpdatePreviewEffectCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PreviewEffectController {
    private final PreviewEffectService previewEffectService;

    @GetMapping("/image-editor/preview-effects")
    public List<PreviewEffectResponse> getAll() {
        return previewEffectService.getAll();
    }

    @DeleteMapping("/admin/image-editor/preview-effects/{id}")
    public void delete(
        @RequestHeader("LB-Role") String role,
        @PathVariable String id
    ) {
        Validator.isAdmin(role);
        previewEffectService.delete(id);
    }

    @PutMapping("/admin/image-editor/preview-effects")
    public void put(
        @RequestHeader("LB-Role") String role,
        @RequestBody UpdatePreviewEffectCommand cmd
    ) {
        Validator.isAdmin(role);
        previewEffectService.put(cmd);
    }
}

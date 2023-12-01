package com.liberty52.main.service.applicationservice.image.editor.setting;

import com.liberty52.main.service.controller.image.editor.setting.dto.PreviewEffectResponse;
import com.liberty52.main.service.controller.image.editor.setting.dto.UpdatePreviewEffectCommand;
import com.liberty52.main.service.entity.image.editor.setting.PreviewEffect;
import com.liberty52.main.service.repository.image.editor.setting.PreviewEffectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PreviewEffectServiceImpl implements PreviewEffectService {
    private final PreviewEffectRepository previewEffectRepository;

    @Override
    public List<PreviewEffectResponse> getAll() {
        return previewEffectRepository.findAll().stream()
            .map(entity -> new PreviewEffectResponse(entity.getId(), entity.getName(), entity.getOpacity(), entity.getSrc()))
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(String id) {
        previewEffectRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void put(UpdatePreviewEffectCommand cmd) {
        previewEffectRepository.findById(cmd.getId())
            .ifPresentOrElse(
                previewEffect -> previewEffect.update(cmd.getName(), cmd.getOpacity(), cmd.getSrc()),
                () -> previewEffectRepository.save(
                    PreviewEffect.builder()
                        .id(cmd.getId())
                        .name(cmd.getName())
                        .opacity(cmd.getOpacity())
                        .src(cmd.getSrc())
                        .build()
                )
            );
    }
}

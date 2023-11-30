package com.liberty52.main.service.entity.image.editor.setting;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "preview_effect")
@Getter
@NoArgsConstructor
public class PreviewEffect {
    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "opacity")
    private Integer opacity;

    @Column(name = "src", nullable = false)
    private String src;

    @Builder
    public PreviewEffect(String id, String name, Integer opacity, String src) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(opacity);
        Objects.requireNonNull(src);
        Optional.ofNullable(id).ifPresentOrElse(
            presentId -> this.id = presentId,
            () -> this.id = UUID.randomUUID().toString()
        );
        this.name = name;
        this.opacity = opacity;
        this.src = src;
    }

    public void update(String name, Integer opacity, String src) {
        this.name = name;
        this.opacity = opacity;
        this.src = src;
    }
}

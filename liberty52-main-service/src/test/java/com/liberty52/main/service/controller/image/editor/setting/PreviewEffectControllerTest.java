package com.liberty52.main.service.controller.image.editor.setting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liberty52.main.global.constants.RoleConstants;
import com.liberty52.main.service.applicationservice.image.editor.setting.PreviewEffectService;
import com.liberty52.main.service.controller.image.editor.setting.dto.PreviewEffectResponse;
import com.liberty52.main.service.controller.image.editor.setting.dto.UpdatePreviewEffectCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PreviewEffectController.class)
class PreviewEffectControllerTest {
    @MockBean
    private PreviewEffectService previewEffectService;

    @Autowired
    private MockMvc mvc;

    @Test
    void findAll() throws Exception {
        List<PreviewEffectResponse> exp = Stream.iterate(0, (i) -> 0).limit(10)
            .map(e -> new PreviewEffectResponse("1", "2", 3, "4")).toList();
        given(previewEffectService.getAll())
            .willReturn(exp);
        mvc.perform(get("/image-editor/preview-effects").contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.length()").value(exp.size())
            );
    }

    @Autowired
    private ObjectMapper mapper;

    @Test
    void remove() throws Exception {
        doNothing().when(previewEffectService).delete(anyString());
        mvc.perform(
                delete("/admin/image-editor/preview-effects/{id}", "some id")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("LB-Role", RoleConstants.ADMIN)
            )
            .andExpectAll(
                status().isOk()
            );
    }

    @Test
    void update() throws Exception {
        doNothing().when(previewEffectService).put(any());
        mvc.perform(
                delete("/admin/image-editor/preview-effects/{id}", "some id")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("LB-Role", RoleConstants.ADMIN)
                    .content(mapper.writeValueAsString(
                        new UpdatePreviewEffectCommand(
                            UUID.randomUUID().toString(),
                            UUID.randomUUID().toString(),
                            4,
                            UUID.randomUUID().toString()
                        )
                    ))
            )
            .andExpectAll(
                status().isOk()
            );
    }
}

package com.liberty52.main.service.controller;

import com.liberty52.main.service.applicationservice.ReviewCreateService;
import com.liberty52.main.service.applicationservice.ReviewModifyService;
import com.liberty52.main.service.applicationservice.ReviewRemoveService;
import com.liberty52.main.service.applicationservice.ReviewRetrieveService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.liberty52.main.service.utils.MockConstants.MOCK_AUTH_ID;
import static com.liberty52.main.service.utils.MockConstants.MOCK_PRODUCT_REPRESENT_URL;
import static com.liberty52.main.service.utils.MockFactory.createMockReviewRetrieveResponse;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {
    @Autowired
    MockMvc mvc;
    @InjectMocks
    ReviewController controller;
    @MockBean
    ReviewCreateService reviewCreateService;
    @MockBean
    ReviewRetrieveService reviewRetrieveService;
    @MockBean
    ReviewModifyService reviewModifyService;
    @MockBean
    ReviewRemoveService reviewItemRemoveService;

    @Test
    void reviewRetrieveTest() throws Exception {
        //given
        BDDMockito.given(reviewRetrieveService.retrieveReviews(anyString(), anyString(), any(), anyBoolean()))
                .willReturn(createMockReviewRetrieveResponse());

        ResultActions actions = mvc.perform(
                        MockMvcRequestBuilders.get("/reviews/products/LIB-001?size=5&page=0")
                                .header(HttpHeaders.AUTHORIZATION, MOCK_AUTH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage").value(1))
                .andExpect(jsonPath("$.startPage").value(1))
                .andExpect(jsonPath("$.lastPage").value(1))
                .andExpect(jsonPath("$.contents[0].rating").value(3))
                .andExpect(jsonPath("$.contents[0].content").value("good"))
                .andExpect(jsonPath("$.contents[0].nofReply").value(3))
                .andExpect(jsonPath("$.contents[0].isYours").value(true))
                .andExpect(
                        jsonPath("$.contents[0].imageUrls[0]").value(MOCK_PRODUCT_REPRESENT_URL));

        for (int i = 0; i < 3; i++) {
            actions.andExpect(jsonPath("$.contents[0].replies[" + i + "].content").value("맛있따" + i));
            actions.andExpect(jsonPath("$.contents[0].replies[" + i + "].isYours").value(true));
        }

        actions.andDo(print());

        //when

        //then

    }

}

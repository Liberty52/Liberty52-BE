package com.liberty52.auth.user.service;

import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.web.dto.ReviewerProfileResponse;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ProfileRetrieveServiceImplTest {

    @Autowired
    ProfileRetrieveService service;

    @Autowired
    EntityManager em;
    Set<String> ids;

    @BeforeEach
    void beforeEach(){
        em.clear();

        ids = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Auth profile = Auth.createUser("uniqueEmail" + i, "12345678",
                    "KIM", null, "PROFILE");
            profile.updateUser(null, "KIM"+profile.getId());
            profile.updateUserProfile("PROFILE");
            ids.add(profile.getId());
            em.persist(profile);
        }
        ids.add("guest_id");


        em.flush();

    }

    @AfterEach
    void afterEach(){
        em.clear();;
    }

    @Test
    void retrieveReviewerProfile () throws Exception{
        //given
        Map<String, ReviewerProfileResponse> response = service.retrieveReviewerProfile(
                ids);

        //when
        assertThat(response.size()).isSameAs(4);
        for (String id : ids) {
            if ("guest_id".equals(id)) {
                assertThat(response.get(id).getAuthorName()).isEqualTo("guest_id");
                assertThat(response.get(id).getAuthorProfileUrl()).isNull();
                continue;
            }
            assertThat(response.get(id).getAuthorName()).isEqualTo("KIM"+id);
            assertThat(response.get(id).getAuthorProfileUrl()).isEqualTo("PROFILE");
        }

        //then


    }


}

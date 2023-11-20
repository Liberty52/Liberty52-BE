package com.liberty52.auth.global.config;

import com.liberty52.auth.service.entity.Auth;
import com.liberty52.auth.service.entity.Notice;
import com.liberty52.auth.service.entity.Question;
import com.liberty52.auth.service.entity.QuestionReply;
import com.liberty52.auth.service.repository.AuthRepository;
import com.liberty52.auth.service.repository.NoticeRepository;
import com.liberty52.auth.service.repository.QuestionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

@Component
@RequiredArgsConstructor
public class DBInitConfig {
    private final DBInitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    public static class DBInitService {

        private final AuthRepository authRepository;
        private final QuestionRepository questionRepository;
        private final NoticeRepository noticeRepository;
        private final PasswordEncoder encoder;

        public void init() {

            final String password = "12341234";
            final String adminPw = "admin";

            try {
                Auth auth = Auth.createUser("test@gmail.com", encoder.encode(password),
                        "김테스터", "010-0101-0101", null);
                Field id = auth.getClass().getDeclaredField("id");

                id.setAccessible(true);
                id.set(auth, "TESTER-001");

                authRepository.save(auth);

                Question question = Question.create("this is title","this is content","TESTER-001");
                Field questionId = question.getClass().getDeclaredField("id");
                questionId.setAccessible(true);
                questionId.set(question, "QUESTION-001");

                QuestionReply questionReply = QuestionReply.create("this is reply content", "ADMIN-001");
                Field questionReplyId = questionReply.getClass().getDeclaredField("id");
                questionReplyId.setAccessible(true);
                questionReplyId.set(questionReply, "QUESTION-REPLY-001");
                questionReply.associate(question);
                questionRepository.save(question);

                // Init Admin
                Auth admin1 = Auth.createAdmin("admin", encoder.encode(adminPw), "관리자", "01012341234");
                Field adminId1 = admin1.getClass().getDeclaredField("id");
                adminId1.setAccessible(true);
                adminId1.set(admin1, "ADMIN-001");
                authRepository.save(admin1);
                Auth admin2 = Auth.createAdmin("mju.omnm@gmail.com", encoder.encode(adminPw), "OMNM_관리자", "01043214321");
                Field adminId2 = admin2.getClass().getDeclaredField("id");
                adminId2.setAccessible(true);
                adminId2.set(admin2, "ADMIN-002");
                authRepository.save(admin2);
                Auth admin3 = Auth.createAdmin("admin@liberty.com", encoder.encode(adminPw), "LIBERTY_관리자", "01078900987");
                Field adminId3 = admin3.getClass().getDeclaredField("id");
                adminId3.setAccessible(true);
                adminId3.set(admin3, "ADMIN-003");
                authRepository.save(admin3);

                for (int i = 0; i < 3; i++) {
                    Notice notice = Notice.create("[이벤트] Liberty52 Frame 포토 리뷰 작성 이벤트"+i,"Notice-Content-"+i,false);
                    Field noticeId = notice.getClass().getDeclaredField("id");
                    noticeId.setAccessible(true);
                    noticeId.set(notice, "NOTICE-00"+i);
                    noticeRepository.save(notice);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

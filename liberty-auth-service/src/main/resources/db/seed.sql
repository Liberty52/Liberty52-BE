INSERT INTO PUBLIC.AUTH (ID, CREATED_AT, EMAIL, NAME, PASSWORD, PW_MODIFIY_REQUEST_DATE, PHONE_NUMBER, PROFILE_URL, REFRESH_TOKEN, ROLE) VALUES ('TESTER-001', '2023-11-23', 'test@gmail.com', '김테스터', '{bcrypt}$2a$10$vTfb3UhXZKTrkafh0dYT.OmSToczwbtXl5HuqzzveBFN2GH03lwCi', '2024-05-23', '010-0101-0101', null, null, 'USER');
INSERT INTO PUBLIC.AUTH (ID, CREATED_AT, EMAIL, NAME, PASSWORD, PW_MODIFIY_REQUEST_DATE, PHONE_NUMBER, PROFILE_URL, REFRESH_TOKEN, ROLE) VALUES ('ADMIN-001', '2023-11-23', 'admin', '관리자', '{bcrypt}$2a$10$YUvP/fXS2BIewyFhXf3mxOdXyLmUtR7WYujIGOZEH.l2VM3rvixDu', '2024-05-23', '01012341234', null, null, 'ADMIN');
INSERT INTO PUBLIC.AUTH (ID, CREATED_AT, EMAIL, NAME, PASSWORD, PW_MODIFIY_REQUEST_DATE, PHONE_NUMBER, PROFILE_URL, REFRESH_TOKEN, ROLE) VALUES ('ADMIN-002', '2023-11-23', 'mju.omnm@gmail.com', 'OMNM_관리자', '{bcrypt}$2a$10$7V2CqVoZGwMb3I.RN0qwiuIEI1oLZjAUwOBLF1t1Wrt2mt2DJYDWm', '2024-05-23', '01043214321', null, null, 'ADMIN');
INSERT INTO PUBLIC.AUTH (ID, CREATED_AT, EMAIL, NAME, PASSWORD, PW_MODIFIY_REQUEST_DATE, PHONE_NUMBER, PROFILE_URL, REFRESH_TOKEN, ROLE) VALUES ('ADMIN-003', '2023-11-23', 'admin@liberty.com', 'LIBERTY_관리자', '{bcrypt}$2a$10$.RAJq80gJz6jL6mtbN8wGeB9fUKxmB056EqKFCfmUowibjDclp4C.', '2024-05-23', '01078900987', null, null, 'ADMIN');


INSERT INTO PUBLIC.NOTICE (ID, COMMENTABLE, CONTENT, CREATED_AT, TITLE) VALUES ('NOTICE-000', false, 'Notice-Content-0', '2023-11-23 06:13:45.314689', '[이벤트] Liberty52 Frame 포토 리뷰 작성 이벤트0');
INSERT INTO PUBLIC.NOTICE (ID, COMMENTABLE, CONTENT, CREATED_AT, TITLE) VALUES ('NOTICE-001', false, 'Notice-Content-1', '2023-11-23 06:13:45.316202', '[이벤트] Liberty52 Frame 포토 리뷰 작성 이벤트1');
INSERT INTO PUBLIC.NOTICE (ID, COMMENTABLE, CONTENT, CREATED_AT, TITLE) VALUES ('NOTICE-002', false, 'Notice-Content-2', '2023-11-23 06:13:45.317224', '[이벤트] Liberty52 Frame 포토 리뷰 작성 이벤트2');


INSERT INTO PUBLIC.QUESTION (ID, CONTENT, CREATED_AT, STATUS, TITLE, WRITER_ID) VALUES ('QUESTION-001', 'this is content', '2023-11-23 06:13:45.021626', 'DONE', 'this is title', 'TESTER-001');



INSERT INTO PUBLIC.QUESTION_REPLY (ID, CONTENT, CREATED_AT, WRITER_ID, QUESTION_ID) VALUES ('QUESTION-REPLY-001', 'this is reply content', '2023-11-23 06:13:45.021626', 'ADMIN-001', 'QUESTION-001');




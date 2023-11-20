package com.liberty52.auth.service.applicationservice;

public interface PasswordMailService {

    boolean sendEmailForUpdatePassword(String email);
    void updatePassword(String emailToken, String password);

}

package com.liberty52.auth.user.service;

public interface PasswordMailService {

    boolean sendEmailForUpdatePassword(String email);
    void updatePassword(String emailToken, String password);

}
